package com.msushanth.chesscountdown;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;




public class MainActivity extends AppCompatActivity {



    public void setTimePerTurn(View view) {
        int timePerTurn;
        EditText timePerTurnET = (EditText) findViewById(R.id.timePerTurn);

        if(timePerTurnET.getText().toString().equals("")) {
            return;
        }

        timePerTurn = Integer.parseInt(timePerTurnET.getText().toString());
        if(timePerTurn == 0) {
            return;
        }

        Intent countdownActivity = new Intent(getApplicationContext(), Main2Activity.class);
        countdownActivity.putExtra("timePerTurn", timePerTurn);
        startActivity(countdownActivity);
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
