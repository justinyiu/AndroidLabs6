package com.cst2335.yiu00005;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

public class ProfileActivity extends AppCompatActivity {
    public static final String TAG = "ProfileActivity";
    ImageView imgView;
    EditText emailText;


    ActivityResultLauncher<Intent> myPictureTakerLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK)
                    { Intent data = result.getData();
                        Bitmap imgbitmap = (Bitmap) data.getExtras().get("data");
                        imgView = findViewById(R.id.imageButton);
                        imgView.setImageBitmap(imgbitmap); // the imageButton
                    }
                    else if(result.getResultCode() == Activity.RESULT_CANCELED)
                        Log.i(TAG, "User refused to capture a picture.");
                }
            } );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(TAG, "In function: onCreate");
        setContentView(R.layout.activity_profile_lab3);

        Intent fromMain = getIntent();
        String email = fromMain.getStringExtra("EMAIL");
        emailText = findViewById(R.id.editTextTextPersonName2);
        emailText.setText(email);

        SharedPreferences prefs = getSharedPreferences(MainActivity.PREFERENCES_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("EMAIL", "");


        ImageButton imgBt = findViewById(R.id.imageButton);
        imgBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    myPictureTakerLauncher.launch(takePictureIntent);
                }
            }
        });

        Button chatButton = findViewById(R.id.chatButton);
        chatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToProfile = new Intent(ProfileActivity.this, ChatRoomActivity.class);
                startActivity(goToProfile);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "In function: onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "In function: onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "In function: onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "In function: onStop");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "In function: onRestart");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "In function: onDestroy");
    }
}