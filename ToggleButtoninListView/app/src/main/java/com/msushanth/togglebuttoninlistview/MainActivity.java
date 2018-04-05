package com.msushanth.togglebuttoninlistview;

import android.os.Bundle;
import android.app.ListActivity;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;




public class MyListViewWithToggle extends ListActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(getApplicationContext(),R.layout.activity_my_list_view_with_toggle,R.id.textView1,getResources().getStringArray(R.array.Strings));
        setListAdapter(adapter);

    }
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        String item = (String) getListAdapter().getItem(position);
        Toast.makeText(this, item+" selected", Toast.LENGTH_LONG).show();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_my_list_view_with_toggle, menu);
        return true;
    }
}
