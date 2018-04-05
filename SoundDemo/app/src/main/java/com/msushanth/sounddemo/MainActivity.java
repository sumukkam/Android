package com.msushanth.sounddemo;

import android.content.Context;
import android.media.AudioManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    MediaPlayer mPlayer;
    AudioManager audioManager;
    SeekBar volumeControl;
    SeekBar scrubber;

    int currentVolume;

    public void playAudio(View view) {
        mPlayer.start();
    }


    public void pauseAudio(View view) {
        mPlayer.pause();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPlayer = MediaPlayer.create(this, R.raw.air_horn);

        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        int maxVolume = audioManager.getStreamMaxVolume(audioManager.STREAM_MUSIC);
        currentVolume = audioManager.getStreamVolume(audioManager.STREAM_MUSIC);

        volumeControl = (SeekBar) findViewById(R.id.volumeSeekBar);
        volumeControl.setMax(maxVolume);
        volumeControl.setProgress(currentVolume);

        volumeControl.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                //Log.i("SeekBar Value", Integer.toString(i));
                audioManager.setStreamVolume(audioManager.STREAM_MUSIC, i, 0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });



        scrubber = (SeekBar) findViewById(R.id.scrubberSeekBar);
        scrubber.setMax(mPlayer.getDuration());

        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                scrubber.setProgress(mPlayer.getCurrentPosition());
            }
        }, 0, 1000);

        scrubber.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                //Log.i("SeekBar Value", Integer.toString(i));
                mPlayer.seekTo(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }



    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        currentVolume = audioManager.getStreamVolume(audioManager.STREAM_MUSIC);
        volumeControl.setProgress(currentVolume);
    }
}
