package com.msushanth.soundboard;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    MediaPlayer mplayer;

    public void buttonTapped(View view) {
        int id = view.getId();
        String ourId = "";
        ourId = view.getResources().getResourceEntryName(id);

        int resourceID = getResources().getIdentifier(ourId, "raw", "com.msushanth.soundboard");

        mplayer = MediaPlayer.create(this, resourceID);
        mplayer.start();

        /*
         * Line of code which will set up a player for an online piece of audio
         * MediaPlayer player = MediaPlayer.create(this, Uri.parse("http://www.example.com/test.mp3"));
         */

        Log.i("Button Tapped", ourId);
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
