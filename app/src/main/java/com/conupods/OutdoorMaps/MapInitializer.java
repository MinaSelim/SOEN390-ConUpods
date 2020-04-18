package com.conupods.OutdoorMaps;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;
import android.graphics.drawable.AnimatedStateListDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;

import com.conupods.IndoorMaps.ConcreteBuildings.HBuilding;
import androidx.core.content.ContextCompat;

import com.conupods.App;
import com.conupods.Calendar.CalendarObject;
import com.conupods.Calendar.Event;
import com.conupods.IndoorMaps.IndoorBuildingOverlays;
import com.conupods.IndoorMaps.IndoorOverlayHandlers.CCBuildingHandler;
import com.conupods.IndoorMaps.IndoorOverlayHandlers.DefaultHandler;
import com.conupods.IndoorMaps.IndoorOverlayHandlers.HallBuildingHandler;
import com.conupods.IndoorMaps.IndoorOverlayHandlers.IndoorOverlayHandler;
import com.conupods.IndoorMaps.IndoorOverlayHandlers.MBBuildingHandler;
import com.conupods.IndoorMaps.IndoorOverlayHandlers.VLBuildingHandler;
import com.conupods.MapsActivity;
import com.conupods.OutdoorMaps.Remote.IGoogleAPIService;
import com.conupods.OutdoorMaps.Services.PlacesService;
import com.conupods.OutdoorMaps.View.PointsOfInterest.SliderAdapter;
import com.conupods.OutdoorMaps.View.Settings.SettingsActivity;
import com.conupods.OutdoorMaps.View.Settings.SettingsPersonalActivity;
import com.conupods.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;

import java.util.ArrayList;
import java.util.List;

import androidx.core.app.ActivityCompat;
import androidx.viewpager.widget.ViewPager;

public class MapInitializer {

    public static final double ZOOM_LEVEL = 18.3;

    private CameraController mCameraController;
    private GoogleMap mMap;
    private BuildingInfoWindow mBuildingInfoWindow;
    private IndoorBuildingOverlays mIndoorBuildingOverlays;
    private OutdoorBuildingOverlays mOutdoorBuildingOverlays;
    private IndoorOverlayHandler mIndoorOverlayHandler;
    private SearchView mSearchBar;
    private Button mSgwButton, mLoyButton;
    IGoogleAPIService mService;
    MapsActivity mView;
    SliderAdapter mSliderAdapter;
    ViewPager mViewPager;

    List<Button> mButtonsH = new ArrayList<>();
    List<Button> mButtonsMB = new ArrayList<>();
    List<Button> mButtonsVL = new ArrayList<>();
    private static final String TAG = "MapInitializer";
    private View mNextEventLayout;
    private RelativeLayout mHomeLayout;
    private TextView mNextEventdate;
    private TextView mNextEventTitle;
    private TextView mNextEventlocation;
    private TextView mNextEventTime;
    private LayoutInflater mInflater;

