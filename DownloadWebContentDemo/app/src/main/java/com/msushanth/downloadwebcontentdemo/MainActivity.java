package com.msushanth.downloadwebcontentdemo;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends Activity {



    private class DownloadTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            String result = "";
            URL url;
            HttpURLConnection urlConnection = null;

            try {
                url = new URL(urls[0]);
                urlConnection = (HttpURLConnection) url.openConnection();

                InputStream in = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);
                int data = reader.read();

                while(data != -1) {
                    char current = (char) data;
                    result += current;
                    data = reader.read();
                }
            }
            catch(Exception e) {
                e.printStackTrace();
            }

            return result;
        }

        @Override
        protected void onPostExecute(String result) {
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

        TextView webContent = (TextView) findViewById(R.id.webConetnt);
        webContent.setText("This is a test");

        DownloadTask task = new DownloadTask();
        String result = null;

        try {
            result = task.execute("http://www.dustyfeet.com/").get();
        }
        catch (Exception e) {
            e.printStackTrace();
        }


        webContent.setText(result);
        //Log.i("Contents of URL", result);
    }
}
