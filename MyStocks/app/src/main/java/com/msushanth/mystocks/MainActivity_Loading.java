package com.msushanth.mystocks;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.regex.Pattern;

public class MainActivity_Loading extends Activity {

    final String API_KEY = "EEQXBJ2E2FCFANHP";
    TextView text1;
    Button button1;
    Button button2;

    String words;
    String printWords;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__loading);

        text1 = (TextView) findViewById(R.id.text1);
        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new download().execute();
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                text1.setText(printWords);
            }
        });
    }


    public class download extends AsyncTask<Void, Void, Void> {



        @Override
        protected Void doInBackground(Void... voids) {

            try {
                Document document = Jsoup
                        .connect("https://www.alphavantage.co/query?function=TIME_SERIES_DAILY&symbol=MSFT&outputsize=full&apikey=" + API_KEY)
                        .ignoreContentType(true)
                        .get();
                words = document.text();


                String[] splitterString = words.split("\"");
                ArrayList<String> values = new ArrayList<String>();

                for(int i=1; i<splitterString.length; i+=2) {
                    values.add(splitterString[i]);
                }

                for(int i=5000; i<values.size(); i++) {
                    if(Objects.equals(values.get(i), "1. open")) {
                        printWords = printWords + "\n" + values.get(i+1);
                    }
                }

                //System.out.println("The size of values is: " + values.size());
            }
            catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            text1.setText("Loading data");
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            text1.setText("Completed");
        }
    }
}
