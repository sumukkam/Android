package com.msushanth.mystocks1;

import android.app.Activity;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;

import static java.util.Arrays.asList;

public class MainActivity extends Activity {

    Intent passOnData;
    ListView stocksListView;
    ImageButton addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        passOnData = new Intent(getApplicationContext(), DisplayGraph.class);
        stocksListView = (ListView) findViewById(R.id.stocksListView);
        addButton = (ImageButton) findViewById(R.id.button);

        // If the user wants to add another stock to the list
        addButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    addButton.setBackground(getResources().getDrawable(R.drawable.floating_add_pressed));
                }
                if(motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    addButton.setBackground(getResources().getDrawable(R.drawable.floating_add));
                }
                return false;
            }
        });

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        final String[] MSFT = extras.getStringArray("Microsoft");
        final String[] AAPL = extras.getStringArray("Apple");
        final String[] FB = extras.getStringArray("Facebook");


        ArrayList<String> stocks = new ArrayList<>(asList("Microsoft", "Apple", "Facebook"));
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, stocks);
        stocksListView.setAdapter(arrayAdapter);

        stocksListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                /*if(i==0) {
                    passOnData.putExtra("Stock Data", MSFT);
                    startActivity(passOnData);
                }
                if(i==1) {
                    passOnData.putExtra("Stock Data", AAPL);
                    startActivity(passOnData);
                }*/
                //if(i==0) {
                    passOnData.putExtra("Stock Name", "FB");
                    passOnData.putExtra("Stock Data", FB);
                    startActivity(passOnData);
                //}
            }
        });

        final int numDataPoints = 100;

        /**
         * get NUM_STOCKS_TO_LOAD
         * using that value, loop through that many times
         * the number will get the name of the stock
         * the name of the stock will get the data from API call
         */

    }



    /*
    public void buttonPressed(View view) {
        System.out.println("Button Pressed");

        Thread myThread = new Thread() {
            @Override
            public void run() {
                try {

                    Thread.sleep(500);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        myThread.start();
    }
    */
}
