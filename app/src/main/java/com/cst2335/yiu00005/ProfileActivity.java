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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

public class ProfileActivity extends AppCompatActivity {

    public static final String TAG = "PROFILE_ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Intent fromMain = getIntent();
        EditText emailSet = findViewById(R.id.emailID);
        emailSet.setText(fromMain.getStringExtra("email"));

        ImageButton imgBtn = findViewById(R.id.imgBtn_1);
        imgBtn.setOnClickListener(v -> { //new Intent(Intent.ACTION_VIEW);
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if(takePictureIntent.resolveActivity(getPackageManager()) != null) {
                startActivity(takePictureIntent);
                myPictureTakerLauncher.launch(takePictureIntent);
            }
        });
        Log.e("profileActivity", "onCreate stage");

        Button btnChat = findViewById(R.id.btn_chat);
        btnChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToChat = new Intent(ProfileActivity.this, ChatRoomActivity.class);
                startActivity(goToChat);
            }
        });

        Button btnWeather = findViewById(R.id.btn_weather);
        btnWeather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent weatherForecast = new Intent(ProfileActivity.this, WeatherForecast.class);
                startActivity(weatherForecast);
            }
        });
    }
    //*************************************************************************************************
    ActivityResultLauncher<Intent> myPictureTakerLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                @Override //onActivityResult(int requestCode, inresultCode, Intent dataBack)
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        ImageButton imgBtn = findViewById(R.id.imgBtn_1);
                        Bitmap imgbitmap = (Bitmap) data.getExtras().get("data");
                        imgBtn.setImageBitmap(imgbitmap);
                        Log.e("profileActivity", "User takes picture");
                    }
                    else if(result.getResultCode() == Activity.RESULT_CANCELED)
                        Log.i(TAG, "User refused to capture a picture.");
                }

            }
    );
    //*************************************************************************************************
    @Override
    public void onStart(){
        super.onStart();
        Log.e("profileActivity", "onStart stage");
    }


    @Override
    public void onResume(){
        super.onResume();
        Log.e("profileActivity", "onResume stage");
    }

    @Override
    public void onPause(){
        super.onPause();
        Log.e("profileActivity", "onPause stage");
    }

    @Override
    public void onStop(){
        super.onStop();
        Log.e("profileActivity", "onStop stage");
    }


    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.e("profileActivity", "onDestroy stage");
    }

}