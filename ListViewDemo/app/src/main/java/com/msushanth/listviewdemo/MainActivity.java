package com.msushanth.listviewdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView friendsListView = (ListView) findViewById(R.id.myListView);

        final ArrayList<String> friends = new ArrayList<String>(asList("John", "Paul", "George", "Ringo"));

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, friends);
        friendsListView.setAdapter(arrayAdapter);

        friendsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext(), "Hello " + friends.get(i), Toast.LENGTH_SHORT).show();
            }
        });



        /*
        ListView myListView = (ListView) findViewById(R.id.myListView);
        final ArrayList<String> myFamily = new ArrayList<String>();

        myFamily.add("Me");
        myFamily.add("You");
        myFamily.add("Him");
        myFamily.add("Her");
        myFamily.add("It");

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, myFamily);
        myListView.setAdapter(arrayAdapter);

        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //view.setVisibility(View.GONE);
                Log.i("Person Tapped", myFamily.get(i));
            }
        });
        */
    }
}
