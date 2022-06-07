package com.cst2335.yiu00005;

import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.Switch;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import com.google.android.material.snackbar.Snackbar;



public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    //May 19
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setContentView(R.layout.activity_main_linear);

        Log.i(TAG,"MainActivity");

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


        }


    }
