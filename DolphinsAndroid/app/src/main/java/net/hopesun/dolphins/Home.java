package net.hopesun.dolphins;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.design.widget.NavigationView;
import android.support.v4.content.FileProvider;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import net.hopesun.dolphins.integration.Backend;
import net.hopesun.dolphins.models.MoreDetails;
import net.hopesun.dolphins.models.MoreDetailsRes;
import net.hopesun.dolphins.models.UploadImageRes;
import net.hopesun.dolphins.models.ValueScore;
import net.hopesun.dolphins.models.classifier.WTSNVRClass;
import net.hopesun.dolphins.models.classifier.WTSNVRClassifier;
import net.hopesun.dolphins.models.classifier.WTSNVRClassifierImage;
import net.hopesun.dolphins.models.faces.WTSNVRFace;
import net.hopesun.dolphins.models.faces.WTSNVRFacesImage;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private String android_id = "";

    private ImageView capturedImage;
    private Button capture;
    private LinearLayout watsonResults;
    private Button enhance;
    private LinearLayout userHelp;
    private Button sendEnhancements;
    private TextView ageValue;
    private SeekBar age;
    private TextView capturedDetails;

    private UsersMoreDetails usersMoreDetails;

    private UploadImageRes uploadImageRes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        android_id = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        capturedImage = (ImageView) findViewById(R.id.capturedImage);
        capture = (Button) findViewById(R.id.capture);
        capture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                watsonResults.setVisibility(View.VISIBLE);
                userHelp.setVisibility(View.GONE);
                enhance.setVisibility(View.VISIBLE);
                dispatchTakePictureIntent();
            }
        });
        watsonResults = (LinearLayout) findViewById(R.id.watsonResults);
        enhance = (Button) findViewById(R.id.enhance);
        enhance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enhance.setVisibility(View.GONE);
                userHelp.setVisibility(View.VISIBLE);
            }
        });
        userHelp = (LinearLayout) findViewById(R.id.userHelp);
        sendEnhancements = (Button) findViewById(R.id.sendEnhancements);
        sendEnhancements.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MoreDetails moreDetails = usersMoreDetails.getMoreDetails();
                moreDetails.id = uploadImageRes.id;
                final ProgressDialog dialog = ProgressDialog.show(Home.this, "", "Loading. Please wait...", true);
                dialog.show();
                Backend.moreDetails(moreDetails, new Backend.MoreDetailsResults() {
                    @Override
                    public void res(MoreDetailsRes moreDetailsRes) {
                        dialog.dismiss();
                        userHelp.setVisibility(View.GONE);
                        watsonResults.setVisibility(View.GONE);
                        capturedDetails.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(), "Thank you for helping homeless people.", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        ageValue = (TextView) findViewById(R.id.ageValue);
        age = (SeekBar) findViewById(R.id.age);
        age.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                ageValue.setText("[ " + i + " ]");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        usersMoreDetails = new UsersMoreDetails(userHelp);
        capturedDetails = (TextView) findViewById(R.id.capturedDetails);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private Handler m_handler;
    private Runnable m_handlerTask;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap bitmap = (Bitmap) extras.get("data");
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            byte[] byteArray = stream.toByteArray();
            capturedImage.setImageBitmap(bitmap);

            final ProgressDialog dialog = ProgressDialog.show(this, "", "Loading. Please wait...", true);
            dialog.show();

            String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);
            Backend.uploadImage(encoded, new Backend.UploadImageResults() {
                @Override
                public void res(UploadImageRes uploadedImageDetails) {
                    dialog.dismiss();
                    watsonResults.removeAllViews();
                    watsonResults.setVisibility(View.VISIBLE);
                    capturedDetails.setVisibility(View.VISIBLE);

                    uploadImageRes = uploadedImageDetails;

                    List<ValueScore> valueScores = new ArrayList<>();

                    if(uploadImageRes != null){
                        if(uploadImageRes.watson_result != null){
                            if(uploadImageRes.watson_result.classifier != null)
                                if(uploadImageRes.watson_result.classifier.images != null)
                                    for(WTSNVRClassifierImage image : uploadImageRes.watson_result.classifier.images){
                                        if(image.classifiers != null)
                                            for(WTSNVRClassifier classifier : image.classifiers){
                                                if(classifier.classes != null)
                                                    for(WTSNVRClass class_ : classifier.classes){
                                                        valueScores.add(new ValueScore(class_.class_, class_.score));
                                                    }
                                            }
                                    }
                            if(uploadImageRes.watson_result.faces != null)
                                if(uploadImageRes.watson_result.faces.images != null)
                                    for(WTSNVRFacesImage image : uploadImageRes.watson_result.faces.images){
                                        if(image.faces != null)
                                            for(WTSNVRFace face : image.faces){
                                                if(face.age != null) {
                                                    valueScores.add(new ValueScore("Age-Max", (long)face.age.score));
                                                    valueScores.add(new ValueScore("Age-Min", (long)face.age.score));
                                                }
                                                if(face.gender != null) {
                                                    valueScores.add(new ValueScore("Gender", (long)face.gender.score));
                                                }
                                            }
                                    }
                        }
                    }

                    Collections.sort(valueScores);
                    Collections.reverse(valueScores);

                    for(ValueScore valueScore : valueScores){
                        TextView textView = new TextView(getApplicationContext());
                        textView.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                        textView.setText(valueScore.value + " \t\t\t\t " + valueScore.score);
                        watsonResults.addView(textView);
                    }
                }
            });
        }

        if(true)
            return;

        Bitmap bitmap = setPic();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        /*new AsyncTask<byte[], Integer, VisualClassification>(){
            protected VisualClassification doInBackground(byte[]... urls) {
                return new Watson().sendImage(android_id + "-" + System.currentTimeMillis(), urls[0]);
            }
            protected void onPostExecute(VisualClassification result) {
                dialog.dismiss();
                capturedDetails.setText(result.toString());
            }
        }.execute(byteArray);*/
        String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);
        Backend.uploadImage(encoded, null);
    }

    private Bitmap setPic() {
        // Get the dimensions of the View
        int targetW = capturedImage.getWidth();
        int targetH = capturedImage.getHeight();

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        capturedImage.setImageBitmap(bitmap);
        return bitmap;
    }

    static final int REQUEST_TAKE_PHOTO = 1;
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
        }

        if(true)
            return;

        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                ex.printStackTrace();
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this, "net.hopesun.dolphins", photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    String mCurrentPhotoPath;
    private File createImageFile() throws IOException {
        // Create an image file name
        String imageFileName = "captured";//"JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }
}
