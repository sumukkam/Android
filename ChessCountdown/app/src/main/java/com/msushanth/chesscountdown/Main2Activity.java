package com.msushanth.chesscountdown;

import android.app.Activity;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Main2Activity extends Activity {

    RelativeLayout relativeLayout;
    TextView timeTextView;
    TextView playerTextView;
    ProgressBar topProgressBar;
    ProgressBar bottomProgressBar;
    int minutes;
    int seconds;
    int totalSeconds;
    boolean whiteIsPlaying;
    CountDownTimer countDownTimer;





    public void updateTimer(int secondsLeft) {
        int minutes = (int) (secondsLeft/60000);
        int seconds = (secondsLeft - (minutes * 60000))/1000;
        int milliseconds = (secondsLeft - (minutes * 60000) - (seconds*1000));

        if(seconds < 10) {
            if(milliseconds < 10) {
                timeTextView.setText(Integer.toString(minutes) + ":0" + Integer.toString(seconds) + ".00" + Integer.toString(milliseconds));
            }
            else if(milliseconds < 100) {
                timeTextView.setText(Integer.toString(minutes) + ":0" + Integer.toString(seconds) + ".0" + Integer.toString(milliseconds));
            }
            else {
                timeTextView.setText(Integer.toString(minutes) + ":0" + Integer.toString(seconds) + "." + Integer.toString(milliseconds));
            }
        }
        else {
            if(milliseconds < 10) {
                timeTextView.setText(Integer.toString(minutes) + ":" + Integer.toString(seconds) + ".00" + Integer.toString(milliseconds));
            }
            else if(milliseconds < 100) {
                timeTextView.setText(Integer.toString(minutes) + ":" + Integer.toString(seconds) + ".0" + Integer.toString(milliseconds));
            }
            else {
                timeTextView.setText(Integer.toString(minutes) + ":" + Integer.toString(seconds) + "." + Integer.toString(milliseconds));
            }
        }
    }




    public void updateProgressBar(int milliSecondsLeft) {
        topProgressBar.setProgress(milliSecondsLeft);
        bottomProgressBar.setProgress((totalSeconds*1000) - milliSecondsLeft);
    }




    public void resetProgressBar() {
        topProgressBar.setMax(totalSeconds*1000);
        topProgressBar.setProgress(totalSeconds*1000);

        bottomProgressBar.setMax(totalSeconds*1000);
        bottomProgressBar.setProgress(totalSeconds*1000);
    }




    public void whitePlayerPlaying() {
        relativeLayout.setBackgroundColor(Color.parseColor("#FFFFFF"));
        playerTextView.setText("Whites Turn");
        timeTextView.setTextColor(Color.parseColor("#000000"));
        playerTextView.setTextColor(Color.parseColor("#000000"));
        topProgressBar.setProgressBackgroundTintList(ColorStateList.valueOf(Color.WHITE));
        topProgressBar.setProgressTintList(ColorStateList.valueOf(Color.BLACK));
        bottomProgressBar.setProgressBackgroundTintList(ColorStateList.valueOf(Color.BLACK));
        bottomProgressBar.setProgressTintList(ColorStateList.valueOf(Color.WHITE));
    }




    public void blackPlayerPlaying() {
        relativeLayout.setBackgroundColor(Color.parseColor("#000000"));
        playerTextView.setText("Blacks Turn");
        timeTextView.setTextColor(Color.parseColor("#FFFFFF"));
        playerTextView.setTextColor(Color.parseColor("#FFFFFF"));
        topProgressBar.setProgressBackgroundTintList(ColorStateList.valueOf(Color.BLACK));
        topProgressBar.setProgressTintList(ColorStateList.valueOf(Color.WHITE));
        bottomProgressBar.setProgressBackgroundTintList(ColorStateList.valueOf(Color.WHITE));
        bottomProgressBar.setProgressTintList(ColorStateList.valueOf(Color.BLACK));
    }




    public void changePlayer(View view) {
        if(whiteIsPlaying) {
            whitePlayerPlaying();
            countDownTimer.cancel();
            runCountDown();
            whiteIsPlaying = false;
        }
        else {
            blackPlayerPlaying();
            countDownTimer.cancel();
            runCountDown();
            whiteIsPlaying = true;
        }
    }



    public void runCountDown() {
        countDownTimer = new CountDownTimer(totalSeconds * 1000 + 200, 50) {
            @Override
            public void onTick(long l) {
                if(l < totalSeconds*1000) {
                    updateTimer((int) l);
                    updateProgressBar((int) l);
                }
                else {
                    updateTimer((int) totalSeconds*1000);
                    updateProgressBar((int) totalSeconds*1000);
                }
            }

            @Override
            public void onFinish() {
                timeTextView.setText("0:00.000");
                updateProgressBar(0);

                MediaPlayer airHorn = MediaPlayer.create(getApplicationContext(), R.raw.air_horn);
                airHorn.start();
            }
        }.start();
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        relativeLayout = (RelativeLayout) findViewById(R.id.mainLayout);

        totalSeconds = getIntent().getIntExtra("timePerTurn", 30);
        //milliSecondsLeft = totalSeconds;
        minutes = (int) (totalSeconds/60);
        seconds = totalSeconds - minutes * 60;
        timeTextView = (TextView) findViewById(R.id.timeTextView);
        timeTextView.setText(Integer.toString(minutes) + ":" + Integer.toString(seconds));

        playerTextView = (TextView) findViewById(R.id.playerTextView);

        topProgressBar = (ProgressBar) findViewById(R.id.topProgressBar);
        bottomProgressBar = (ProgressBar) findViewById(R.id.bottomProgressBar);

        resetProgressBar();
        whitePlayerPlaying();
        whiteIsPlaying = false;
        runCountDown();
    }
}
