package org.thoughtcrime.securesms;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.MenuItem;
import android.view.Window;

import org.thoughtcrime.securesms.crypto.MasterSecret;
import org.thoughtcrime.securesms.mms.ImageSlide;
import org.thoughtcrime.securesms.util.BitmapDecodingException;
import org.thoughtcrime.securesms.util.DynamicTheme;

import java.io.IOException;

import de.gdata.messaging.util.GUtil;
import de.gdata.messaging.util.ProfileAccessor;


public class ProfileActivity extends PassphraseRequiredActionBarActivity {

  private MasterSecret masterSecret;
  private static final int PICK_IMAGE = 1;
  
  private DynamicTheme dynamicTheme = new DynamicTheme();
  private String profileId;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    dynamicTheme.onCreate(this);
    super.onCreate(savedInstanceState);
    setContentView(R.layout.profile_activity);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    getSupportActionBar().setTitle(getIntent().getStringExtra("profile_name"));
    getSupportActionBar().setSubtitle(getIntent().getStringExtra("profile_number"));
    getSupportActionBar().hide();
    initializeResources();
    this.overridePendingTransition(R.anim.slide_from_top,
        R.anim.slide_out_top);
    Window window = this.getWindow();
    window.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
  }

  @Override
  public void onResume() {
      dynamicTheme.onResume(this);
      super.onResume();
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    super.onOptionsItemSelected(item);

    switch (item.getItemId()) {
      case android.R.id.home:  finish();  return true;
    }

    return false;
  }

  @Override
  protected void onPause() {
    super.onPause();
  }

  private void initializeResources() {
    this.masterSecret    = getIntent().getParcelableExtra("master_secret");
    this.profileId    = getIntent().getStringExtra("profile_id");
  }

  @Override
  public void onActivityResult(int reqCode, int resultCode, Intent data) {
    Log.w("", "onActivityResult called: " + reqCode + ", " + resultCode + " , " + data);
    super.onActivityResult(reqCode, resultCode, data);

    if (data == null || resultCode != RESULT_OK) return;
    switch (reqCode) {
      case PICK_IMAGE:
        try {
          ImageSlide chosenImage = new ImageSlide(this, data.getData());
          ProfileAccessor.setProfilePicture(this, chosenImage);
          ProfileAccessor.setProfilePartId(this, GUtil.numberToLong(profileId), chosenImage.getPart().getPartId().getUniqueId());
          ProfileAccessor.setProfilePartRow(this, GUtil.numberToLong(profileId), chosenImage.getPart().getPartId().getRowId());
        } catch (IOException e) {
          Log.w("GDATA", e);
        } catch (BitmapDecodingException e) {
          Log.w("GDATA", e);
        }
        break;
    }
  }
}