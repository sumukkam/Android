package com.msushanth.headphonetest;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {



    TextView headsetStatus;
    TextView musicStatus;
    TextView speakerStatus;

    AudioManager am1;




    private Timer timer;
    private TimerTask timerTask = new TimerTask() {

        @Override
        public void run() {
            Log.i("WiredHeadsetOn = ", am1.isWiredHeadsetOn()+"");
            Log.i("MusicActive = ", am1.isMusicActive()+"");
            Log.i("SpeakerphoneOn = ", am1.isSpeakerphoneOn()+"");

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    headsetStatus.setText("Headset Status: " + am1.isWiredHeadsetOn());
                    musicStatus.setText("Music Status: " + am1.isMusicActive());
                    speakerStatus.setText("Speaker Phone Status: " + am1.isSpeakerphoneOn());
                }
            });
        }
    };

    public void start() {
        if(timer != null) {
            return;
        }
        timer = new Timer();
        timer.scheduleAtFixedRate(timerTask, 0, 100);
    }

    public void stop() {
        timer.cancel();
        timer = null;
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        headsetStatus = (TextView) findViewById(R.id.headsetStatus);
        musicStatus = (TextView) findViewById(R.id.musicStatus);
        speakerStatus = (TextView) findViewById(R.id.speakerStatus);


        am1 = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
        //Log.i("WiredHeadsetOn = ", am1.isWiredHeadsetOn()+"");
        //Log.i("MusicActive = ", am1.isMusicActive()+"");
        //Log.i("SpeakerphoneOn = ", am1.isSpeakerphoneOn()+"");


        headsetStatus.setText("Headset Status: " + am1.isWiredHeadsetOn());
        musicStatus.setText("Music Status: " + am1.isMusicActive());
        speakerStatus.setText("Speaker Phone Status: " + am1.isSpeakerphoneOn());

        start();
    }
}
