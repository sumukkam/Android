package com.msushanth.multipleactivitiesdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    private String username = "Rob";


    /*
    public void changeActivity(View view) {
        Intent i = new Intent(getApplicationContext(), Main2Activity.class);

        //passing variables between activities
        i.putExtra("username", username);

        startActivity(i);
    }
    */




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = (ListView) findViewById(R.id.listView);

        final ArrayList<String> friends = new ArrayList<String>();

        friends.add("Monica");
        friends.add("Rob");
        friends.add("Joe");
        friends.add("Mama");

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, friends);

        listView.setAdapter(arrayAdapter);


        // problem here is that it will create a new menu and not use the old one again (ex: when pressing the back button)
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), Main2Activity.class);
                intent.putExtra("name", friends.get(i));

                startActivity(intent);
            }
        });

    }
}
