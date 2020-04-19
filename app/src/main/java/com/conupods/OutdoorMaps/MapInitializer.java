package com.conupods.OutdoorMaps;

import android.Manifest;
import android.content.Context;
import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.provider.CalendarContract;
import android.util.Log;
import android.graphics.drawable.AnimatedStateListDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;

import com.conupods.IndoorMaps.ConcreteBuildings.HBuilding;
import androidx.core.app.ActivityCompat;
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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
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
    private CalendarObject mCalendar;

    enum Notification {
        OPEN,
        UNOPEN,
        NONE
    }

    private static Notification mNotificationStatus = Notification.NONE;
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
        mInflater = inflater;
        mHomeLayout = homeLayout;
        Button nextEventBttn = (Button) mHomeLayout.findViewById(R.id.calendarButton);

        initEventWindow();
        initNextEventPopUp();
        initEventExitButton(nextEventBttn);
        notifyOfUpcomingEvent(nextEventBttn);

        nextEventBttn.setOnClickListener(v -> {
            mNotificationStatus = Notification.OPEN;
            refreshNextEvent(nextEventBttn);
            showEventWindow();
        });
    }


    private void showEventWindow() {
        mHomeLayout.addView(mNextEventLayout);
    }

    private void refreshNextEvent(Button nextEventBttn) {
        initEventWindow();
        initNextEventPopUp();
        initEventExitButton(nextEventBttn);
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


    }

    /*
      initiates close button of the calendar popup
       */
    private void initEventExitButton(Button nextEventBttn) {
        Button mCloseEventPopup = (Button) mNextEventLayout.findViewById(R.id.exitEventButton);
        mCloseEventPopup.requestFocus();
        //close the popup window on button click
        mCloseEventPopup.setOnClickListener(v -> {
            if (mNextEventLayout != null) {
                mHomeLayout.removeView(mNextEventLayout);
                nextEventBttn.setBackgroundResource(R.drawable.ic_next_event_button);
            } else {
                Log.d(TAG, "could not close next event window, view nextEventLayout is null");
            }
        });
    }

    /*
    set up text in next event window
     */
    private void initNextEventPopUp() {
        //todo: inverted these lines
        mCalendar = SettingsPersonalActivity.mSelectedCalendar;
        boolean isCalendarSelected = mCalendar != null;

        if (!isCalendarSelected) {
            mNextEventdate.setText("");
            mNextEventTitle.setText("");
            mNextEventlocation.setText("");
            mNextEventTime.setText("set up Google Calendar account in settings");
        } else if (!mCalendar.hasNextEvent()) {
            mNextEventdate.setText("");
            mNextEventTitle.setText("");
            mNextEventlocation.setText("");
            mNextEventTime.setText("No upcoming events in the next 24h");
        } else {
            Event nextEvent = mCalendar.getNextEvent();
            mNextEventdate.setText(nextEvent.getmNextEventDate());
            mNextEventTitle.setText(nextEvent.getmNextEventTitle());
            mNextEventTime.setText(nextEvent.getmNextEventStartTime() + "-" + nextEvent.getmNextEventEndTime());
            mNextEventlocation.setText(nextEvent.getmNextEventLocation());
        }
    }

    private void notifyOfUpcomingEvent(Button eventBttn) {
        mCalendar = SettingsPersonalActivity.mSelectedCalendar;
        if (mCalendar != null && mCalendar.hasNextEvent()) {
            Event nextEvent = mCalendar.getNextEvent();
            Log.d(TAG, "nextEvent.upcomingEventSoon(): " + nextEvent.upcomingEventSoon());
            if (nextEvent.upcomingEventSoon() && mNotificationStatus == Notification.NONE) {
                eventBttn.setBackgroundResource(R.drawable.ic_next_event_button_highlight);
                mNotificationStatus = Notification.UNOPEN;
            }
        }
    }

    public CalendarObject getStoredCalendar() {
        String id = null;
        try {
            File calendarFile = new File("calendar_data.txt");
            BufferedReader bufferedR = new BufferedReader(new FileReader(calendarFile));
            id = bufferedR.readLine();
            Log.d(TAG, "ID from text file: " + id);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Firstline is : " + id);

        return null;
    }

    public void storeId(String id) {
        File oldFile = new File("calendar_data.txt");
        oldFile.delete();
        File newFile = new File("calendar_data.txt");
        try {

            FileWriter fileW = new FileWriter(newFile, false);
            fileW.write(id);
            fileW.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeToFile(String data, Context context) {
        try {
            Log.d(TAG, "about to write: " + data);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("calendar_data.txt", Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    public String readFromFile(Context context) {
        String ret = "";
        try {
            InputStream inputStream = context.openFileInput("calendar_data.txt");

            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ((receiveString = bufferedReader.readLine()) != null) {
                    stringBuilder.append(receiveString);
                }
                inputStream.close();
                ret = stringBuilder.toString();
            }
        } catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }
        Log.d(TAG, "READ FROM FILE: " + ret);
        return ret;
    }

    @SuppressLint("MissingPermission")
    public CalendarObject getCalendarFromId(String id, Activity activity) {
        CalendarObject savedCalendar = null;
        //init cursor
        String[] projection = new String[]{
                CalendarContract.Calendars.CALENDAR_DISPLAY_NAME //0
        };
        String selection = "_id=?";
        String[] selectionArgs = new String[]{id};
        ContentResolver contentResolver = App.getContext().getContentResolver();
        Uri uri = CalendarContract.Calendars.CONTENT_URI;
        if (getCalendarPermissions(activity)) {
            Cursor calendarCursor = contentResolver.query(uri, projection, selection, selectionArgs, null);
            if (calendarCursor.moveToNext()) {
                String displayName = calendarCursor.getString(0);
                savedCalendar = new CalendarObject(id, displayName);
                Log.d(TAG, "assigning savedCalendar to id : " + savedCalendar.getCalendarID());
            }
        }
        Log.d(TAG, "returned value savedCalendar id : " + savedCalendar);
        return savedCalendar;
    }

    public void resetNotificationState() {
        mNotificationStatus = Notification.NONE;
    }

    public boolean getCalendarPermissions(Activity activity) {
        if (ContextCompat.checkSelfPermission(App.getContext().getApplicationContext(), Manifest.permission.READ_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
            String[] permissions = {Manifest.permission.READ_CALENDAR};
            ActivityCompat.requestPermissions(activity, permissions, 1235);
        }

        if (ContextCompat.checkSelfPermission(App.getContext().getApplicationContext(), Manifest.permission.READ_CALENDAR) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        return false;
    }
}
