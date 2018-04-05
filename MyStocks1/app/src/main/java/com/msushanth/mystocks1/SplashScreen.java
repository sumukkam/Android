package com.msushanth.mystocks1;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Connection;
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


// TODO: dont add stock current price to the file.txt
public class SplashScreen extends Activity {

    TextView loadingText;
    int numCompleted = 0;
    Intent passOnData;

    FileOutputStream outputFile;
    OutputStreamWriter outputWriter;
    FileInputStream fileIn;
    InputStreamReader inputRead;
    String stocksToSave="";
    String dataRead="";

    final String CURRENT_STOCK_PRICE_BASE_URL = "https://www.google.com/finance/info?q=NASDAQ:";
    final String API_KEY = "EEQXBJ2E2FCFANHP";
    int NUM_STOCKS_TO_LOAD = 0;
    ArrayList<String> stockDataToRetrieve = new ArrayList<>();
    ArrayList<String> stockName = new ArrayList<>();

    /*
    String[] stockDataToRetrieve =
            { "Facebook", "https://www.alphavantage.co/query?function=TIME_SERIES_WEEKLY&symbol=FB&outputsize=full&apikey=" + API_KEY };
    */

    // For current stock price:
    // https://www.google.com/finance/info?q=NASDAQ:STOCK_SYMBOL_GOES_HERE

    // For weekly stock prices outputsize=full
    // "Facebook", "https://www.alphavantage.co/query?function=TIME_SERIES_WEEKLY&symbol=FB&outputsize=full&apikey=" + API_KEY




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        loadingText = (TextView) findViewById(R.id.textView);
        passOnData = new Intent(getApplicationContext(), MainActivity.class);


        // Get data from memory
        try {
            outputFile = openFileOutput("stocksToSave.txt", MODE_APPEND);
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

        /*
        stocksToSave = "TSLA";
        writeToFile();
        */
        readFromFile();
        stocksToSave = dataRead;
        System.out.println("$^%$%$ SplashScreen data: " + stocksToSave);

        // If there is no data to read, go to MainActivity
        if(dataRead.equals("")) {
            System.out.println("No data to read.");
            passOnData.putExtra("0", 0);
            startActivity(passOnData);
            finish();
        } else {
            // Continue with rest of program if there are stocks to load
            String[] splitterString = dataRead.split(",");
            for (String str : splitterString) {
                stockDataToRetrieve.add(CURRENT_STOCK_PRICE_BASE_URL + str);
                stockName.add(str);
            }

            // The number of stocks the user has added to his list
            NUM_STOCKS_TO_LOAD = stockDataToRetrieve.size();
            System.out.println("#$%#$%Number of stocks: " + NUM_STOCKS_TO_LOAD);
            passOnData.putExtra("0", NUM_STOCKS_TO_LOAD);

            // Load the current stock price for users stocks
            loadingText.setText("Loading " + stockName.get(0));
            for (int i = 0; i < stockDataToRetrieve.size(); i++) {
                new download().execute(stockDataToRetrieve.get(i));
            }
        }
    }




    public class download extends AsyncTask<String, Void, Integer> {

        @Override
        protected Integer doInBackground(String[] string) {
            Document document = null;
            numCompleted++;

            try {

                Connection con = Jsoup
                        .connect(string[0])
                        .userAgent("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/535.21 (KHTML, like Gecko) Chrome/19.0.1042.0 Safari/535.21")
                        .followRedirects(true)
                        .timeout(20000);
                Connection.Response response = con.execute();
                document = null;
                if (response.statusCode() == 200) {
                    document = con.get();
                }

                String words = document.text();
                System.out.println("##$$##" + words);
                String[] splitterString = words.split("\"");

                // Data passed on to MainActivity will be in the form STOCK_NAME,STOCK_PRICE
                String putExtraStr = splitterString[7] + "," + splitterString[15];
                passOnData.putExtra(String.valueOf(numCompleted), putExtraStr);

                System.out.println("*^&$#$%putExtraStr" + putExtraStr);
            } catch (IOException e) {
                e.printStackTrace();
            }


            if(document == null) {
                try {
                    Connection con = Jsoup
                            .connect(string[0].replace("NASDAQ", "NYSE"))
                            .userAgent("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/535.21 (KHTML, like Gecko) Chrome/19.0.1042.0 Safari/535.21")
                            .followRedirects(true)
                            .timeout(20000);
                    Connection.Response response = con.execute();
                    document = null;
                    if (response.statusCode() == 200) {
                        document = con.get();
                    }

                    String words = document.text();
                    System.out.println("##$$##" + words);
                    String[] splitterString = words.split("\"");

                    // Data passed on to MainActivity will be in the form STOCK_NAME,STOCK_PRICE
                    String putExtraStr = splitterString[7] + "," + splitterString[15];
                    passOnData.putExtra(String.valueOf(numCompleted), putExtraStr);

                    System.out.println("@$@#$putExtraStr" + putExtraStr);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return numCompleted;
        }


        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);

            System.out.println("NUMBER OF STOCKS LOADED: " + integer);

            if(integer < stockDataToRetrieve.size()) {
                loadingText.setText("Loading " + stockName.get(integer));
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
        try {
            outputWriter.append(stocksToSave);
            outputWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Read text from file
    public void readFromFile() {
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