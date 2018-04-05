package com.msushanth.mystocks1;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Objects;

public class SplashScreen extends Activity {

    //String words;
    //String printWords;
    String stocksToSave="";
    String dataRead="";
    TextView loadingText;
    int numCompleted = 0;
    Intent passOnData;

    FileOutputStream outputFile;
    OutputStreamWriter outputWriter;
    FileInputStream fileIn;
    InputStreamReader inputRead;

    static final int READ_BLOCK_SIZE = 100;
    final String API_KEY = "EEQXBJ2E2FCFANHP";
    int NUM_STOCKS_TO_LOAD=0;
    final String[] stockDataToRetrieve =
            {/*"Microsoft", "https://www.alphavantage.co/query?function=TIME_SERIES_DAILY&symbol=MSFT&outputsize=compact&apikey=" + API_KEY,
                    "Apple", "https://www.alphavantage.co/query?function=TIME_SERIES_DAILY&symbol=AAPL&outputsize=compact&apikey=" + API_KEY,*/
                    //"Facebook", "https://www.alphavantage.co/query?function=TIME_SERIES_DAILY&symbol=FB&outputsize=full&apikey=" + API_KEY
                    //"Facebook", "https://www.alphavantage.co/query?function=TIME_SERIES_INTRADAY&symbol=FB&interval=5min&outputsize=compact&apikey=" + API_KEY
                    //"Microsoft", "https://www.alphavantage.co/query?function=TIME_SERIES_WEEKLY&symbol=MSFT&outputsize=full&apikey=" + API_KEY,
                    //"Apple", "https://www.alphavantage.co/query?function=TIME_SERIES_WEEKLY&symbol=AAPL&outputsize=full&apikey=" + API_KEY,
                    "Facebook", "https://www.alphavantage.co/query?function=TIME_SERIES_WEEKLY&symbol=FB&outputsize=full&apikey=" + API_KEY
                    //"Facebook", "https://www.alphavantage.co/query?function=TIME_SERIES_INTRADAY&symbol=FB&interval=60min&outputsize=compact&apikey=" + API_KEY
            };




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        // Get data from memory
        try {
            outputFile = openFileOutput("stocksToSave.txt", MODE_PRIVATE);
            outputWriter = new OutputStreamWriter(outputFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // Save data to memory
        try {
            fileIn = openFileInput("stocksToSave.txt");
            inputRead= new InputStreamReader(fileIn);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        loadingText = (TextView) findViewById(R.id.textView);
        passOnData = new Intent(getApplicationContext(), MainActivity.class);

        NUM_STOCKS_TO_LOAD = stockDataToRetrieve.length/2;
        System.out.println("#$%#$%Number of stocks: " + NUM_STOCKS_TO_LOAD);
        passOnData.putExtra("NUM_STOCKS_TO_LOAD", NUM_STOCKS_TO_LOAD);

        if(stockDataToRetrieve.length != 0) {
            loadingText.setText("Loading " + stockDataToRetrieve[0]);
            for (int i = 1; i < stockDataToRetrieve.length; i += 2) {
                new download().execute(stockDataToRetrieve[i]);
            }
        }



        stocksToSave = "This is a test";

        writeToFile();
        readFromFile();
        System.out.println(dataRead);
    }




    public class download extends AsyncTask<String, Void, Integer> {

        @Override
        protected Integer doInBackground(String[] string) {

            try {
                Document document = Jsoup
                        .connect(string[0])
                        .ignoreContentType(true)
                        .get();
                String words = document.text();

                String[] splitterString = words.split("\"");
                ArrayList<String> values = new ArrayList<>();

                for (int i = 1; i < splitterString.length; i += 2) {
                    values.add(splitterString[i]);
                }

                splitterString = values.toArray(new String[values.size()]);
                if((numCompleted*2) < stockDataToRetrieve.length) {
                    passOnData.putExtra(String.valueOf(numCompleted+1), stockDataToRetrieve[numCompleted*2]);
                    passOnData.putExtra(stockDataToRetrieve[numCompleted*2], splitterString);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            numCompleted++;
            return numCompleted;
        }


        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);

            System.out.println("NUMBER OF STOCKS LOADED: " + integer);

            if((integer*2) < stockDataToRetrieve.length) {
                loadingText.setText("Loading " + stockDataToRetrieve[integer * 2]);
            }

            if(integer == NUM_STOCKS_TO_LOAD) {
                loadingText.setText("Finished");

                Thread myThread = new Thread() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(500);
                            startActivity(passOnData);
                            finish();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };
                myThread.start();
            }
        }
    }



    // write text to file
    public void writeToFile() {
        // add-write text into file
        try {
            outputWriter.append(stocksToSave);
            outputWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Read text from file
    public void readFromFile() {
        //reading text from file
        try {

            int content;
            while ((content = fileIn.read()) != -1) {
                dataRead += (char) content;
            }
            inputRead.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}