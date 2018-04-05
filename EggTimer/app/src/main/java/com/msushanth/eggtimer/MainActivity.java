package com.msushanth.eggtimer;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    SeekBar timerSeekBar;
    TextView timerTextView;
    Button controllerButton;
    boolean counterIsActive = false;
    CountDownTimer countDownTimer;


    public void updateTimer(int secondsLeft) {
        int minutes = (int) (secondsLeft/60);
        int seconds = secondsLeft - minutes * 60;

        if(seconds < 10) {
            timerTextView.setText(Integer.toString(minutes) + ":0" + Integer.toString(seconds));

        }
        else {
            timerTextView.setText(Integer.toString(minutes) + ":" + Integer.toString(seconds));
        }
    }



    public void resetAll() {
        timerTextView.setText("0:30");
        timerSeekBar.setEnabled(true);
        timerSeekBar.setProgress(30);
        countDownTimer.cancel();
        controllerButton.setText("Go!");
        counterIsActive = false;
    }




    public void controlTimer(View view) {
        //Log.i("Buton Pressed", "Pressed");
        if(counterIsActive == false) {
            counterIsActive = true;
            timerSeekBar.setEnabled(false);
            controllerButton.setText("Stop");

            countDownTimer = new CountDownTimer(timerSeekBar.getProgress() * 1000 + 100, 1000) {
                @Override
                public void onTick(long l) {
                    updateTimer((int) l / 1000);
                }

                @Override
                public void onFinish() {
                    timerTextView.setText("0:00");

                    MediaPlayer airHorn = MediaPlayer.create(getApplicationContext(), R.raw.air_horn);
                    airHorn.start();
                    resetAll();
                }
            }.start();
        }
        else {
            resetAll();
        }
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        controllerButton = (Button) findViewById(R.id.controllerButton);

        timerSeekBar = (SeekBar) findViewById(R.id.timerSeekBar);
        timerSeekBar.setMax(600);
        timerSeekBar.setProgress(30);

        timerTextView = (TextView) findViewById(R.id.timerTextview);

        timerSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                updateTimer(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
