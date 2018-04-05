package com.msushanth.trackdistance;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import com.google.maps.android.SphericalUtil;

import java.util.ArrayList;




// http://stackoverflow.com/questions/16262837/how-to-draw-route-in-google-maps-api-v2-from-my-location
// https://developers.google.com/maps/documentation/android-api/shapes
// Polyline PolylineOptions




public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, LocationListener {

    private DrawerLayout drawerLayout;
    private ListView leftDrawer;
    private String[] leftDrawerOptions;


    private static final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private GoogleMap mMap;

    private LocationManager locationManager;
    private String provider;

    private Polyline polylinePath = null;
    static ArrayList<Location> path = new ArrayList<Location>();

    static double totalDistance = 0;

    TextView distanceTextView;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


/*
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        leftDrawerOptions = getResources().getStringArray(R.array.leftDrawerOptions);
        leftDrawer = (ListView) findViewById(R.id.left_drawer);
        leftDrawer.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, leftDrawerOptions));


        createDrawerHeader();


        leftDrawer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            //leftDrawer.getChildAt(0).setBackgroundColor(Color.parseColor("#303F9F"));
            //leftDrawer.getChildAt(0)

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                if(position != 0) {
                    runSelectedPosition(position);
                }
            }
        });
*/


        distanceTextView = (TextView) findViewById(R.id.distanceTextView);

        // Ask the user for location permissions
        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION},
                        MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        provider = locationManager.getBestProvider(new Criteria(), false);
    }




    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(provider, 400, 1, this);

        //creates a blue dot for your current location
        mMap.setMyLocationEnabled(true);

        //place a marker on the current location
        Location currentLocation = locationManager.getLastKnownLocation(provider);
        LatLng currentPosition = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentPosition, 15));
        path.add(currentLocation);
    }




    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {

        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Intent intent = new Intent(this, MapsActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish(); // Call once you redirect to another activity

                }
                else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }

    }




    @Override
    public void onLocationChanged(Location location) {
        path.add(location);
        drawPrimaryLinePath(path);

        if(path == null || path.size() < 2) {
            return;
        }
        else{
            totalDistance += SphericalUtil.computeDistanceBetween(
                    new LatLng(path.get(path.size()-1).getLatitude(), path.get(path.size()-1).getLongitude()),
                    new LatLng(path.get(path.size()-2).getLatitude(), path.get(path.size()-2).getLongitude())
            );

            distanceTextView.setText("Total distance: " + String.format("%.2f", totalDistance) + " m");
        }
    }




    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }




    @Override
    public void onProviderEnabled(String s) {

    }




    @Override
    public void onProviderDisabled(String s) {

    }




    @Override
    protected void onResume() {
        super.onResume();

        distanceTextView.setText("Total distance: " + String.format("%.2f", totalDistance) + " m");

        stopService(new Intent(getBaseContext(), MyServices.class));
        drawPrimaryLinePath(path);

        //-1 means there was an error getting the location clicked on
        //0 means the user wanted to add a new marker
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(provider, 1000, 1, this);
        onLocationChanged(locationManager.getLastKnownLocation(provider));
    }




    @Override
    protected void onPause() {
        super.onPause();

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.removeUpdates(this);

        startService(new Intent(getBaseContext(), MyServices.class));
    }




    private void drawPrimaryLinePath( ArrayList<Location> listLocsToDraw ) {

        if ( mMap == null ) {
            return;
        }

        if ( listLocsToDraw == null || listLocsToDraw.size() < 2 ) {
            return;
        }

        if(polylinePath != null) {
            polylinePath.remove();
        }

        PolylineOptions pathLine = new PolylineOptions();

        pathLine.color( Color.parseColor( "#CC0000FF" ) );
        pathLine.width( 5 );
        pathLine.visible( true );

        for ( Location locRecorded : listLocsToDraw ) {
            pathLine.add( new LatLng( locRecorded.getLatitude(), locRecorded.getLongitude() ) );
        }

        polylinePath = mMap.addPolyline( pathLine );

    }




    private void createDrawerHeader() {
        TextView drawerHeader = new TextView(getApplicationContext());
        drawerHeader.setText("Select an Option");
        drawerHeader.setTextColor(Color.WHITE);
        drawerHeader.setBackgroundColor(Color.parseColor("#303F9F"));
        drawerHeader.setTextSize(28);
        drawerHeader.setGravity(Gravity.CENTER);
        drawerHeader.setHeight(190);
        leftDrawer.addHeaderView(drawerHeader);
    }




    private void runSelectedPosition(int position) {
        //Toast.makeText(getApplicationContext(), "Item " + position + " was selected", Toast.LENGTH_SHORT).show();
        leftDrawer.setItemChecked(position, true);
        drawerLayout.closeDrawer(leftDrawer);
    }
}
