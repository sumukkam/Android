package com.msushanth.trackdistance;


import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.SphericalUtil;


public class MyServices extends Service implements LocationListener {

    private LocationManager locationManager;
    private String provider;




    @Override
    public void onCreate() {
        super.onCreate();

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        provider = locationManager.getBestProvider(new Criteria(), false);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(provider, 3000, 5, this);
    }




    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }




    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //This service will run until we stop it
        //START_STICKY will keep running the service going until it is explicitly stopped
        return START_STICKY;
    }




    @Override
    public void onDestroy() {
        super.onDestroy();


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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
    }




    // Close the background service when the app is explicitly removed
    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);

        this.stopSelf();
    }




    @Override
    public void onLocationChanged(Location location) {
        MapsActivity.path.add(location);

        if(MapsActivity.path == null || MapsActivity.path.size() < 2) {
            return;
        }
        else{
            MapsActivity.totalDistance += SphericalUtil.computeDistanceBetween(
                    new LatLng(MapsActivity.path.get(MapsActivity.path.size()-1).getLatitude(), MapsActivity.path.get(MapsActivity.path.size()-1).getLongitude()),
                    new LatLng(MapsActivity.path.get(MapsActivity.path.size()-2).getLatitude(), MapsActivity.path.get(MapsActivity.path.size()-2).getLongitude())
            );
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
}
