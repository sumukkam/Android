package com.msushanth.multipleactivitiesdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity {


    private String name = "";


    // problem here is that it will create a new menu and not use the old one again (ex: when pressing the back button)
    public void goBack(View view) {
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        //get the intent that ended us up in this activity
        Intent i = getIntent();
        name = i.getStringExtra("name");

        TextView textView = (TextView) findViewById(R.id.textView);
        textView.setText("Hello " + name);
    }
}
