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
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import java.util.ArrayList;
import java.util.List;

import static android.view.inputmethod.EditorInfo.IME_ACTION_DONE;
import static android.view.inputmethod.EditorInfo.IME_ACTION_SEARCH;

public class MapInitializer{


    //
    private static final LatLng centerOfHall = new LatLng(  45.49728190486448,  	-73.57892364263535);
    private static final LatLng centerOfJMSB = new LatLng(45.49524950613837, -73.57895582914352);


    public List<LatLng> buildingPoints = new ArrayList<LatLng>();


    private CameraController mCameraController;
    private GoogleMap mMap;
    private BuildingInfoWindow mBuildingInfoWindow;
    private CameraController mCameraController;
    private IndoorBuildingOverlays mIndoorBuildingOverlays;
    private OutdoorBuildingOverlays mOutdoorBuildingOverlays;
    private GoogleMap mMap;
    private static final String TAG = "MapInitializer";
    //private boolean firstDisplay = false;



    public MapInitializer(CameraController cameraController, IndoorBuildingOverlays indoorBuildingOverlays, OutdoorBuildingOverlays outdoorBuildingOverlays, GoogleMap map) {
        mCameraController = cameraController;
        mIndoorBuildingOverlays = indoorBuildingOverlays;
    public MapInitializer(CameraController CameraController, GoogleMap map, BuildingInfoWindow buildingInfoWindow) {
        mCameraController = CameraController;
        mBuildingInfoWindow = buildingInfoWindow;
        mOutdoorBuildingOverlays = outdoorBuildingOverlays;
        mMap = map;
    }

    private float mCurrentZoomLevel;

    public void onCameraChange(){

        List<String> buildings = new ArrayList<String>();
        buildings.add("JMSB");
        buildings.add("LOY");
        buildings.add("HALL");


        mMap.setOnCameraIdleListener(()->{

            LatLngBounds bounds = mMap.getProjection().getVisibleRegion().latLngBounds;
            if (mMap.getCameraPosition().zoom > 18) {
                mOutdoorBuildingOverlays.removePolygons();
                if (bounds.contains(centerOfJMSB)) {
                        mIndoorBuildingOverlays.displayOverlayJMSB();
                        mIndoorBuildingOverlays.showLevelButton("JMSB");
                }
                else if(bounds.contains(centerOfHall)){
                    mIndoorBuildingOverlays.displayOverlayHall();
                    mIndoorBuildingOverlays.showLevelButton("HALL");
                }
                else {
                        mIndoorBuildingOverlays.hideLevelButton();
                        mIndoorBuildingOverlays.removeOverlay();

                }
            } else {
                    mIndoorBuildingOverlays.hideLevelButton();
                    mIndoorBuildingOverlays.removeOverlay();
                    mOutdoorBuildingOverlays.overlayPolygons();

            }


        });
    }
    public void initializeFloorButtons(View floorButtons) {
        //Listener for floor buttons, display appropriate floor blueprint

        /*Button jmsb1 = (Button) floorButtons.findViewById(R.id.jmsb2);
        jmsb1.setOnClickListener((View v) -> {
            mIndoorBuildingOverlays.changeOverlay(0, "JMSB");
        });*/

        Button hall1 = (Button) floorButtons.findViewById(R.id.hall1);
        hall1.setOnClickListener((View v) -> {
            mIndoorBuildingOverlays.changeOverlay(0, "HALL");
        });

        Button hall2 = (Button) floorButtons.findViewById(R.id.hall2);
        hall2.setOnClickListener((View v) -> {
            mIndoorBuildingOverlays.changeOverlay(1, "HALL");
        });

        Button hall8 = (Button) floorButtons.findViewById(R.id.hall8);
        hall8.setOnClickListener((View v) -> {
            mIndoorBuildingOverlays.changeOverlay(2, "HALL");
        });

        Button hall9 = (Button) floorButtons.findViewById(R.id.hall9);
        hall9.setOnClickListener((View v) -> {
            mIndoorBuildingOverlays.changeOverlay(3, "HALL");
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
