package com.cst2335.yiu00005;

import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.AsyncTaskLoader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONObject;
import org.w3c.dom.Text;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherForecast extends AppCompatActivity {
    ImageView iv;
    TextView current;
    TextView min;
    TextView max;
    TextView uv;
    ProgressBar pb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_forecast);

        pb = findViewById(R.id.progressBar);
        pb.setVisibility(View.VISIBLE);

        iv = findViewById(R.id.iv_weather);
        current = findViewById(R.id.tv_current);
        min = findViewById(R.id.tv_min);
        max = findViewById(R.id.tv_max);
        uv = findViewById(R.id.tv_uv);


        ForecastQuery fq = new ForecastQuery();
        fq.execute();

    }

    public class ForecastQuery extends AsyncTask <String, Integer, String> {
        String uvT;
        String minT;
        String maxT;
        String currentT;
        Bitmap pic;
        String iconName;

        protected String doInBackground(String ... args){
            try{
                URL url = new URL("http://api.openweathermap.org/data/2.5/weather?q=ottawa,ca&APPID=7e943c97096a9784391a981c4d878b22&mode=xml&units=metric");
                HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();

                InputStream inputStream = urlConn.getInputStream();

                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(false);
                XmlPullParser xpp = factory.newPullParser();
                xpp.setInput(inputStream, "UTF-8");

                int eventType = xpp.getEventType();

                while (eventType != XmlPullParser.END_DOCUMENT){

                    if(eventType == XmlPullParser.START_TAG){
                        if(xpp.getName().equals("temperature")){
                            currentT = xpp.getAttributeValue(null, "value");
                            publishProgress(25);
                            minT = xpp.getAttributeValue(null, "min");
                            publishProgress(50);
                            maxT = xpp.getAttributeValue(null, "max");
                            publishProgress(75);
                        }
                        else if(xpp.getName().equals("weather")){
                            iconName = xpp.getAttributeValue(null, "icon");
                        }
                    }
                    eventType = xpp.next();
                }


                if(fileExistance(iconName + ".png")){
                    FileInputStream fis = null;
                    try{
                        fis = openFileInput(iconName + ".png");
                    }
                    catch(FileNotFoundException e){
                        e.printStackTrace();
                    }
                    pic = BitmapFactory.decodeStream(fis);
                    Log.e("IFFound", iconName + ".png file found locally");
                }
                else{
                    URL url2 = new URL("http://openweathermap.org/img/w/" + iconName + ".png");
                    urlConn = (HttpURLConnection) url2.openConnection();
                    urlConn.connect();
                    int responseCode = urlConn.getResponseCode();
                    if(responseCode == 200){
                        pic = BitmapFactory.decodeStream(urlConn.getInputStream());

                        FileOutputStream outputStream = openFileOutput(iconName + ".png", Context.MODE_PRIVATE);
                        pic.compress(Bitmap.CompressFormat.PNG,80,outputStream);
                        outputStream.flush();
                        outputStream.close();

                        publishProgress(100);
                        Log.e("IFFound", iconName + ".png file not found locally, download it");
                    }
                }

                URL url3 = new URL("http://api.openweathermap.org/data/2.5/uvi?appid=7e943c97096a9784391a981c4d878b22&lat=45.348945&lon=-75.759389");
                urlConn = (HttpURLConnection) url3.openConnection();
                InputStream response = urlConn.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(response, "UTF-8"), 8);
                StringBuilder sb = new StringBuilder();

                String line = null;
                while ((line = reader.readLine()) != null)
                {
                    sb.append(line + "\n");
                }
                String result = sb.toString();

                JSONObject uvReport = new JSONObject(result);
                uvT = uvReport.getString("value");


            }
            catch(Exception e) {
                e.printStackTrace();
            }
            return "Done";
        }

        public void onProgressUpdate(Integer ... args){
            pb.setVisibility(View.VISIBLE);
            pb.setProgress(args[0]);
        }

        public void onPostExecute(String fromOdInBackground){
            pb.setVisibility(View.INVISIBLE); //VISIBLE
            current.setText("current: " + currentT +"c");
            min.setText("min: " + minT + "c");
            max.setText("max: "  + maxT + "c");
            uv.setText("uv: " + uvT);
            iv.setImageBitmap(pic);

        }

        public boolean fileExistance(String fname){
            File file = getBaseContext().getFileStreamPath(fname);
            return file.exists();
        }

    }


}