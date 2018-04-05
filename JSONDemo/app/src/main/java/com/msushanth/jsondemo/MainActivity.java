package com.msushanth.jsondemo;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {




    private class DownloadTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            String result = "";
            URL url;
            HttpURLConnection urlConnection = null;

            try {
                url = new URL(urls[0]);
                urlConnection = (HttpURLConnection) url.openConnection();

                urlConnection.setDoInput(true);
                InputStream in = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);
                int data = reader.read();

                while (data != -1) {
                    char current = (char) data;
                    result += current;
                    data = reader.read();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            try {
                JSONObject jsonObject = new JSONObject(result);
                String weatherInfo = jsonObject.getString("weather");
                Log.i("Weather Content", weatherInfo);

                JSONArray jsonArray = new JSONArray(weatherInfo);
                for(int i=0; i < jsonArray.length(); i++) {
                    jsonObject = jsonArray.getJSONObject(i);

                    Log.i("main", jsonObject.getString("main"));
                    Log.i("description", jsonObject.getString("description"));
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }

            Log.i("Downloaded API Result", result);
        }

        @Override
        protected void onPreExecute() {
            Log.i("Downloading API Result", "The Webpage is downloading.");
        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DownloadTask task = new DownloadTask();
        task.execute("http://api.openweathermap.org/data/2.5/weather?q=Petaluma&APPID=ac2eef5218a357a10842e8ecd771a598");
    }
}
