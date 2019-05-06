package org.thoughtcrime.securesms.mms;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.json.JSONException;
import org.json.JSONObject;
import org.thoughtcrime.securesms.database.Address;
import org.thoughtcrime.securesms.database.model.MessageRecord;
import org.thoughtcrime.securesms.logging.Log;

/**
 * Represents the information required to find the {@link MessageRecord} pointed to by a quote.
 */
public class QuoteId {

  private static final String TAG = QuoteId.class.getSimpleName();

  private static final String ID_STRING = "id";
  private static final String AUTHOR_STRING = "author";

  private final long    id;
  private final Address author;

  public QuoteId(long id, @NonNull Address author) {
    this.id     = id;
    this.author = author;
  }

  public long getId() {
    return id;
  }

  public @NonNull Address getAuthor() {
    return author;
  }

  public @NonNull String serialize() {
    try {
      JSONObject object = new JSONObject();
      object.put(ID_STRING, id);
      object.put(AUTHOR_STRING, author.serialize());
      return object.toString();
    } catch (JSONException e) {
      Log.e(TAG, "Failed to serialize to json", e);
      return "";
    }
  }

  public static @Nullable QuoteId deserialize(@NonNull String serialized) {
    try {
      JSONObject json = new JSONObject(serialized);
      return new QuoteId(json.getLong(ID_STRING), Address.fromSerialized(json.getString(AUTHOR_STRING)));
    } catch (JSONException e) {
      Log.e(TAG, "Failed to deserialize from json", e);
      return null;
    }
  }
}
