package com.msushanth.weatherapp;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class MainActivity extends Activity {

    EditText cityName;
    TextView resultTextView;




    public void findWeather(View view) {
        // to hide keyboard when button has been tapped
        InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        mgr.hideSoftInputFromWindow(cityName.getWindowToken(), 0);

        String encodedCityName = "";
        try {
            encodedCityName = URLEncoder.encode(cityName.getText().toString(), "UTF-8");
            DownloadTask task = new DownloadTask();
            task.execute("http://api.openweathermap.org/data/2.5/weather?q=" + encodedCityName + "&APPID=ac2eef5218a357a10842e8ecd771a598");
        }
        catch (Exception e) {
            resultTextView.setText("No weather information available!");
        }
    }




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
                String message = "";


                JSONArray jsonArray = new JSONArray(weatherInfo);
                for(int i=0; i < jsonArray.length(); i++) {
                    jsonObject = jsonArray.getJSONObject(i);

                    String main = jsonObject.getString("main");
                    String description = jsonObject.getString("description");

                    if(main != "" && description != "") {
                        message += "\r\n" + main + ": " + description + "\r\n";
                    }
                }

                if(message != "") {
                    resultTextView.setText(message);
                }
                else {
                    resultTextView.setText("No weather information available!");
                }
            }
            catch (Exception e) {
                resultTextView.setText("No weather information available!");
            }
        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cityName = (EditText) findViewById(R.id.cityName);
        resultTextView = (TextView) findViewById(R.id.weatherInfo);
    }
}
