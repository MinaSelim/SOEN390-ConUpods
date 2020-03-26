package com.conupods.OutdoorMaps;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;

import com.conupods.IndoorMaps.IndoorOverlayHandlers.CCBuildingHandler;
import com.conupods.IndoorMaps.IndoorOverlayHandlers.DefaultHandler;
import com.conupods.IndoorMaps.IndoorOverlayHandlers.HallBuildingHandler;
import com.conupods.IndoorMaps.IndoorOverlayHandlers.MBBuildingHandler;
import com.conupods.IndoorMaps.IndoorOverlayHandlers.IndoorOverlayHandler;
import com.conupods.IndoorMaps.IndoorBuildingOverlays;
import com.conupods.IndoorMaps.IndoorOverlayHandlers.VLBuildingHandler;
import com.conupods.OutdoorMaps.View.MapsActivity;
import com.conupods.R;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import java.util.ArrayList;
import java.util.List;

import static android.view.inputmethod.EditorInfo.IME_ACTION_DONE;
import static android.view.inputmethod.EditorInfo.IME_ACTION_SEARCH;

public class MapInitializer {

    public static final double mZoomLevel = 18.3;

    private CameraController mCameraController;
    private GoogleMap mMap;
    private BuildingInfoWindow mBuildingInfoWindow;
    private IndoorBuildingOverlays mIndoorBuildingOverlays;
    private OutdoorBuildingOverlays mOutdoorBuildingOverlays;
    private static final String TAG = "MapInitializer";
    private IndoorOverlayHandler mIndoorOverlayHandler;
    private SearchView mSearchBar;

    List<Button> mButtonsH = new ArrayList<Button>();
    List<Button> mButtonsMB = new ArrayList<Button>();
    List<Button> mButtonsVL = new ArrayList<Button>();

    public MapInitializer(CameraController cameraController, IndoorBuildingOverlays indoorBuildingOverlays, OutdoorBuildingOverlays outdoorBuildingOverlays, GoogleMap map, BuildingInfoWindow buildingInfoWindow) {
        mCameraController = cameraController;
        mIndoorBuildingOverlays = indoorBuildingOverlays;
        mBuildingInfoWindow = buildingInfoWindow;
        mOutdoorBuildingOverlays = outdoorBuildingOverlays;
        mMap = map;

        IndoorOverlayHandler mbBuildingHandler = new MBBuildingHandler();
        IndoorOverlayHandler vlBuildingHandler = new VLBuildingHandler();
        IndoorOverlayHandler ccBuildingHandler = new CCBuildingHandler();
        IndoorOverlayHandler mDefaultHandler = new DefaultHandler();

        this.mIndoorOverlayHandler = new HallBuildingHandler();
        mIndoorOverlayHandler.setNextInChain(mbBuildingHandler);
        mbBuildingHandler.setNextInChain(vlBuildingHandler);
        vlBuildingHandler.setNextInChain(ccBuildingHandler);
        ccBuildingHandler.setNextInChain(mDefaultHandler);
    }

    public void onCameraChange() {

        mMap.setOnCameraIdleListener(() -> {

            LatLngBounds bounds = mMap.getProjection().getVisibleRegion().latLngBounds;

            if (mMap.getCameraPosition().zoom > mZoomLevel) {
                mOutdoorBuildingOverlays.removePolygons();
                mIndoorOverlayHandler.checkBounds(bounds, mIndoorBuildingOverlays);
            } else {
                mIndoorBuildingOverlays.hideLevelButton();
                mIndoorBuildingOverlays.removeOverlay();
                mOutdoorBuildingOverlays.overlayPolygons();
            }
        });
    }

    //TODO: Add all button related methods (following 3) to a separate class
    private void changeButtonColors(List<Button> floorButtons) {
        for (Button button : floorButtons) {
            if (!button.isPressed()) {
                button.setBackgroundColor(Color.WHITE);
            }
            if (button.isPressed()) {
                button.setBackgroundColor(Color.LTGRAY);
            }
        }
    }

    //creates button objects
    private void createButton(int index, IndoorBuildingOverlays.BuildingCodes building, List<Button> buttonContainer, View buttonId) {

        Button b = (Button) buttonId;
        b.setOnClickListener((View v) -> {
            mIndoorBuildingOverlays.changeOverlay(index, building);
            changeButtonColors(buttonContainer);
        });
        buttonContainer.add(b);
    }

    //Listener for floor buttons, display appropriate floor blueprint
    public void initializeFloorButtons(View floorButtons) {

        createButton(7, IndoorBuildingOverlays.BuildingCodes.VL, mButtonsVL, floorButtons.findViewById(R.id.loy_vl1));
        createButton(8, IndoorBuildingOverlays.BuildingCodes.VL, mButtonsVL, floorButtons.findViewById(R.id.loy_vl2));
        createButton(4, IndoorBuildingOverlays.BuildingCodes.MB, mButtonsMB, floorButtons.findViewById(R.id.MB1));
        createButton(5, IndoorBuildingOverlays.BuildingCodes.MB, mButtonsMB, floorButtons.findViewById(R.id.MBS2));
        createButton(0, IndoorBuildingOverlays.BuildingCodes.H, mButtonsH, floorButtons.findViewById(R.id.hall1));
        createButton(1, IndoorBuildingOverlays.BuildingCodes.H, mButtonsH, floorButtons.findViewById(R.id.hall2));
        createButton(2, IndoorBuildingOverlays.BuildingCodes.H, mButtonsH, floorButtons.findViewById(R.id.hall8));
        createButton(3, IndoorBuildingOverlays.BuildingCodes.H, mButtonsH, floorButtons.findViewById(R.id.hall9));
    }

    // The two campus swap buttons
    public void initializeToggleButtons(Button sgwButton, Button loyButton) {
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
        locationButton.setOnClickListener((View view) -> mCameraController.goToDeviceCurrentLocation());
    }

    public SearchView initializeSearchBar(SearchView searchBar, Context context) {
        searchBar.setQueryHint("Where To?");
        searchBar.setTransitionName("BeginTransition");
        return searchBar;

    }

    public void initializeBuildingMarkers() {
        mBuildingInfoWindow.generateBuildingMakers(mMap);
        mMap.setOnMarkerClickListener((Marker marker) -> {
            mMap.setInfoWindowAdapter(mBuildingInfoWindow);
            return false;
        });
    }

    public static void setSearchViewOnClickListener(View v, View.OnClickListener listener) {
        if (v instanceof ViewGroup) {
            ViewGroup group = (ViewGroup) v;
            int count = group.getChildCount();
            for (int i = 0; i < count; i++) {
                View child = group.getChildAt(i);
                if (child instanceof LinearLayout || child instanceof RelativeLayout) {
                    setSearchViewOnClickListener(child, listener);
                }

                if (child instanceof TextView) {
                    TextView text = (TextView) child;
                    text.setFocusable(false);
                }
                child.setOnClickListener(listener);
            }
        }
    }
}
