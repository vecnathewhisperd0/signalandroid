package org.thoughtcrime.securesms.jobs;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import org.thoughtcrime.securesms.crypto.MasterCipher;
import org.thoughtcrime.securesms.crypto.MasterSecret;
import org.thoughtcrime.securesms.database.DatabaseFactory;
import org.thoughtcrime.securesms.database.EncryptingSmsDatabase;
import org.thoughtcrime.securesms.database.MmsDatabase;
import org.thoughtcrime.securesms.database.MmsSmsDatabase;
import org.thoughtcrime.securesms.database.NoSuchMessageException;
import org.thoughtcrime.securesms.database.PartDatabase;
import org.thoughtcrime.securesms.database.SmsDatabase;
import org.thoughtcrime.securesms.database.ThreadDatabase;
import org.thoughtcrime.securesms.database.model.MessageRecord;
import org.thoughtcrime.securesms.database.model.SmsMessageRecord;
import org.thoughtcrime.securesms.dependencies.InjectableType;
import org.thoughtcrime.securesms.jobs.requirements.MasterSecretRequirement;
import org.thoughtcrime.securesms.recipients.Recipients;
import org.thoughtcrime.securesms.util.Base64;
import org.thoughtcrime.securesms.util.Util;
import org.whispersystems.jobqueue.JobParameters;
import org.whispersystems.jobqueue.requirements.NetworkRequirement;
import org.whispersystems.libaxolotl.InvalidMessageException;
import org.whispersystems.textsecure.api.TextSecureMessageReceiver;
import org.whispersystems.textsecure.api.messages.TextSecureAttachmentPointer;
import org.whispersystems.textsecure.api.push.exceptions.NonSuccessfulResponseCodeException;
import org.whispersystems.textsecure.api.push.exceptions.PushNetworkException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.inject.Inject;

import de.gdata.messaging.util.GService;
import de.gdata.messaging.util.GUtil;
import ws.com.google.android.mms.MmsException;
import ws.com.google.android.mms.pdu.PduPart;

public class AttachmentDownloadJob extends MasterSecretJob implements InjectableType {

  private static final String TAG = AttachmentDownloadJob.class.getSimpleName();

  @Inject transient TextSecureMessageReceiver messageReceiver;

  private final long messageId;

  public AttachmentDownloadJob(Context context, long messageId) {
    super(context, JobParameters.newBuilder()
                                .withRequirement(new MasterSecretRequirement(context))
                                .withRequirement(new NetworkRequirement(context))
                                .withPersistence()
                                .create());

    this.messageId = messageId;
  }

  @Override
  public void onAdded() {
  }

  @Override
  public void onRun(MasterSecret masterSecret) throws IOException {
    PartDatabase database = DatabaseFactory.getPartDatabase(context);

    Log.w(TAG, "Downloading push parts for: " + messageId);

    List<PduPart> parts = database.getParts(messageId);

    for (PduPart part : parts) {
      retrievePart(masterSecret, part, messageId);
      Log.w(TAG, "Got part: " + part.getPartId());
    }
  }

  @Override
  public void onCanceled() {
    PartDatabase  database = DatabaseFactory.getPartDatabase(context);
    List<PduPart> parts    = database.getParts(messageId);

    for (PduPart part : parts) {
      markFailed(messageId, part, part.getPartId());
    }
  }

  @Override
  public boolean onShouldRetryThrowable(Exception exception) {
    return (exception instanceof PushNetworkException);
  }

  private void retrievePart(MasterSecret masterSecret, PduPart part, long messageId)
          throws IOException
  {
    PartDatabase        database       = DatabaseFactory.getPartDatabase(context);
    File                attachmentFile = null;
    PartDatabase.PartId partId         = part.getPartId();

    try {
      attachmentFile = createTempFile();

      TextSecureAttachmentPointer pointer    = createAttachmentPointer(masterSecret, part);
      InputStream                 attachment = messageReceiver.retrieveAttachment(pointer, attachmentFile);

      database.updateDownloadedPart(masterSecret, messageId, partId, part, attachment);
      saveSlideToMediaHistory(messageId, part, masterSecret);
    } catch (InvalidPartException | NonSuccessfulResponseCodeException | InvalidMessageException | MmsException e) {
      Log.w(TAG, e);
      markFailed(messageId, part, partId);
    } finally {
      if (attachmentFile != null)
        attachmentFile.delete();
    }
  }
  public MessageRecord getMessage(MasterSecret masterSecret, long messageId) throws NoSuchMessageException {
    MmsDatabase mmsDatabase      = DatabaseFactory.getMmsDatabase(context);
    Cursor cursor = mmsDatabase.getMessage(messageId);
    MmsDatabase.Reader reader = mmsDatabase.readerFor(masterSecret, cursor);
    MessageRecord record = reader.getNext();

    reader.close();

    if (record == null) throw new NoSuchMessageException("No message for ID: " + messageId);
    else                return record;
  }
  private void saveSlideToMediaHistory(long messageId, PduPart part, MasterSecret masterSecret) {
    MmsDatabase smsDatabase      = DatabaseFactory.getMmsDatabase(context);
    ThreadDatabase threadDb      = DatabaseFactory.getThreadDatabase(context);
    long threadId = smsDatabase.getThreadIdForMessage(messageId);
    Recipients sender = threadDb.getRecipientsForThreadId(threadId);

    MessageRecord   messageRecord       = null;
    try {
      messageRecord = getMessage(masterSecret, messageId);
    } catch (NoSuchMessageException e) {
      e.printStackTrace();
    }
    if (sender != null && messageRecord != null && !messageRecord.getBody().isSelfDestruction()) {
        GUtil.saveInMediaHistory(context, part, sender.getPrimaryRecipient().getNumber());
      }
    }
  private TextSecureAttachmentPointer createAttachmentPointer(MasterSecret masterSecret, PduPart part)
          throws InvalidPartException
  {
    try {
      MasterCipher masterCipher = new MasterCipher(masterSecret);
      long         id           = Long.parseLong(Util.toIsoString(part.getContentLocation()));
      byte[]       key          = masterCipher.decryptBytes(Base64.decode(Util.toIsoString(part.getContentDisposition())));
      String       relay        = null;

      if (part.getName() != null) {
        relay = Util.toIsoString(part.getName());
      }

      return new TextSecureAttachmentPointer(id, null, key, relay);
    } catch (InvalidMessageException | IOException e) {
      Log.w(TAG, e);
      throw new InvalidPartException(e);
    }
  }

  private File createTempFile() throws InvalidPartException {
    try {
      File file = File.createTempFile("push-attachment", "tmp", context.getCacheDir());
      file.deleteOnExit();

      return file;
    } catch (IOException e) {
      throw new InvalidPartException(e);
    }
  }

  private void markFailed(long messageId, PduPart part, PartDatabase.PartId partId) {
    try {
      PartDatabase database = DatabaseFactory.getPartDatabase(context);
      database.updateFailedDownloadedPart(messageId, partId, part);
    } catch (MmsException e) {
      Log.w(TAG, e);
    }
  }

  private class InvalidPartException extends Exception {
    public InvalidPartException(Exception e) {super(e);}
  }
}
