package com.boisestate.srisarguru.mydog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class ClinicMapsActivity extends FragmentActivity implements LocationListener {

    private GoogleMap googleMap; // Might be null if Google Play services APK is not available.
    int cityPosition,position;
    String[] clinics,cities, tempClinics;
    String city;
    double gpslatitude,gpslongitude;
    ArrayList<ClinicDetails> clinicsArraylist = new ArrayList<ClinicDetails>();
    double latitude,lomgitude;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clinic_maps);
        this.position=getIntent().getIntExtra("Number",0);
        this.cityPosition =getIntent().getIntExtra("citypos",0);
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000, 1000, this);
        final MapFragment mapFragment = (MapFragment) (getFragmentManager().findFragmentById(R.id.map));

        googleMap = mapFragment.getMap();
        cities=getResources().getStringArray(R.array.Cities);
        Resources res = getResources();
        TypedArray typedArray = res.obtainTypedArray(R.array.Clinics);
        int typedArraySize = typedArray.length();
        String[][] array = new String[typedArraySize][];
        for (int i = 0; i < typedArraySize; ++i) {
            int id = typedArray.getResourceId(i, 0);
            if (id > 0) {
                array[i] = res.getStringArray(id);
            }
        }
        typedArray.recycle();
        clinics=array[cityPosition];
        tempClinics =new String[clinics.length];
        for (int i=0;i<clinics.length;i++){
            String[] split=clinics[i].split("/");
            ClinicDetails cl= new ClinicDetails(split[0],split[1],split[2],split[3],split[4]);
            clinicsArraylist.add(i, cl);
            tempClinics[i]=split[0]+" "+split[1]+" "+split[4];
        }
        this.latitude=Double.parseDouble(clinicsArraylist.get(position).latitude);
        this.lomgitude=Double.parseDouble(clinicsArraylist.get(position).longitude);

        if ( !locationManager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
            buildAlertMessageNoGps();
        }

            Marker marker = googleMap.addMarker(new MarkerOptions()
                            .position(new LatLng(latitude,lomgitude))
                            .title(clinicsArraylist.get(position).name)
                            .snippet("Address:" + clinicsArraylist.get(position).address)
            );
            marker.showInfoWindow();
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude,lomgitude), 10));


    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #googleMap} is not null.
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

        if (googleMap == null) {

            googleMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            if (googleMap != null) {
                setUpMap();
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #googleMap} is not null.
     */
    private void setUpMap() {
        googleMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
    }

    @Override
    public void onLocationChanged(Location location) {
        this.gpslatitude = (location.getLatitude());
        this.gpslongitude = (location.getLongitude());

        Log.i("Geo_Location", "Latitude: " + gpslatitude + ", Longitude: " + gpslongitude);
        Marker markergps = googleMap.addMarker(new MarkerOptions()
                        .position(new LatLng(gpslatitude,gpslongitude))
                        .title("You are here")

        );
        markergps.showInfoWindow();
        Marker marker = googleMap.addMarker(new MarkerOptions()
                        .position(new LatLng(latitude,lomgitude))
                        .title(clinicsArraylist.get(position).name)
                       .snippet("Address:" + clinicsArraylist.get(position).address)
        );

        marker.showInfoWindow();
        LatLngBounds.Builder b = new LatLngBounds.Builder();

            b.include(marker.getPosition());
            b.include(markergps.getPosition());

        LatLngBounds bounds = b.build();
        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, 500,500,50);
        googleMap.animateCamera(cu);
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
    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }
}
