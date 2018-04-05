package com.msushanth.mystocks1;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.sleep;
import static java.util.Arrays.asList;

public class MainActivity extends AppCompatActivity {

    Intent passOnData;
    ListView stocksListView;
    ImageButton addButton;

    FileOutputStream outputFile;
    OutputStreamWriter outputWriter;
    FileInputStream fileIn;
    InputStreamReader inputRead;
    String stocksToSave="";
    String dataRead="";

    final String CURRENT_STOCK_PRICE_BASE_URL = "https://www.google.com/finance/info?q=NASDAQ:";
    String stockToAddStr="";
    String[] stocksToAddArr = {"Click the + to add a stock"};
    String[] stocksToAddCurrValueArr = {""};
    int NUM_STOCKS_TO_LOAD=0;
    boolean stockExists = false;
    boolean tookTooLong = false;


    AlertDialog.Builder alert;
    EditText input;
    AlertDialog alertDialog;
    private DrawerLayout mDrawerLayout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        passOnData = new Intent(getApplicationContext(), SplashScreen.class);


        // Get data from memory
        try {
            outputFile = openFileOutput("stocksToSave.txt", MODE_APPEND);
            outputWriter = new OutputStreamWriter(outputFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // Save data to memory
        try {
            fileIn = openFileInput("stocksToSave.txt");
            inputRead= new InputStreamReader(fileIn);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        readFromFile();
        stocksToSave = dataRead;
        // Get the stocks loaded in SplashScreen
        // should be in format NAME,CURRENT_VALUE,NAME,CURRENT_VALUE...
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            NUM_STOCKS_TO_LOAD = extras.getInt("0");
            System.out.println("@$@#$#$NUM_STOCKS_TO_LOAD: " + NUM_STOCKS_TO_LOAD);

            for(int i=1; i<=NUM_STOCKS_TO_LOAD; i++) {
                String str = extras.getString(String.valueOf(i));
                //stocksToSave = stocksToSave + str;
                stocksToSave = str;
                if(i != NUM_STOCKS_TO_LOAD) {
                    stocksToSave = stocksToSave + ",";
                }
            }
        }

        if(NUM_STOCKS_TO_LOAD != 0) {
            System.out.println("@$@#$#$: " + stocksToSave);
            String[] temp = stocksToSave.split(",");
            stocksToAddArr = new String[temp.length / 2];
            stocksToAddCurrValueArr = new String[temp.length / 2];
            for (int i = 0; i < temp.length; i++) {
                if (i % 2 == 0) {
                    stocksToAddArr[i / 2] = temp[i];
                } else {
                    stocksToAddCurrValueArr[i / 2] = temp[i];
                }
            }
        }



        // Adding Toolbar to Main screen
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Setting ViewPager for each Tabs
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        // Set Tabs inside Toolbar
        TabLayout tabs = (TabLayout) findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        // Create Navigation drawer and inlfate layout
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);

        // Adding menu icon to Toolbar
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            VectorDrawableCompat indicator
                    = VectorDrawableCompat.create(getResources(), R.drawable.ic_menu, getTheme());
            indicator.setTint(ResourcesCompat.getColor(getResources(),R.color.white,getTheme()));
            supportActionBar.setHomeAsUpIndicator(indicator);
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        }

        // Set behavior of Navigation drawer
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    // This method will trigger on item Click of navigation menu
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // Set item in checked state
                        menuItem.setChecked(true);

                        // TODO: handle navigation

