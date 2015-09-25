package com.example.markus.locationbasedadventure;

import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Random;

public class MapsActivity extends FragmentActivity implements LocationListener{

    private static final int NUM_ENEMIES = 5;
    private static final int LOC_UPDATE_TIME = 1000;

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.

    private String provider;
    private LocationManager locationManager;
    private Location location;
    private Criteria cr;

    private double lat, lng;
    private LatLng latLng;

    private LatLng[] enemies = new LatLng[NUM_ENEMIES];
    private boolean enemiesSet = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
    }

    @Override
    protected void onStart() {
        super.onStart();
        setUpMapIfNeeded();
    }

    @Override
    protected void onPause() {
        super.onPause();
        locationManager.removeUpdates(this);
    }


    @Override
    protected void onResume() {
        super.onResume();
        locationManager.requestLocationUpdates(provider, LOC_UPDATE_TIME, 0, this);
    }


    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }


    private void setUpMap() {
        mMap.setMyLocationEnabled(true);
        //mMap.getUiSettings().setAllGesturesEnabled(false);
        mMap.getUiSettings().setMyLocationButtonEnabled(false);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        cr = new Criteria();

        provider = locationManager.getBestProvider(cr, true);

        location = locationManager.getLastKnownLocation(provider);

        if(location!=null){
            onLocationChanged(location);
        }
        locationManager.requestLocationUpdates(provider, 1000, 0, this);

    }

    private void setupEnemies() {
        for(int i = 0; i < NUM_ENEMIES; i++) {
            enemies[i] = getRandomLoc();
            mMap.addMarker(new MarkerOptions().position(enemies[i]).title("Marker").snippet("Snippet"));
        }
        enemiesSet = true;
    }

    private LatLng getRandomLoc() {
        LatLng result;
        double rangeMin = -0.001;
        double rangeMax = 0.001;
        double elat = generateRandomDouble(rangeMin, rangeMax) + lat;
        double elng = generateRandomDouble(rangeMin, rangeMax) + lng;
        result = new LatLng(elat, elng);
        return result;
    }

    private double generateRandomDouble(double rangeMin, double rangeMax) {
        Random r = new Random();
        double randomValue = rangeMin + (rangeMax - rangeMin) * r.nextDouble();
        return randomValue;
    }


    private void startBattle() {
        Intent battleIntent = new Intent(this, BattleActivity.class);
        startActivity(battleIntent);

    }

    private void detectEnemy() {
        for(int i = 0; i < NUM_ENEMIES; i++) {
            float [] result = new float[1];
            Location.distanceBetween(lat, lng, enemies[i].latitude, enemies[i].longitude, result);
            if(result[0] <= 3) {
                startBattle();
            }
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        lat = location.getLatitude();
        lng = location.getLongitude();
        latLng = new LatLng(lat, lng);

        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

        mMap.animateCamera(CameraUpdateFactory.zoomTo(18));

        if(!enemiesSet) {
            setupEnemies();
        } else if (enemiesSet) {
            detectEnemy();
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
