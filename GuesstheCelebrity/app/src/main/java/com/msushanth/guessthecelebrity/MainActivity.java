package com.msushanth.guessthecelebrity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> celebUrls = new ArrayList<String>();
    ArrayList<String> celebNames = new ArrayList<String>();
    Random random;
    int chosenCeleb = 0;
    int locationOfCorrectAnswer = 0;
    String[] answers = new String[4];
    ImageView imageView;
    Button button0;
    Button button1;
    Button button2;
    Button button3;




    public void createNewQuestion() {
        random = new Random();
        chosenCeleb = random.nextInt(celebNames.size());

        ImageDownloader imageTask = new ImageDownloader();
        Bitmap celebImage;
        try {
            celebImage = imageTask.execute(celebUrls.get(chosenCeleb)).get();
            imageView.setImageBitmap(celebImage);

            locationOfCorrectAnswer = random.nextInt(4);
            for(int i=0; i < 4; i++) {
                if(i == locationOfCorrectAnswer) {
                    answers[i] = celebNames.get(chosenCeleb);
                }
                else {
                    int incorrectAnswer = random.nextInt(celebNames.size());
                    while(incorrectAnswer == chosenCeleb) {
                        incorrectAnswer = random.nextInt(celebNames.size());
                    }

                    answers[i] = celebNames.get(incorrectAnswer);
                }
            }

            button0.setText(answers[0]);
            button1.setText(answers[1]);
            button2.setText(answers[2]);
            button3.setText(answers[3]);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }




    public void celebChosen(View view) {
        if(view.getTag().toString().equals(Integer.toString(locationOfCorrectAnswer))) {
            Toast.makeText(getApplicationContext(), "Correct!", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(getApplicationContext(), "Wrong! It was " + celebNames.get(chosenCeleb), Toast.LENGTH_SHORT).show();
        }

        createNewQuestion();
    }






    private class ImageDownloader extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(String... urls) {
            try {

                URL url = new URL(urls[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                connection.setDoInput(true);
                connection.connect();
                InputStream inputStream = connection.getInputStream();

                Bitmap myBitmap = BitmapFactory.decodeStream(inputStream);
                return myBitmap;
            }
            catch(Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        protected void onPostExecute(String result) {
            Log.i("Downloaded Image", "The image has been downloaded.");
        }

        @Override
        protected void onPreExecute() {
            Log.i("Downloading Image", "The image is downloading.");
            Log.i("Number of Celebs", "The number of celebs in the array are: " + celebNames.size());
        }

        @Override
        protected void onProgressUpdate(Void... values) {
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
            Log.i("Downloaded Webpage", "The Webpage has been downloaded.");
            createNewQuestion();
        }

        @Override
        protected void onPreExecute() {
            Log.i("Downloading Webpage", "The Webpage is downloading.");
        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = (ImageView) findViewById(R.id.imageView);
        button0 = (Button) findViewById(R.id.button0);
        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);

        DownloadTask task = new DownloadTask();
        String result = null;

        try {
            result = task.execute("http://www.posh24.com/celebrities").get();
            String[] splitResult = result.split("<div class=\"sidebarContainer\">");

            Pattern p = Pattern.compile("img src=\"(.*?)\"");
            Matcher m = p.matcher(splitResult[0]);
            while(m.find()) {
                celebUrls.add(m.group(1));
            }

            p = Pattern.compile("alt=\"(.*?)\"");
            m = p.matcher(splitResult[0]);
            while(m.find()) {
                celebNames.add(m.group(1));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
