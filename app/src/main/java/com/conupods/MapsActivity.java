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

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, View.OnClickListener {

    private GoogleMap mMap;

    private LatLng sgw = new LatLng(45.496080, -73.577957);
    private LatLng loy = new LatLng(45.458333, -73.640450);

    private LatLng current = sgw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Button one = (Button) findViewById(R.id.SGW);
        one.setOnClickListener(this); // calling onClick() method
        Button two = (Button) findViewById(R.id.LOY);
        two.setOnClickListener(this);
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

        // Add a marker in starting location and move the camera
        mMap.addMarker(new MarkerOptions().position(current).title("Marker in Campus"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(current));
    }

    @Override
    public void onClick(View v) {
        // handling onClick Events
        Button btnSGW = findViewById(R.id.SGW);
        Button btnLOY = findViewById(R.id.LOY);

        switch (v.getId()) {

            case R.id.SGW:
                // code for button when user clicks buttonOne.
                mMap.addMarker(new MarkerOptions().position(sgw).title("Marker in Campus"));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(sgw));
                current = sgw;

                //changing color on click
                btnSGW.setBackgroundResource(R.drawable.conu_gradient);
                btnSGW.setTextColor(Color.WHITE);
                btnLOY.setBackgroundColor(Color.WHITE);
                btnLOY.setTextColor(Color.BLACK);
                break;

            case R.id.LOY:
                // do your code
                mMap.addMarker(new MarkerOptions().position(loy).title("Marker in Campus"));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(loy));
                current = loy;

                //changing color on click
                btnLOY.setBackgroundResource(R.drawable.conu_gradient);
                btnLOY.setTextColor(Color.WHITE);
                btnSGW.setBackgroundColor(Color.WHITE);
                btnSGW.setTextColor(Color.BLACK);
                break;

            default:
                break;
        }
    }
}
