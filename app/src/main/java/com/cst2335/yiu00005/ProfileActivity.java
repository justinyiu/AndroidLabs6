package com.cst2335.yiu00005;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

public class ProfileActivity extends AppCompatActivity {



    public final static String TAG = "PROFILE_ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_lab3);
        ImageButton imgbtn = findViewById(R.id.imageButton2);

        ActivityResultLauncher<Intent> myPictureTakerLauncher =
                registerForActivityResult(new ActivityResultContracts.StartActivityForResult()
                        ,new ActivityResultCallback<ActivityResult>() {
                            @Override
                            public void onActivityResult(ActivityResult result) {
                                if (result.getResultCode() == Activity.RESULT_OK) {
                                    Intent data = result.getData();
                                    Bitmap imgbitmap = (Bitmap) data.getExtras().get("data");
                                    imgbtn.setImageBitmap(imgbitmap); // the imageButton
                                } else if (result.getResultCode() == Activity.RESULT_CANCELED)
                                    Log.i(TAG, "User refused to capture a picture.");
                            }
                        });

        Intent fromMain = getIntent();
        EditText emailEditTest = findViewById(R.id.editText2);
        emailEditTest.setText(fromMain.getStringExtra("EMAIL"));

        ImageButton ibtn = findViewById(R.id.imageButton2);
        ibtn.setOnClickListener((vw) -> {

            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                myPictureTakerLauncher.launch(takePictureIntent);
            }

            myPictureTakerLauncher.launch(takePictureIntent);

            startActivity(takePictureIntent);

        });


    }

    @Override //screen is visible but not responding
    protected void onStart() {
        super.onStart();

        Log.d(TAG, "In onStart, visible but not responding");
    }

    @Override //screen is visible but not responding
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "In onResume");
    }

    @Override //screen is visible but not responding
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "In onPause");
    }

    @Override //not visible
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "In onStop");
    }

    @Override  //garbage collected
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "In onDestroy");
    }


    protected void onActivityResult(){
        Log.e(TAG,"In function:");
    }





}