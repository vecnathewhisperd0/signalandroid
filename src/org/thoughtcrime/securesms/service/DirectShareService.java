package org.thoughtcrime.securesms.service;

import android.content.ComponentName;
import android.content.IntentFilter;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.os.Build;
import android.os.Bundle;
import android.service.chooser.ChooserTarget;
import android.service.chooser.ChooserTargetService;
import android.support.annotation.RequiresApi;
import android.util.Log;

import org.thoughtcrime.securesms.ShareActivity;
import org.thoughtcrime.securesms.crypto.MasterCipher;
import org.thoughtcrime.securesms.crypto.MasterSecret;
import org.thoughtcrime.securesms.database.DatabaseFactory;
import org.thoughtcrime.securesms.database.ThreadDatabase;
import org.thoughtcrime.securesms.database.model.ThreadRecord;
import org.thoughtcrime.securesms.recipients.RecipientFactory;
import org.thoughtcrime.securesms.recipients.Recipients;
import org.thoughtcrime.securesms.util.BitmapUtil;

import java.util.LinkedList;
import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.M)
public class DirectShareService extends ChooserTargetService {

  private boolean waitingUntilConversationsLoaded   = true;
  private long    getConversationsRetryRate         = 200;
  private int     maxGetConversationTries           = 15;
  private int     getConversationsTryCounter        = 0;

  @Override
  public List<ChooserTarget> onGetChooserTargets(ComponentName targetActivityName,
                                                 IntentFilter matchedFilter)
  {
    List<ChooserTarget> results        = new LinkedList<>();
    MasterSecret        masterSecret   = KeyCachingService.getMasterSecret(this);

    if (masterSecret == null) {
      return results;
    }

    while (waitingUntilConversationsLoaded) {
      ThreadDatabase threadDatabase   = DatabaseFactory.getThreadDatabase(this);
      Cursor         cursor           = threadDatabase.getDirectShareList();

      try {
        ThreadDatabase.Reader reader = threadDatabase.readerFor(cursor, new MasterCipher(masterSecret));
        ThreadRecord record;

        while ((record = reader.getNext()) != null) {
          if (record.getRecipients().getPrimaryRecipient().getName() != null
                  && record.getRecipients().getPrimaryRecipient().getContactPhoto() != null) {
            waitingUntilConversationsLoaded = false;
            break;
          }
        }
      } finally {
        if (cursor != null) cursor.close();
      }

      try {
        Thread.sleep(getConversationsRetryRate);
      } catch (Exception exception) {
        Log.i("DirectShareService: ", exception.getMessage());
      }

      if (getConversationsTryCounter > maxGetConversationTries) {
        Log.i("DirectShareService: ", "Timeout for getting Conversations!");
        results.clear();
        return results;
      }

      getConversationsTryCounter++;
    }

    ComponentName  componentName    = new ComponentName(this, ShareActivity.class);
    ThreadDatabase threadDatabase   = DatabaseFactory.getThreadDatabase(this);
    Cursor         cursor           = threadDatabase.getDirectShareList();

    try {
      ThreadDatabase.Reader reader = threadDatabase.readerFor(cursor, new MasterCipher(masterSecret));
      ThreadRecord record;

      while ((record = reader.getNext()) != null && results.size() < 10) {
        if (record.getRecipients().getPrimaryRecipient().getName() != null
                && record.getRecipients().getPrimaryRecipient().getContactPhoto() != null) {
          Recipients recipients   = RecipientFactory.getRecipientsForIds(this, record.getRecipients().getIds(), false);
          String     name         = recipients.toShortString();
          Drawable   drawable     = recipients.getContactPhoto().asDrawable(this, recipients.getColor().toConversationColor(this));
          Bitmap     avatar       = BitmapUtil.createFromDrawable(drawable, 500, 500);

          Bundle bundle = new Bundle();
          bundle.putLong(ShareActivity.EXTRA_THREAD_ID, record.getThreadId());
          bundle.putLongArray(ShareActivity.EXTRA_RECIPIENT_IDS, recipients.getIds());
          bundle.putInt(ShareActivity.EXTRA_DISTRIBUTION_TYPE, record.getDistributionType());

          results.add(new ChooserTarget(name, Icon.createWithBitmap(avatar), 1.0f, componentName, bundle));
        }
      }

      return results;
    } finally {
      if (cursor != null) cursor.close();
    }
  }
}
