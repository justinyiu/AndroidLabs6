package com.cst2335.yiu00005;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.Intent;
import android.util.Log;
import android.widget.Button;
import android.widget.Switch;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import com.google.android.material.snackbar.Snackbar;
import android.widget.EditText;



public class MainActivity extends AppCompatActivity {


    //May 19
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lab3layout);

        EditText editText = findViewById(R.id.editText);
        Button btn = findViewById(R.id.button);

        SharedPreferences sp = getSharedPreferences("data", Context.MODE_PRIVATE);
        String previous = sp.getString("editEmail", "");
        editText.setText(previous);

        btn.setOnClickListener(click -> {
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("editEmail", editText.getText().toString());
            editor.apply();

            Intent goToProfile = new Intent(MainActivity.this, ProfileActivity.class);
            goToProfile.putExtra("EMAIL", editText.getText().toString());
            startActivity(goToProfile);


                });



        }

    @Override
    protected void onPause() {
        super.onPause();

    }

    /**
        Switch sw = findViewById(R.id.Switch);

        sw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar snackbar = Snackbar.make(findViewById(R.id.Switch), "The switch is now ", Snackbar.LENGTH_SHORT);
                snackbar.setAction( "Undo", click -> sw.setChecked(false));
                snackbar.show();

                if(sw.isChecked())  {


                    snackbar.setText("The switch is now ON");
                    Log.i(TAG, "view");
                }
                else if (!sw.isChecked()) {
                    snackbar.setText("The switch is now OFF");
                    Log.i(TAG,"view");
                }
            }
        });

        Button btn = findViewById(R.id.button);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(getApplicationContext(),"Here is more information", Toast.LENGTH_LONG);
                toast.show();

                Log.i(TAG,"view");
            }
        }) ;

**/
        }