    public MapInitializer(CameraController cameraController, IndoorBuildingOverlays indoorBuildingOverlays,
                          OutdoorBuildingOverlays outdoorBuildingOverlays, GoogleMap map,
                          BuildingInfoWindow buildingInfoWindow, Button sgwButton, Button loyButton,
                          IGoogleAPIService googleAPIService, MapsActivity view,
                          SliderAdapter sliderAdapter) {

        mCameraController = cameraController;
        mIndoorBuildingOverlays = indoorBuildingOverlays;
        mBuildingInfoWindow = buildingInfoWindow;
        mOutdoorBuildingOverlays = outdoorBuildingOverlays;
        mMap = map;
        HBuilding.getInstance();


        mView = view;
        mService = googleAPIService;
        mSliderAdapter = sliderAdapter;

        mSgwButton = sgwButton;
        mLoyButton = loyButton;


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

            if (mMap.getCameraPosition().zoom > ZOOM_LEVEL) {
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
    public void initializeToggleButtons() {

        mSgwButton.setBackgroundColor(Color.WHITE);
        mSgwButton.setTextColor(Color.BLACK);
        mLoyButton.setBackgroundColor(Color.WHITE);
        mLoyButton.setTextColor(Color.BLACK);

        mSgwButton.setOnClickListener((View v) -> {
            PlacesService mPlaceService = new PlacesService(mView, mService, mMap);
            mPlaceService.getAllPointsOfInterest("SGW");
            mCameraController.moveToLocationAndAddMarker(CameraController.SGW_CAMPUS_LOC);

            mSgwButton.setBackgroundResource(R.drawable.conu_gradient);
            mSgwButton.setTextColor(Color.WHITE);
            mLoyButton.setBackgroundColor(Color.WHITE);
            mLoyButton.setTextColor(Color.BLACK);

            // restore initial camera zoom level
            mMap.animateCamera(CameraUpdateFactory.zoomTo(16.0f));
        });

        mLoyButton.setOnClickListener((View v) -> {
            PlacesService mPlaceService = new PlacesService(mView, mService, mMap);
            mPlaceService.getAllPointsOfInterest("LOY");
            mCameraController.moveToLocationAndAddMarker(CameraController.LOY_CAMPUS_LOC);

            mLoyButton.setBackgroundResource(R.drawable.conu_gradient);
            mLoyButton.setTextColor(Color.WHITE);
            mSgwButton.setBackgroundColor(Color.WHITE);
            mSgwButton.setTextColor(Color.BLACK);

            // restore initial camera zoom level 
            mMap.animateCamera(CameraUpdateFactory.zoomTo(16.0f));
        });
    }

    public void initializeLocationButton(Button locationButton) {
        PlacesService mPlaceService = new PlacesService(mView, mService, mMap);
        mPlaceService.getAllPointsOfInterest("Current Location");
        locationButton.setOnClickListener((View view) -> {
            mCameraController.goToDeviceCurrentLocation();
            mSgwButton.setBackgroundColor(Color.WHITE);
            mSgwButton.setTextColor(Color.BLACK);
            mLoyButton.setBackgroundColor(Color.WHITE);
            mLoyButton.setTextColor(Color.BLACK);
        });
    }

    public SearchView initializeSearchBar(SearchView searchBar) {
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

    public void launchSettingsActivity(MapsActivity current) {
        current.findViewById(R.id.settingsButton).setOnClickListener(view -> current.startActivityIfNeeded(new Intent(current, SettingsActivity.class).setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT), 0));
    }

    public void initNextEventButton(LayoutInflater inflater, RelativeLayout homeLayout) {
        mInflater=inflater;
        mHomeLayout=homeLayout;
        Button nextEventBttn = (Button) mHomeLayout.findViewById(R.id.calendarButton);
        nextEventBttn.setOnClickListener(v -> { showEventWindow(); });
    }
    private void showEventWindow() {
        initEventWindow();
        mHomeLayout.addView(mNextEventLayout);
    }

    /*
    inflate next event window
     */
    public void initEventWindow() {
        //inflate (create) a copy of our custom layout
        mNextEventLayout = mInflater.inflate(R.layout.window_next_event, mHomeLayout, false);
        //shade background
        mNextEventLayout.setBackgroundColor(ContextCompat.getColor(App.getContext(), R.color.shade));

        mNextEventdate = (TextView) mNextEventLayout.findViewById(R.id.event_date);
        mNextEventTitle = (TextView) mNextEventLayout.findViewById(R.id.event_name);
        mNextEventlocation = (TextView) mNextEventLayout.findViewById(R.id.event_location);
        mNextEventTime = (TextView) mNextEventLayout.findViewById(R.id.event_time);

        initNextEventPopUp();
        initEventExitButton();
    }

    /*
      initiates close button of the calendar popup
       */
    private void initEventExitButton() {
        Button mCloseEventPopup = (Button) mNextEventLayout.findViewById(R.id.exitEventButton);
        mCloseEventPopup.requestFocus();
        //close the popup window on button click
        mCloseEventPopup.setOnClickListener(v -> {
            if(mNextEventLayout!=null){
                mHomeLayout.removeView(mNextEventLayout);
            }else {
                Log.d(TAG, "could not close next event window, view nextEventLayout is null");
            }
        });
    }

    /*
    set up text in next event window
     */
    private void initNextEventPopUp() {
        CalendarObject calendar = SettingsPersonalActivity.mSelectedCalendar;
        boolean isCalendarSelected = calendar != null;
        
        if (!isCalendarSelected || !calendar.hasNextEvent()) {
            mNextEventdate.setText("");
            mNextEventTitle.setText("");
            mNextEventlocation.setText("");

            if (!isCalendarSelected) { mNextEventTime.setText("set up Google Calendar account in settings"); }
            if (!calendar.hasNextEvent()) { mNextEventTime.setText("No upcoming events in the next 24h"); }

        } else {
            Event nextEvent = calendar.getmNextEvent();
            mNextEventdate.setText(nextEvent.getmNextEventDate());
            mNextEventTitle.setText(nextEvent.getmNextEventTitle());
            mNextEventTime.setText(nextEvent.getmNextEventStartTime() + "-" + nextEvent.getmNextEventEndTime());
            mNextEventlocation.setText(nextEvent.getmNextEventLocation());
        }
    }
}
