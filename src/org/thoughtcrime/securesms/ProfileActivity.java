package org.thoughtcrime.securesms;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import org.thoughtcrime.securesms.crypto.MasterSecret;
import org.thoughtcrime.securesms.mms.AttachmentManager;
import org.thoughtcrime.securesms.mms.ImageSlide;
import org.thoughtcrime.securesms.util.BitmapDecodingException;
import org.thoughtcrime.securesms.util.DynamicTheme;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;

import de.gdata.messaging.util.ProfileAccessor;


public class ProfileActivity extends PassphraseRequiredActionBarActivity {

    private MasterSecret masterSecret;
    public static final int PICK_IMAGE = 1;
    public static final int TAKE_PHOTO = 2;

    private DynamicTheme dynamicTheme = new DynamicTheme();
    private String profileId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        dynamicTheme.onCreate(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_activity);
        setStatusBarColor();

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

    public void setStatusBarColor() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            int statusBarColor = Color.parseColor("#00000000");

            if (statusBarColor == Color.BLACK && window.getNavigationBarColor() == Color.BLACK) {
                window.clearFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            } else {
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            }
            window.setStatusBarColor(statusBarColor);
        }
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
            case android.R.id.home:
                finish();
                return true;
        }

        return false;
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    private void initializeResources() {
        this.masterSecret = getIntent().getParcelableExtra("master_secret");
        this.profileId = getIntent().getStringExtra("profile_id");
    }

    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        Log.w("", "onActivityResult called: " + reqCode + ", " + resultCode + " , " + data);
        super.onActivityResult(reqCode, resultCode, data);

        switch (reqCode) {
            case PICK_IMAGE:
                if(data != null) {
                    try {
                        OutputStream out;
                        File f = AttachmentManager.getOutputMediaFile();
                        if(f.exists()) {
                            f.delete();
                        }
                        out = new FileOutputStream(f);
                        out.write(readBytes(data.getData()));
                        out.close();

                        ImageSlide chosenImage = new ImageSlide(this, Uri.fromFile(f));
                        ProfileAccessor.setProfilePicture(this, chosenImage);
                    } catch (IOException e) {
                        Log.w("GDATA", e);
                    } catch (BitmapDecodingException e) {
                        Log.w("GDATA", e);
                    }
                }
                break;
            case TAKE_PHOTO:
                if(resultCode == 0) {return;}
                try {
                    File image = AttachmentManager.getOutputMediaFile();
                    if (image != null) {
                        Uri fileUri = Uri.fromFile(image);
                        ImageSlide chosenImage = new ImageSlide(this, fileUri);
                        ProfileAccessor.setProfilePicture(this, chosenImage);
                    }
                } catch (IOException e) {
                    Log.w("GDATA", e);
                } catch (BitmapDecodingException e) {
                    Log.w("GDATA", e);
                }
                break;
        }
    }

    public byte[] readBytes(Uri uri) throws IOException {
        // this dynamically extends to take the bytes you read
        InputStream inputStream = getContentResolver().openInputStream(uri);
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();

        // this is storage overwritten on each iteration with bytes
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        // we need to know how may bytes were read to write them to the byteBuffer
        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        // and then we can return your byte array.
        return byteBuffer.toByteArray();
    }
}