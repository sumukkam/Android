package com.msushanth.mathbraintrainer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.test.ViewAsserts;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button startButton;



    public void start(View view) {
        Log.i("Start Button", "Start Button Pressed");
        Intent brainTeaserGame = new Intent(getApplicationContext(), Main2Activity.class);
        startActivity(brainTeaserGame);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startButton = (Button) findViewById(R.id.startButton);
    }
}
