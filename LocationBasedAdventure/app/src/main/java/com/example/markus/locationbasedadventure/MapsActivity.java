package com.example.markus.locationbasedadventure;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;

import java.util.Random;

public class MapsActivity extends FragmentActivity implements LocationListener{

    private static final int NUM_ENEMIES = 5;
    private static final int LOC_UPDATE_TIME = 1000;

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.

    private String provider;
    private LocationManager locationManager;

    private double lat, lng;

    private LatLng[] enemies = new LatLng[NUM_ENEMIES];
    private boolean enemiesSet = false;

    //private TextView gpsText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        setUpMapIfNeeded();
        setupUI();
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
        if ( !locationManager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
            buildAlertMessageNoGps();
        }
    }

    private void setupUI() {
        //gpsText = (TextView) findViewById(R.id.gpsText);
        //gpsText.setVisibility(View.VISIBLE);
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
        mMap.getUiSettings().setAllGesturesEnabled(false);
        mMap.getUiSettings().setMyLocationButtonEnabled(false);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        Criteria cr = new Criteria();

        provider = locationManager.getBestProvider(cr, true);

        Location location = locationManager.getLastKnownLocation(provider);

        if(location !=null){
            onLocationChanged(location);
        }

        locationManager.requestLocationUpdates(provider, 1000, 0, this);

    }

    private void buildAlertMessageNoGps() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Die Standortbestimmung ist momentan deaktiviert. Um spielen zu können müssen Sie diese aktivieren. Wollen Sie das nun tun?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        dialog.cancel();
                        startActivity(new Intent(MapsActivity.this, MenueActivity.class));
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    private void setupEnemies() {
        for(int i = 0; i < NUM_ENEMIES; i++) {
            boolean minDistance = false;
            while(!minDistance) {
                enemies[i] = getRandomLoc();
                float [] result = new float[1];
                Location.distanceBetween(lat, lng, enemies[i].latitude, enemies[i].longitude, result);

                if (result[0] > 20.0f) {
                    minDistance = true;
                }
            }
            addCircle(enemies[i]);

        }
        enemiesSet = true;
    }

    private void addCircle(LatLng enemy) {
        CircleOptions circleOptions = new CircleOptions()
                .center(enemy)
                .radius(10)
                .fillColor(Color.argb(150, 255, 0, 0))
                .strokeWidth(3.0f)
                .strokeColor(Color.argb(80, 0, 0, 0));
        Circle cirlce = mMap.addCircle(circleOptions);
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
        return rangeMin + (rangeMax - rangeMin) * r.nextDouble();
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

    private void removeGPSText() {
        //gpsText.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onLocationChanged(Location location) {
        lat = location.getLatitude();
        lng = location.getLongitude();
        LatLng latLng = new LatLng(lat, lng);

        //removeGPSText();
        //Log.d("asd ", "" + gpsText.isShown());
        /*
        if(gpsText.isShown()){
            removeGPSText();
        } */

        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

        mMap.animateCamera(CameraUpdateFactory.zoomTo(18));

        if(!enemiesSet) {
            setupEnemies();
        } else {
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