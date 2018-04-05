package com.msushanth.mystocks1;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.OnChartGestureListener;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class DisplayGraph extends Activity {

    LineChart lineChart;
    TextView stockName;
    TextView currentValue;
    TextView minMax;
    TextView amountGainedLost;

    String CURRENT_VALUE="Current Value";
    float pricePaid = 100.0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_graph);
        stockName = (TextView) findViewById(R.id.stockName);
        currentValue = (TextView) findViewById(R.id.currentValue);
        minMax = (TextView) findViewById(R.id.minMax);
        amountGainedLost = (TextView) findViewById(R.id.amountGainedLost);
        lineChart = (LineChart) findViewById(R.id.lineChart);

        // Get data from MainActivity
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        final String STOCK_NAME = extras.getString("Stock Name");
        final String[] STOCK = extras.getStringArray("Stock Data");
        CURRENT_VALUE = extras.getString("Current Value");

        stockName.setText(STOCK_NAME);
        currentValue.setText(CURRENT_VALUE);

        // Get historical closing prices for the selected stock
        ArrayList<String> stockClosingPrices = new ArrayList<>();
        for(int i=STOCK.length-1; i>0; i--) {
            if(STOCK[i].equals("1. open")) {
                stockClosingPrices.add(STOCK[i+1]);
            }
        }

        /*
        for(String str : stockClosingPrices) {
            System.out.println(str);
        }
        */

        final int NUM_DATA_POINTS = stockClosingPrices.size();
        final int NUM_EXTRA_X_AXIS = 10;
        String[] xAxis = new String[NUM_DATA_POINTS+NUM_EXTRA_X_AXIS];
        ArrayList<Entry> yAxis = new ArrayList<>();

        // Add data points to Arrays
        for(int i=0; i<NUM_DATA_POINTS; i++) {
            yAxis.add(new Entry(Float.parseFloat(stockClosingPrices.get(i)),i));
            xAxis[i] = String.valueOf(i);
        }
        for(int i=NUM_DATA_POINTS; i<NUM_DATA_POINTS+NUM_EXTRA_X_AXIS; i++) {
            xAxis[i] = String.valueOf(i);
        }

        // Dotted line for days open price
        // TODO: change this to price paid when buying the stock
        ArrayList<Entry> dayOpenYAxis = new ArrayList<>();
        dayOpenYAxis.add(new Entry(pricePaid, 0));
        dayOpenYAxis.add(new Entry(pricePaid, NUM_DATA_POINTS+30));
        //dayOpenYAxis.add(new Entry(Float.parseFloat(stockClosingPrices.get(/*opening price*/)),0));
        //dayOpenYAxis.add(new Entry(Float.parseFloat(stockClosingPrices.get(/*opening price*/)),NUM_DATA_POINTS+30));
        LineDataSet dayOpenLineDataSet = new LineDataSet(dayOpenYAxis, "Price Paid");
        dayOpenLineDataSet.setLineWidth(2f);
        dayOpenLineDataSet.setColor(Color.parseColor("#999999"));
        dayOpenLineDataSet.setDrawCircles(false);
        dayOpenLineDataSet.enableDashedLine(15f,15f,0f);

        // List of the points to plot on graph
        LineDataSet lineDataSet = new LineDataSet(yAxis, STOCK_NAME); // y values, label
        lineDataSet.setLineWidth(3f);
        lineDataSet.setColor(Color.parseColor("#2c3e50")); // Dark Blue for color of line
        lineDataSet.setDrawCircles(false); // Don't draw circles
        lineDataSet.setDrawFilled(true); // To highlight the area under the graph
        lineDataSet.setFillColor(Color.parseColor("#333333")); // Shade Gray over area under graph

        // Plot data on line chart
        if(Float.parseFloat(stockClosingPrices.get(NUM_DATA_POINTS-1)) > pricePaid) {
            lineChart.setBackgroundColor(Color.parseColor("#129933")); // Green
        } else {
            lineChart.setBackgroundColor(Color.parseColor("#aa2c1e")); // Red
        }
        lineChart.setDrawGridBackground(false);
        lineChart.setDescription("Pinch to zoom. Touch for more information.");
        lineChart.setDrawBorder(false);
        lineChart.setPinchZoom(true);
        lineChart.setDoubleTapToZoomEnabled(false);
        lineChart.animateY(1500);
        lineChart.setDrawYValues(false);
        lineChart.setDrawXLabels(false);
        lineChart.setOnChartGestureListener(new OnChartGestureListener() {

            @Override
            public void onChartSingleTapped(MotionEvent me) {
            }

            @Override
            public void onChartLongPressed(MotionEvent me) {
                lineChart.fitScreen();
            }

            @Override
            public void onChartFling(MotionEvent me1, MotionEvent me2,
                                     float velocityX, float velocityY) {
            }

            @Override
            public void onChartDoubleTapped(MotionEvent me) {
            }
        });
        //lineChart.zoom(2f,2f,(float) NUM_DATA_POINTS,Float.parseFloat(stockClosingPrices.get(NUM_DATA_POINTS-1))); // complete this

        ArrayList<LineDataSet> lines = new ArrayList<> ();
        lines.add(lineDataSet);
        lines.add(dayOpenLineDataSet);
        lineChart.setData(new LineData(xAxis,lines));
    }
}
