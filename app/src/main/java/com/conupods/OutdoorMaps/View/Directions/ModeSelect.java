package com.conupods.OutdoorMaps.View.Directions;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.conupods.OutdoorMaps.Services.OutdoorDirectionsService;
import com.conupods.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.TravelMode;

import java.util.HashMap;

public class ModeSelect extends FragmentActivity implements OnMapReadyCallback {

    // There is really no point to this attribute.
    // Why do we even need a map fragment in this activity?
    private GoogleMap mMap;
    private LatLng mOrigin;
    private LatLng mDestination;

    private HashMap<TravelMode, DirectionsResult> directionsResults;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mode_select);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // class attribute?
        OutdoorDirectionsService outdoorDirectionsService = new OutdoorDirectionsService(this, mMap);

        // add info to buttons
        Intent previousActivityIntent = getIntent();
        mOrigin = previousActivityIntent.getParcelableExtra("fromLatLng");
        mDestination = previousActivityIntent.getParcelableExtra("toLatLng");

        for (TravelMode mode : TravelMode.values()) {
            directionsResults.put(mode, outdoorDirectionsService.computeDirections(mOrigin, mDestination, mode));
        }

        // to origin and destination data
        // rename when we know what activity this is

        String from_location = previousActivityIntent.getStringExtra("fromString");
        String to_location = previousActivityIntent.getStringExtra("toString");

        Button fromButton = (Button) findViewById(R.id.fromButton);
        fromButton.setText("From: " + from_location);

        Button toButton = (Button) findViewById(R.id.toButton);
        toButton.setText("To: " + to_location);


        // add routes info
        String walkingDuration = directionsResults.get(TravelMode.WALKING).routes[0].legs[0].duration.humanReadable;
        TextView walkingDurationTextView = (TextView) findViewById(R.id.walkingDuration);
        walkingDurationTextView.setText(walkingDuration);
        //Button walkingButton = (Button) findViewById(R.id.walkingModeButton);
        //walkingButton.setText("Walking\tDuration: " + walkingDuration);
    }

    public void onClickSetOrigin(View view) {
//        Intent setOriginIntent = new Intent(this, target.class);
//        startActivity(setOriginIntent);
    }

    public void onClickSetDestination(View view) {
//        Intent setDestinationIntent = new Intent(this, target.class);
//        startActivity(setDestinationIntent);
    }

    public void onClickWalking(View view) {
//        Intent walkingIntent(this, target.class);
//        walkingIntent.putExtra("directions", directionsResults.get(TravelMode.WALKING));
//        startActivity(walkingIntent);
    }

    public void onClickDriving(View view) {
//        Intent drivingIntent(this, target.class)
//        startActivity(drivingIntent);

    }

    public void onClickPublicTransit(View view) {

    }

    public void onClickShuttle(View view) {

    }


    // add the onClickListeners
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

        // This makes no sense.
        // Where am I even positioning the camera if it shouldnt be seen
        mMap.moveCamera(CameraUpdateFactory.newLatLng(mDestination));
    }

}
