package com.conupods;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private static final String TAG = "MapsActivity";
    private static final int ERROOR_DIALOG_REQUEST = 9001;
    private static final float DEFAULT_ZOOM = 15f;


    private GoogleMap mMap;

    private final String COURSE_LOCATION_PERMISSION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private final String FINE_LOCATION_PERMISSION = Manifest.permission.ACCESS_FINE_LOCATION;

    //Activity Components
    private  Button locationBtn;


    //Variables for logic
    Boolean permissionsGranted = false;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;

    //Providers
    //TODO Might need to update to more recent version
    private FusedLocationProviderClient fusedLocationProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        if(!permissionsGranted)
           getLocationPermission();

        
    }


    private void initializeMap() {
        Log.d(TAG, "Initializing Map...");

        //TODO Remove to create custom current location button
        locationBtn = findViewById(R.id.myLocationButton);

        locationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDeviceCurrentLocation();
            }
        });

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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
        Log.d(TAG, "Map is ready");

        mMap = googleMap;

        if(permissionsGranted){
            getDeviceCurrentLocation();
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(false);
        }



        // Add a marker in Sydney and move the camera

        Toast.makeText(this, "Maps is ready", Toast.LENGTH_SHORT).show();
    }

    private void getLocationPermission(){
        Log.d(TAG, "Getting Location Permissions");


        /** After android Marshmellow release, we need to explicitly check for
         * permissions such as location permissions*/

        String[] permissions = {
                COURSE_LOCATION_PERMISSION, FINE_LOCATION_PERMISSION};

        if(ContextCompat.checkSelfPermission(this.getApplicationContext(), FINE_LOCATION_PERMISSION) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this.getApplicationContext(), COURSE_LOCATION_PERMISSION) == PackageManager.PERMISSION_GRANTED)
            permissionsGranted = true;
        else
            ActivityCompat.requestPermissions(MapsActivity.this, permissions, LOCATION_PERMISSION_REQUEST_CODE);



    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults){
        Log.d(TAG, "onRequestPermissionsResult is called");

        permissionsGranted = false;

        switch(requestCode){
            case LOCATION_PERMISSION_REQUEST_CODE:{
                if(grantResults.length>0){
                    for(int i = 0; i< grantResults.length; i++){
                        if(grantResults[i] != PackageManager.PERMISSION_GRANTED){
                            permissionsGranted = false;
                            Log.d(TAG, "Permissions Failed");
                            break;
                        }
                    }

                    Log.d(TAG, "Permissions Granted");
                    permissionsGranted = true;
                    initializeMap();
                }
            }
        }
    }


    private void getDeviceCurrentLocation(){
        fusedLocationProvider = LocationServices.getFusedLocationProviderClient(this);

        try{
            if(permissionsGranted){
                final Task currentLocation = fusedLocationProvider.getLastLocation();

                currentLocation.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if(task.isSuccessful()){
                            Log.d(TAG, "onComplete: Got the current location");
                            Location location = (Location) currentLocation.getResult();



                            moveCamera(new LatLng(location.getLatitude(), location.getLongitude()), DEFAULT_ZOOM);

                        }
                        else{
                            Log.d(TAG, "onComplete: Current Location is null");
                            Toast.makeText(MapsActivity.this, "Current location not found", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        }catch(SecurityException e){
            Log.e(TAG, "getDeviceCurrentLOcation: SecurityException: "+ e.getMessage());
        }
    }


    private void moveCamera(LatLng latLng, float zoom){
        Log.d(TAG, "initiMap: Moving the camera to Latitude: "+ latLng.latitude + " and longitude: "+ latLng.longitude);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));

    }


}
