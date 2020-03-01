package com.conupods;

import androidx.fragment.app.FragmentActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.conupods.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    // These could to be moved outside of the file
    public final double SGW_LAT = 45.496080;
    public final double SGW_LNG = -73.577957;
    public final double LOY_LAT = 45.458333;
    public final double LOY_LNG = -73.640450;

    public final LatLng SGW_CAMPUS_LOC = new LatLng(SGW_LAT, SGW_LNG);
    public final LatLng LOY_CAMPUS_LOC = new LatLng(LOY_LAT, LOY_LNG);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        // The two campus swap buttons
        Button SGWButton = (Button) findViewById(R.id.SGW);
        Button LOYButton = (Button) findViewById(R.id.LOY);

        SGWButton.setOnClickListener((View v) -> {
            moveToCampus(SGW_CAMPUS_LOC);

            SGWButton.setBackgroundResource(R.drawable.conu_gradient);
            SGWButton.setTextColor(Color.WHITE);
            LOYButton.setBackgroundColor(Color.WHITE);
            LOYButton.setTextColor(Color.BLACK);
        });


        LOYButton.setOnClickListener((View v) -> {
            moveToCampus(LOY_CAMPUS_LOC);

            LOYButton.setBackgroundResource(R.drawable.conu_gradient);
            LOYButton.setTextColor(Color.WHITE);
            SGWButton.setBackgroundColor(Color.WHITE);
            SGWButton.setTextColor(Color.BLACK);
        });
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
        moveToCampus(SGW_CAMPUS_LOC);
    }

    // Add a marker in starting location and move the camera
    private void moveToCampus(LatLng targetCampus) {
        mMap.addMarker(new MarkerOptions().position(targetCampus).title("Marker in Campus"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(targetCampus));
    }
}
