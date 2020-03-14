package com.conupods.OutdoorMaps.View;

import android.graphics.Color;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.conupods.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import static android.view.inputmethod.EditorInfo.IME_ACTION_DONE;
import static android.view.inputmethod.EditorInfo.IME_ACTION_SEARCH;

public class MapInitializer {

    GoogleMap mMap;

    public MapInitializer(GoogleMap map) {
        mMap = map;
    }

    public void initializeToggleButtons(Button sgwButton, Button loyButton) {
        // The two campus swap buttons
        sgwButton.setOnClickListener((View v) -> {
            moveToCampus(MapsActivity.SGW_CAMPUS_LOC);

            sgwButton.setBackgroundResource(R.drawable.conu_gradient);
            sgwButton.setTextColor(Color.WHITE);
            loyButton.setBackgroundColor(Color.WHITE);
            loyButton.setTextColor(Color.BLACK);
        });


        loyButton.setOnClickListener((View v) -> {
            moveToCampus(MapsActivity.LOY_CAMPUS_LOC);

            loyButton.setBackgroundResource(R.drawable.conu_gradient);
            loyButton.setTextColor(Color.WHITE);
            sgwButton.setBackgroundColor(Color.WHITE);
            sgwButton.setTextColor(Color.BLACK);
        });
    }

    // Add a marker in starting location and move the camera
    private void moveToCampus(LatLng targetCampus) {
        mMap.addMarker(new MarkerOptions().position(targetCampus).title("Marker in Campus"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(targetCampus));
    }


    public void initializeSearchBar(EditText searchBar) {

        //TODO Remove to create custom current location button
        searchBar.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if(actionId == IME_ACTION_SEARCH
                        || actionId == IME_ACTION_DONE
                        || keyEvent.getAction() == KeyEvent.ACTION_DOWN
                        || keyEvent.getAction() == keyEvent.KEYCODE_ENTER
                )
                {
                    //TODO Logic for searching goes here

                }{

                }
                return false;
            }
        });
    }
}
