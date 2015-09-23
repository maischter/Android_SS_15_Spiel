package com.example.markus.locationbasedadventure;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private static final int SECOND = 1000;
    private static final int LOC_UPDATE_INTERVAL = 2 * SECOND;

    protected LocationRequest mLocationRequest;
    protected Location mLocation;
    protected Boolean mapActive = false;

    private double lat, lng;
    private LatLng latLng;

    private LocationListener mLocationListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        setUpMapIfNeeded();
    }


    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
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

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {
        mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker").snippet("Snippet"));

        mMap.setMyLocationEnabled(true);

        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        mLocationListener = new MLocationListener();
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, LOC_UPDATE_INTERVAL, 0, mLocationListener);

        Criteria cr = new Criteria();

        String provider = lm.getBestProvider(cr, true);

        mLocation = lm.getLastKnownLocation(provider);

        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        lat = mLocation.getLatitude();
        lng = mLocation.getLongitude();

        latLng = new LatLng(lat, lng);

        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

        mMap.animateCamera(CameraUpdateFactory.zoomTo(18));

        mMap.getUiSettings().setScrollGesturesEnabled(false);

        Log.d("latlng", "lat: " + lat + ", lng: " + lng);

        //mMap.addMarker(new MarkerOptions().position(new LatLng(lat, lng)).title("Momentaner Standort").snippet("Hat wohl geklappt!"));

        mapActive = true;
    }

    private class MLocationListener implements LocationListener {
        @Override
        public void onLocationChanged(Location location) {
            lat = mLocation.getLatitude();
            lng = mLocation.getLongitude();

            latLng = new LatLng(lat, lng);

            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

            Log.d("New Pos", "Lat: " + lat + ", Lng: " + lng);
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
}
