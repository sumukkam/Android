package com.msushanth.locationinfo;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.renderscript.Double2;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;
import java.util.Locale;




/*

https://github.com/googlesamples/android-RuntimePermissions/blob/master/Application/src/main/java/com/example/android/system/runtimepermissions/MainActivity.java
http://android-developers.blogspot.in/2015/08/building-better-apps-with-runtime.html
https://material.google.com/patterns/permissions.html?utm_campaign=runtime-permissions-827&utm_source=dac&utm_medium=blog#permissions-request-patterns
https://developer.android.com/training/permissions/requesting.html
http://stackoverflow.com/questions/38285653/accessing-user-location-in-api-23-throws-security-exception-though-permissions-a
https://developer.android.com/training/location/change-location-settings.html
http://stackoverflow.com/questions/11411395/how-to-get-current-foreground-activity-context-in-android
https://www.google.com/webhp?sourceid=chrome-instant&ion=1&espv=2&ie=UTF-8#q=getting%20location%20permissions%20android

*/




public class MainActivity extends Activity implements LocationListener {

    private static final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;

    LocationManager locationManager = null;
    Location location = null;
    String provider = "";
    TextView latTextView;
    TextView lngTextView;
    TextView altTextView;
    TextView bearingTextView;
    TextView speedTextView;
    TextView accuracyTextView;
    TextView addressTextView;


    /*
     * When app has been put into the background, then reactivated
     */
    @Override
    protected void onResume() {
        super.onResume();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            Log.i("Debugging", "Failed at onResume");
            return;
        }

        if (locationManager == null) {
            return;
        } else {
            locationManager.requestLocationUpdates(provider, 400, 1, this);
        }
    }


    /*
     * When app has been put into the background
     */
    @Override
    protected void onPause() {
        super.onPause();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            Log.i("Debugging", "Failed at onPause");
            return;
        }

        if (locationManager == null) {
            return;
        } else {
            locationManager.removeUpdates(this);
        }
    }


    /*
     * Gives location as soon as location has been updated
     */
    @Override
    public void onLocationChanged(Location location) {

        Double lat = 0.0;
        Double lng = 0.0;
        Double alt = 0.0;
        float bearing = 0;
        float speed = 0;
        float accuracy = 0;

        if (location != null) {
            lat = location.getLatitude();
            lng = location.getLongitude();
            alt = location.getAltitude();
            bearing = location.getBearing();
            speed = location.getSpeed();
            accuracy = location.getAccuracy();
        }


        Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
        try {
            //only want 1 result
            List<Address> listAddresses = geocoder.getFromLocation(lat, lng, 1);

            if (listAddresses != null && listAddresses.size() > 0) {
                //Log.i("Place Info", listAddresses.get(0).toString());


                String addressHolder = "";
                for (int i = 0; i < listAddresses.get(0).getMaxAddressLineIndex(); i++) {
                    addressHolder += listAddresses.get(0).getAddressLine(i);
                    if (i != listAddresses.get(0).getMaxAddressLineIndex() - 1) {
                        addressHolder += "\r\n";
                    }
                }

                addressTextView.setText("Address: \r\n" + addressHolder);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        latTextView.setText("Latitude: " + String.format("%.6f", lat) + "°");
        lngTextView.setText("Longitude: " + String.format("%.6f", lng) + "°");
        altTextView.setText("Altitude: " + String.format("%.4f", alt) + " m");
        bearingTextView.setText("Bearing: " + String.format("%.4f", bearing) + "°");
        speedTextView.setText("Speed: " + String.format("%.2f", speed) + " m/s");
        accuracyTextView.setText("Accuracy: " + String.format("%.2f", accuracy) + " m");
        /*
        Log.i("Location Info", "Latitude: " + String.format("%.6f", lat) +
                ", Longitude: " + String.format("%.6f", lng) +
                ", Altitude: " + String.format("%.6f", alt) +
                ", Bearing: " + String.format("%.6f", bearing) +
                ", Speed: " + String.format("%.6f", speed) +
                ", Accuracy: " + String.format("%.6f", accuracy));
        */
    }


    /*
     * When users location becomes available after it has not been available for a while
     */
    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }


    /*
     * When gps has been enabled
     */
    @Override
    public void onProviderEnabled(String s) {

    }


    /*
     * When gps has been disabled
     */
    @Override
    public void onProviderDisabled(String s) {

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                        MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }


        latTextView = (TextView) findViewById(R.id.latitude);
        lngTextView = (TextView) findViewById(R.id.longitude);
        altTextView = (TextView) findViewById(R.id.altitude);
        bearingTextView = (TextView) findViewById(R.id.bearing);
        speedTextView = (TextView) findViewById(R.id.speed);
        accuracyTextView = (TextView) findViewById(R.id.accuracy);
        addressTextView = (TextView) findViewById(R.id.address);


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            Log.i("Debugging", "Failed at onCreate");
            return;
        }
        else {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            provider = locationManager.getBestProvider(new Criteria(), false);
            getLocation(findViewById(R.id.imageView));
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {

        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Intent i = getBaseContext().getPackageManager()
                            .getLaunchIntentForPackage( getBaseContext().getPackageName() );
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);

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




    public void getLocation(View view) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            Log.i("Debugging", "Failed at getLocation");
            return;
        }

        if(locationManager == null) {
            return;
        }
        else {
            location = locationManager.getLastKnownLocation(provider);

            if(location == null) {
                return;
            }
            else {
                onLocationChanged(location);
            }
        }
    }



}
