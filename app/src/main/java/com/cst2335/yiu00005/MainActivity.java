package com.cst2335.yiu00005;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;



import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_lab3);

        EditText email = findViewById(R.id.email_input);

        Button btnLogin = findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(v -> {
            Intent goToProfile = new Intent(MainActivity.this, ProfileActivity.class);
            goToProfile.putExtra("email", email.getText().toString());
            startActivity(goToProfile);
        });

        SharedPreferences sp = getSharedPreferences("userInput", MODE_PRIVATE);
        email.setText(sp.getString("email",""));
        //getResources().getString(R.string.stringName);
        Log.v("input", sp.getString("email", ""));


        /*
        //code for lab2
        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick (View view){
                Toast.makeText(MainActivity.this, R.string.toast_message, Toast.LENGTH_LONG).show();
            }
        });

        Switch switch1 = findViewById(R.id.switch1);
        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton cb, boolean isChecked) {
                Snackbar snackbar = Snackbar.make(switch1, "", Snackbar.LENGTH_LONG)
                        .setAction(getResources().getString(R.string.snackbar_undo), click -> cb.setChecked(!isChecked));
                        //.show();
                if (isChecked) {
                    snackbar.setText(R.string.snackbar_on);
                } else {
                    snackbar.setText(R.string.snackbar_off);
                }
                snackbar.show();
            }
        });
        */
    }

    /*
        @Override
        public void onStart(){
            super.onStart();
            Log.e("MainActivity", "onStart stage");
        }


        @Override
        public void onResume(){
            super.onResume();
            Log.e("MainActivity", "onResume stage");
        }
    */
    @Override
    public void onPause(){
        super.onPause();
        EditText email = findViewById(R.id.email_input);
        //SharedPreferences sp = getSharedPreferences("fileName", Context.MODE_PRIVATE);
        SharedPreferences sp = getSharedPreferences("userInput", MODE_PRIVATE);
        //need initialized before use
        SharedPreferences.Editor spE = sp.edit();
        spE.putString("email", email.getText().toString());
        spE.commit();
        //Log.v("first", sp.getString("email", ""));
        Log.e("MainActivity", "onPause stage");
    }
/*
    @Override
    public void onStop(){
        super.onStop();
        Log.e("MainActivity", "onStop stage");
    }


    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.e("MainActivity", "onDestroy stage");
    }

 */
}

