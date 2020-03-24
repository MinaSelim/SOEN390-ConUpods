package com.conupods.OutdoorMaps;

import android.graphics.Color;
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

    public static final LatLng CENTER_OF_HALL = new LatLng(  45.49728190486448,  	-73.57892364263535);
    public static final LatLng CENTER_OF_JMSB = new LatLng(45.49524950613837, -73.57895582914352);
    public static final LatLng CENTER_OF_LOY_CC = new LatLng(45.45824552786007,    -73.64033281803131);
    public static final LatLng CENTER_OF_LOY_VL = new LatLng( 45.459086497919344,     -73.63828897476196);
    public static final double mZoomLevel = 18.3;

    private CameraController mCameraController;
    private GoogleMap mMap;
    private BuildingInfoWindow mBuildingInfoWindow;
    private CameraController mCameraController;
    private IndoorBuildingOverlays mIndoorBuildingOverlays;
    private OutdoorBuildingOverlays mOutdoorBuildingOverlays;
    private GoogleMap mMap;
    private static final String TAG = "MapInitializer";

    public MapInitializer(CameraController cameraController, IndoorBuildingOverlays indoorBuildingOverlays, OutdoorBuildingOverlays outdoorBuildingOverlays, GoogleMap map) {
        mCameraController = cameraController;
        mIndoorBuildingOverlays = indoorBuildingOverlays;
    public MapInitializer(CameraController CameraController, GoogleMap map, BuildingInfoWindow buildingInfoWindow) {
        mCameraController = CameraController;
        mBuildingInfoWindow = buildingInfoWindow;
        mOutdoorBuildingOverlays = outdoorBuildingOverlays;
        mMap = map;
    }

    public void onCameraChange(){

        mMap.setOnCameraIdleListener(()->{

            LatLngBounds bounds = mMap.getProjection().getVisibleRegion().latLngBounds;

            if (mMap.getCameraPosition().zoom > mZoomLevel) {
                mOutdoorBuildingOverlays.removePolygons();
                if (bounds.contains(CENTER_OF_JMSB)) {
                    mIndoorBuildingOverlays.displayOverlayJMSB();
                    mIndoorBuildingOverlays.showButtonsJMSB();
                }
                else if(bounds.contains(CENTER_OF_HALL)) {
                    mIndoorBuildingOverlays.displayOverlayHall();
                    mIndoorBuildingOverlays.showButtonsHALL();
                }
                else if(bounds.contains(CENTER_OF_LOY_CC)) {
                    mIndoorBuildingOverlays.displayOverlayLOYCC();
                }
                else if(bounds.contains(CENTER_OF_LOY_VL)) {
                    mIndoorBuildingOverlays.displayOverlayLOYVL();
                    mIndoorBuildingOverlays.showButtonsLOYVL();
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

    private void changeButtonColors(List<Button> floorButtons){

        for(Button button : floorButtons) {
            if(!button.isPressed()){
                button.setBackgroundColor(Color.WHITE);
            }
            if(button.isPressed()) {
                button.setBackgroundColor(Color.LTGRAY);
            }
        }

    }

    public void initializeFloorButtons(View floorButtons) {
        //Listener for floor buttons, display appropriate floor blueprint

        //TODO logic for changing button colors on clickEvent
        List<Button> buttonsHALL = new ArrayList<Button>();
        List<Button> buttonsJMSB = new ArrayList<Button>();
        List<Button> buttonsLOYCC = new ArrayList<Button>();


        Button loy_vl1 = (Button) floorButtons.findViewById(R.id.loy_vl1);
        loy_vl1.setOnClickListener((View v) -> {
            mIndoorBuildingOverlays.changeOverlay(7, "VL");
            changeButtonColors(buttonsLOYCC);
        });

        Button loy_vl2 = (Button) floorButtons.findViewById(R.id.loy_vl2);
        loy_vl2.setOnClickListener((View v) -> {
            mIndoorBuildingOverlays.changeOverlay(8, "VL");
            changeButtonColors(buttonsLOYCC);

        });

        Button jmsb1 = (Button) floorButtons.findViewById(R.id.jmsb1);
        jmsb1.setOnClickListener((View v) -> {
            mIndoorBuildingOverlays.changeOverlay(4, "JMSB");
            changeButtonColors(buttonsJMSB);
        });

        Button jmsbS2 = (Button) floorButtons.findViewById(R.id.jmsbS2);
        jmsbS2.setOnClickListener((View v) -> {
            mIndoorBuildingOverlays.changeOverlay(5, "JMSB");
            changeButtonColors(buttonsJMSB);
        });

        Button hall1 = (Button) floorButtons.findViewById(R.id.hall1);
        hall1.setOnClickListener((View v) -> {
            mIndoorBuildingOverlays.changeOverlay(0, "HALL");
            changeButtonColors(buttonsHALL);

        });

        Button hall2 = (Button) floorButtons.findViewById(R.id.hall2);
        hall2.setOnClickListener((View v) -> {
            mIndoorBuildingOverlays.changeOverlay(1, "HALL");
            changeButtonColors(buttonsHALL);
        });

        Button hall8 = (Button) floorButtons.findViewById(R.id.hall8);
        hall8.setOnClickListener((View v) -> {
            mIndoorBuildingOverlays.changeOverlay(2, "HALL");
            changeButtonColors(buttonsHALL);
        });

        Button hall9 = (Button) floorButtons.findViewById(R.id.hall9);
        hall9.setOnClickListener((View v) -> {
            mIndoorBuildingOverlays.changeOverlay(3, "HALL");
            changeButtonColors(buttonsHALL);
        });

        buttonsLOYCC.add(loy_vl1);
        buttonsLOYCC.add(loy_vl2);
        buttonsJMSB.add(jmsb1);
        buttonsJMSB.add(jmsbS2);
        buttonsHALL.add(hall1);
        buttonsHALL.add(hall2);
        buttonsHALL.add(hall8);
        buttonsHALL.add(hall9);
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
