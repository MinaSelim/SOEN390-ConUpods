package com.conupods.OutdoorMaps;

import android.graphics.Color;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.conupods.IndoorMaps.IndoorBuildingOverlays;
import com.conupods.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import static android.view.inputmethod.EditorInfo.IME_ACTION_DONE;
import static android.view.inputmethod.EditorInfo.IME_ACTION_SEARCH;

public class MapInitializer {

    private CameraController mCameraController;
    private GoogleMap mMap;
    private BuildingInfoWindow mBuildingInfoWindow;
    private CameraController mCameraController;
    private IndoorBuildingOverlays mIndoorBuildingOverlays;
    private static final String TAG = "MapInitializer";


    public MapInitializer(CameraController cameraController, IndoorBuildingOverlays indoorBuildingOverlays) {
        mCameraController = cameraController;
        mIndoorBuildingOverlays = indoorBuildingOverlays;
    public MapInitializer(CameraController CameraController, GoogleMap map, BuildingInfoWindow buildingInfoWindow) {
        mCameraController = CameraController;
        mMap = map;
        mBuildingInfoWindow = buildingInfoWindow;
    }

    public void initializeFloorButtons(View floorButtons) {
        //Listener for floor buttons, display appropriate floor blueprint
        Button hall8 = (Button) floorButtons.findViewById(R.id.eighthFloor);
        hall8.setOnClickListener((View v) -> {
            mIndoorBuildingOverlays.displayOverlay(0);
        });

        Button hall9 = (Button) floorButtons.findViewById(R.id.ninthFloor);
        hall9.setOnClickListener((View v) -> {
            mIndoorBuildingOverlays.displayOverlay(1);
        });

    }

    public void initializeToggleButtons(Button sgwButton, Button loyButton) {
        // The two campus swap buttons
        sgwButton.setOnClickListener((View v) -> {
            mCameraController.moveToLocationAndAddMarker(CameraController.SGW_CAMPUS_LOC);

            sgwButton.setBackgroundResource(R.drawable.conu_gradient);
            sgwButton.setTextColor(Color.WHITE);
            loyButton.setBackgroundColor(Color.WHITE);
            loyButton.setTextColor(Color.BLACK);
        });


        loyButton.setOnClickListener((View v) -> {
            mCameraController.moveToLocationAndAddMarker(CameraController.LOY_CAMPUS_LOC);

            loyButton.setBackgroundResource(R.drawable.conu_gradient);
            loyButton.setTextColor(Color.WHITE);
            sgwButton.setBackgroundColor(Color.WHITE);
            sgwButton.setTextColor(Color.BLACK);
        });
    }

    public void initializeLocationButton(Button locationButton) {
        locationButton.setOnClickListener((View view) -> {
            mCameraController.goToDeviceCurrentLocation();
        });
    }

    public void initializeSearchBar(EditText searchBar) {
        //TODO Remove to create custom current location button
        searchBar.setOnEditorActionListener((TextView textView, int actionId, KeyEvent keyEvent) -> {
            if (actionId == IME_ACTION_SEARCH
                    || actionId == IME_ACTION_DONE
                    || keyEvent.getAction() == KeyEvent.ACTION_DOWN
                    || keyEvent.getAction() == keyEvent.KEYCODE_ENTER) {
                //TODO Logic for searching goes here

            }
            return false;
        });
    }

    public void initializeBuildingMarkers() {
        mBuildingInfoWindow.generateBuildingMakers(mMap);
        mMap.setOnMarkerClickListener((Marker marker) -> {
            mMap.setInfoWindowAdapter(mBuildingInfoWindow);
            return false;
        });
    }
}
