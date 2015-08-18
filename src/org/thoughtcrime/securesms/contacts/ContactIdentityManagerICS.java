package org.thoughtcrime.securesms.contacts;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.ContactsContract.Contacts;
import android.provider.ContactsContract.PhoneLookup;

import java.util.LinkedList;
import java.util.List;

class ContactIdentityManagerICS extends ContactIdentityManager {

  public ContactIdentityManagerICS(Context context) {
    super(context);
  }

  @SuppressLint("NewApi")
  @Override
  public Uri getSelfIdentityUri() {
    String[] PROJECTION = new String[] {
        PhoneLookup.DISPLAY_NAME,
        PhoneLookup.LOOKUP_KEY,
        PhoneLookup._ID,
    };

    Cursor cursor = null;

    try {
      cursor = context.getContentResolver().query(ContactsContract.Profile.CONTENT_URI,
                                                    PROJECTION, null, null, null);

      if (cursor != null && cursor.moveToFirst()) {
        return Contacts.getLookupUri(cursor.getLong(2), cursor.getString(1));
      }
    } finally {
      if (cursor != null)
        cursor.close();
    }

    return null;
  }

  @Override
  public boolean isSelfIdentityAutoDetected() {
    return true;
  }

}