                        // Closing drawer on item click
                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                });


        // Adding Floating Action Button to bottom right of main view
        addButton = (ImageButton) findViewById(R.id.button);
        addButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    addButton.setBackground(getResources().getDrawable(R.drawable.floating_add_pressed));
                }
                if(motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    addButton.setBackground(getResources().getDrawable(R.drawable.floating_add));
                    //Snackbar.make(view, "This button does nothing! (for now)", Snackbar.LENGTH_LONG).show();
                    popupEnterStock(view);
                }
                return false;
            }
        });
    }




    // Add Fragments to Tabs
    private void setupViewPager(ViewPager viewPager) {
        Adapter adapter = new Adapter(getSupportFragmentManager());
        adapter.addFragment(new OwnListContentFragment(), "Own");
        adapter.addFragment(new FollowListContentFragment(), "Follow");
        //adapter.addFragment(new CardContentFragment(), "Card");
        viewPager.setAdapter(adapter);
    }




    static class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public Adapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        } else if (id == android.R.id.home) {
            mDrawerLayout.openDrawer(GravityCompat.START);
        }
        return super.onOptionsItemSelected(item);
    }




    private void popupEnterStock(View view) {
        alert = new AlertDialog.Builder(MainActivity.this);
        input = new EditText(MainActivity.this);
        final View v = view;

        alert.setView(input);

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                System.out.println("Ok button clicked");
                stockToAddStr = input.getText().toString();
                boolean exists = false;

                if(!stockToAddStr.equals("")) {
                    checkIfStockExists(v);
                }
                /*
                if(exists) {
                    stocksToSave += "," + stockToAddStr;
                }

                System.out.println("#$@#$" + stocksToSave);
                */
            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                System.out.println("Cancel button clicked");
            }
        });

        alert.setTitle("Add a Stock");
        alert.setMessage("Enter the symbol of the stock you want");
        alertDialog = alert.create();
        alertDialog.show();
    }



    // TODO: Show a loading popup animation during this check
    // TODO: tookTooLong will return true if it does not get the response in time so have a pop up that says "error took too long. try again"
    public void checkIfStockExists(View view) {

        // Wait 5 seconds for response after calling AsyncTask
        try {
            stockExists = false;
            tookTooLong = true;
            String checkStockURL = CURRENT_STOCK_PRICE_BASE_URL + stockToAddStr;

            checkIfStockExists cISE = new checkIfStockExists();
            cISE.setView(view);
            cISE.execute(checkStockURL);
            //new checkIfStockExists().execute(checkStockURL);
            //Thread.sleep(5000);
        } catch (Exception e) {
            e.printStackTrace();
        }

        /*
        System.out.println("Check Completed");
        System.out.println("Does stock exist: " + stockExists);
        if(stockExists) {
            Snackbar.make(view, stockToAddStr + " has been added.", Snackbar.LENGTH_LONG).show();
        } else {
            Snackbar.make(view, "This stock does not exist.", Snackbar.LENGTH_LONG).show();
        }
        */
    }


    public class checkIfStockExists extends AsyncTask<String, Void, Void> {
        View view = null;
        ProgressDialog progressDialog = null;

        protected void setView(View v) {
            view = v;
        }

        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(view.getContext(), AlertDialog.THEME_DEVICE_DEFAULT_DARK);
            progressDialog.setTitle("Loading Information");
            progressDialog.setMessage("Please wait");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        // TODO: Try with the other URL (NYSE)
        @Override
        protected Void doInBackground(String... string) {

            try {
                sleep(2000);
                Document document = Jsoup
                        .connect(string[0])
                        .ignoreContentType(true)
                        .get();

                stockExists = true;
            } catch (Exception e) {
                stockExists = false;
                e.printStackTrace();
            }

            tookTooLong = false;
            return null;
        }

        @Override
        protected void onPostExecute(Void voids) {
            progressDialog.dismiss();

            System.out.println("Check Completed");
            System.out.println("Does stock exist: " + stockExists);
            if(stockExists) {
                if(NUM_STOCKS_TO_LOAD != 0) {
                    stockToAddStr += ",";
                }
                stocksToSave += stockToAddStr;
                writeToFile();
                readFromFile();
                System.out.println("*&@^$@*&#^ Data Saved: " + dataRead);
                System.out.println("*&@^$@*&#^ Stocks Saved: " + stocksToSave);
                Snackbar.make(view, stockToAddStr + " has been added. Restarting.", Snackbar.LENGTH_LONG).show();
                try {
                    sleep(2000);
                    startActivity(passOnData);
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                Snackbar.make(view, "This stock does not exist.", Snackbar.LENGTH_LONG).show();
            }
        }
    }


    // Write text to file
    public void writeToFile() {
        try {
            outputWriter.append(stocksToSave);
            outputWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Read text from file
    public void readFromFile() {
        try {
            int content;
            while ((content = fileIn.read()) != -1) {
                dataRead += (char) content;
            }
            //inputRead.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public String[] getStocksToAddArr() {
        return stocksToAddArr;
    }

    public String[] getStocksToAddCurrValueArr() {
        return stocksToAddCurrValueArr;
    }

}
