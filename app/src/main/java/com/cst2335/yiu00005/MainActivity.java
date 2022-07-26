package com.cst2335.yiu00005;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity  {
    private static final String TAG = "MainActivity";
    public final static String PREFERENCES_FILE = "MyData";
    String email;
    EditText emailInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(TAG, "In function: onCreate");
        setContentView(R.layout.activity_main);

        emailInput = findViewById(R.id.editText);
        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = emailInput.getText().toString();
                Intent goToProfile = new Intent(MainActivity.this, ProfileActivity.class);
                goToProfile.putExtra("EMAIL", email);
                startActivity(goToProfile);

            }
        });

        SharedPreferences prefs = getSharedPreferences( "activity_main.xml",
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("EMAIL", email);
        editor.apply();

        prefs.getString("EMAIL", "");

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

