package com.conupods.OutdoorMaps.View.Directions;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.conupods.OutdoorMaps.Services.Shuttle;
import com.conupods.OutdoorMaps.View.SearchSetupView.FinalizeSearchActivity;
import com.conupods.OutdoorMaps.View.SearchView.SearchActivity;
import com.conupods.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.DirectionsApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.PendingResult;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.Duration;
import com.google.maps.model.TravelMode;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ModeSelectActivity extends FragmentActivity implements OnMapReadyCallback {

    private LatLng mOrigin;
    private LatLng mDestination;
    private GeoApiContext mGoogleAPIContext;

    private String mFromLongName;
    private String mFromCode;
    private String mToLongName;
    private String mToCode;

    private LatLng mTerminalA;
    private LatLng mTerminalB;

    private LatLng mTerminalSGW = new LatLng(45.497092, -73.5788); // H
    private LatLng mTerminalLOY = new LatLng(45.458204, -73.6403); // CC

    private boolean mShuttleAvailability = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mode_select);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mGoogleAPIContext = new GeoApiContext.Builder()
                .apiKey(getString(R.string.Google_API_Key))
                .build();

        // add info to buttons
        Intent passedIntent = getIntent();
        unpackIntent(passedIntent);

        // Compute the directions for each modes we use
        // when compute directions runs it calls a method to update the view elements for each route
        // i.e. when computing the route by car it updates the duration, start, and end time fields
        for (TravelMode mode : TravelMode.values()) {
            if (!mode.equals(TravelMode.BICYCLING) && !mode.equals(TravelMode.UNKNOWN)) {
                computeDirections(mOrigin, mDestination, mode);
            }
        }

        int dayOfWeek;
        long currentTime;

        //for testing on the weekend
        dayOfWeek = Calendar.MONDAY;
        currentTime = 10 * 3600 * 1000;

        long morningCutoff = 7 * 3600 * 1000;
        long eveningCutoff = 22 * 3600 * 1000;

        if (dayOfWeek == Calendar.SUNDAY || dayOfWeek == Calendar.SATURDAY || currentTime < morningCutoff || currentTime > eveningCutoff) {
            Toast.makeText(ModeSelectActivity.this, "Shuttle is not availble", Toast.LENGTH_SHORT).show();
        } else {
            computeShuttleDirections(mOrigin, mDestination);

            // shuttleAvailbility is false when routes are not supported by the shuttle
            if (mShuttleAvailability) {
                Button shuttleBTN = findViewById(R.id.modeSelect_shuttleButton);
                shuttleBTN.setOnClickListener(view -> {
                    // if destination is on the same campus just use walking
                    // use driving or public transport of the user is far from either campus?
                    // discuss with team
                    launchModeSelectIntent("SHUTTLE");
                });
            }
        }

        // Set the from and to fields
        Button fromButton = findViewById(R.id.modeSelect_from);
        fromButton.setText("From: " + mFromLongName);

        if (mToLongName == null) {
            Button toButton = findViewById(R.id.modeSelect_to);
            toButton.setText("To: " + mToCode);

        } else {
            Button toButton = findViewById(R.id.modeSelect_to);
            toButton.setText("To: " + mToLongName);
        }


        Button walkingBTN = findViewById(R.id.modeSelect_walkingButton);
        walkingBTN.setOnClickListener(view -> launchModeSelectIntent("WALKING"));

        Button drivingBTN = findViewById(R.id.modeSelect_drivingButton);
        drivingBTN.setOnClickListener(view -> launchModeSelectIntent("DRIVING"));

        Button transitBTN = findViewById(R.id.modeSelect_transitButton);
        transitBTN.setOnClickListener(view -> launchModeSelectIntent("TRANSIT"));
    }

    public void onClickSetOrigin(View view) {
        Intent modeSelectIntent = new Intent(this, FinalizeSearchActivity.class);
        loadLocationsIntoIntent(modeSelectIntent);
        startActivity(modeSelectIntent);
    }

    public void onClickSetDestination(View view) {
        Intent modeSelectIntent = new Intent(this, SearchActivity.class);
        loadLocationsIntoIntent(modeSelectIntent);
        startActivity(modeSelectIntent);
    }

    private void unpackIntent(Intent intent) {
        mFromLongName = intent.getStringExtra("fromLongName");

        if (mFromLongName.equals("Current Location")) {
            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

            if (ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (location == null) {
                location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                if (location == null) {
                    mOrigin = new LatLng(45.4973, -73.5790);    // Hall building
                }
                mOrigin = new LatLng(location.getLatitude(), location.getLongitude());
            }
            mOrigin = new LatLng(location.getLatitude(), location.getLongitude());
            mFromCode = "NA";
        } else {
            mOrigin = intent.getParcelableExtra("fromCoordinates");
            mFromCode = intent.getStringExtra("fromCode");
        }

        mDestination = intent.getParcelableExtra("toCoordinates");
        mToLongName = intent.getStringExtra("toLongName");
        mToCode = intent.getStringExtra("toCode");
    }

    private void loadLocationsIntoIntent(Intent intent) {
        intent.putExtra("fromCoordinates", mOrigin);
        intent.putExtra("fromCode", mFromCode);
        intent.putExtra("fromLongName", mFromLongName);

        intent.putExtra("toCoordinates", mDestination);
        intent.putExtra("toCode", mToCode);
        intent.putExtra("toLongName", mToLongName);
    }

    // Extracted details related to creating and launching intents for different modes
    private void launchModeSelectIntent(String mode) {
        Intent modeSelectIntent = new Intent(this, NavigationActivity.class);
        loadLocationsIntoIntent(modeSelectIntent);
        modeSelectIntent.putExtra("mode", mode);
        if (mode.equalsIgnoreCase("SHUTTLE")) {
            modeSelectIntent.putExtra("terminalA", mTerminalA);
            modeSelectIntent.putExtra("terminalB", mTerminalB);
        }
        startActivity(modeSelectIntent);
    }

    // Sends Directions API request
    // Calls function to update the view elements on success
    public void computeDirections(LatLng origin, LatLng destination, TravelMode mode) {

        DirectionsApiRequest directions = new DirectionsApiRequest(mGoogleAPIContext);

        directions.origin(new com.google.maps.model.LatLng(origin.latitude, origin.longitude));
        directions.destination(new com.google.maps.model.LatLng(destination.latitude, destination.longitude));
        directions.mode(mode);

        directions.setCallback(new PendingResult.Callback<DirectionsResult>() {

            @Override
            public void onResult(DirectionsResult result) {

                if (result == null || result.routes.length == 0 || result.routes[0].legs.length == 0) {
                    Toast.makeText(ModeSelectActivity.this, "Could not load directions", Toast.LENGTH_SHORT).show();
                } else {
                    updateView(result, mode);
                }
            }

            @Override
            public void onFailure(Throwable e) {
                Log.d("OUTDOORSERVICES", "Failed to get directions");
            }
        });
    }

    // Updates the view elements given DirectionResult and a TravelMode
    // Gets details from the result and selects the view elements with the mode
    // Grabs the main thread so that DirectionResult is not null
    private void updateView(final DirectionsResult result, TravelMode mode) {
        new Handler(Looper.getMainLooper()).post(() -> {

            // Compute arrival time from duration
            Duration duration = result.routes[0].legs[0].duration;
            long durationInMs = 1000 * duration.inSeconds;

            Date currentTime = Calendar.getInstance().getTime();
            long currentTimeInMs = currentTime.getTime();

            Date arrivalTime = new Date(currentTimeInMs + durationInMs);

            // Format start and end times
            SimpleDateFormat formatPattern = new SimpleDateFormat("h:mm a");

            String startTimeFormatted = formatPattern.format(currentTime);
            String endTimeFormatted = formatPattern.format(arrivalTime);

            switch (mode) {
                case DRIVING:
                    TextView drivingDuration = findViewById(R.id.modeSelect_drivingDuration);
                    drivingDuration.setText(duration.humanReadable);

                    TextView drivingTimes = findViewById(R.id.modeSelect_drivingTimes);
                    drivingTimes.setText(startTimeFormatted + " - " + endTimeFormatted);

                    break;
                case WALKING:
                    TextView walkingDuration = findViewById(R.id.modeSelect_walkingDuration);
                    walkingDuration.setText(duration.humanReadable);

                    TextView walkingTimes = findViewById(R.id.modeSelect_walkingTimes);
                    walkingTimes.setText(startTimeFormatted + " - " + endTimeFormatted);

                    break;
                case TRANSIT:
                    TextView transitDuration = findViewById(R.id.modeSelect_publicTransitDuration);
                    transitDuration.setText(duration.humanReadable);

                    TextView transitTimes = findViewById(R.id.modeSelect_publicTransitTimes);
                    transitTimes.setText(startTimeFormatted + " - " + endTimeFormatted);

                    break;
                default:
                    Log.d("Unexpected Mode", "updateViews: received result with unexpected transit mode");
            }
        });
    }

    private void computeShuttleDirections(LatLng origin, LatLng destination) {
        float[] distanceFromSGW = new float[1];
        float[] distanceFromLOY = new float[1];

        Location.distanceBetween(origin.latitude, origin.longitude,
                mTerminalSGW.latitude, mTerminalSGW.longitude, distanceFromSGW);

        Location.distanceBetween(origin.latitude, origin.longitude,
                mTerminalLOY.latitude, mTerminalLOY.longitude, distanceFromLOY);

        final double maxRadius = 3000.0; // in meters

        String campus;

        if (distanceFromSGW[0] < maxRadius) {
            // user is on the sgw campus
            mTerminalA = mTerminalSGW;
            mTerminalB = mTerminalLOY;
            campus = "SGW";
        } else if (distanceFromLOY[0] < maxRadius) {
            // user is on the loy campus
            mTerminalA = mTerminalLOY;
            mTerminalB = mTerminalSGW;
            campus = "LOY";
        } else {
            Toast.makeText(ModeSelectActivity.this,
                    "You must be on a campus to use the shuttle option.", Toast.LENGTH_SHORT).show();
            mShuttleAvailability = false;
            return;
        }

        float[] distanceFromOriginToDestination = new float[1];

        Location.distanceBetween(mOrigin.latitude, mOrigin.longitude,
                mDestination.latitude, mDestination.longitude, distanceFromOriginToDestination);

        // check for valid destination
        if (distanceFromOriginToDestination[0] < maxRadius) {
            // user is on the same campus as the destination. Don't use shuttle
            Toast.makeText(ModeSelectActivity.this,
                    "Not a valid shuttle route", Toast.LENGTH_SHORT).show();

            mShuttleAvailability = false;
        } else if (mTerminalA != null && mTerminalB != null) {
            mShuttleAvailability = true;
            shuttleWalkToTerminal(campus);
        } else {
            Toast.makeText(ModeSelectActivity.this,
                    "Error computing shuttle directions. Terminal is null.", Toast.LENGTH_SHORT).show();
        }
    }

    private void shuttleWalkToTerminal(String campus) {
        DirectionsApiRequest directions = new DirectionsApiRequest(mGoogleAPIContext);

        directions.origin(new com.google.maps.model.LatLng(mOrigin.latitude, mOrigin.longitude));
        directions.destination(new com.google.maps.model.LatLng(mTerminalA.latitude, mTerminalA.longitude));
        directions.mode(TravelMode.WALKING);

        directions.setCallback(new PendingResult.Callback<DirectionsResult>() {

            @Override
            public void onResult(DirectionsResult result) {

                if (result == null || result.routes.length == 0 || result.routes[0].legs.length == 0) {
                    Toast.makeText(ModeSelectActivity.this, "Could not load directions", Toast.LENGTH_SHORT).show();
                } else {
                    // Compute arrival time from duration
                    Duration walkToTerminalduration = result.routes[0].legs[0].duration;
                    long durationOfWalk = walkToTerminalduration.inSeconds;

                    int dayOfWeek = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
                    boolean standardSchedule = true;
                    if (dayOfWeek == Calendar.FRIDAY) {
                        standardSchedule = false;
                    }

                    long waitTime = computeWaitTime(walkToTerminalduration.inSeconds, standardSchedule, campus);
                    shuttleDrive(durationOfWalk + waitTime);
                }
            }

            @Override
            public void onFailure(Throwable e) {
                Log.d("OUTDOORSERVICES", "Failed to get directions");
            }
        });
    }

    private long computeWaitTime(long durationOfWalkToTerminal, boolean standardSchedule, String startingCampus) {
        Date currentTime = Calendar.getInstance().getTime();
        long currentTimeInMs;

        // --- FOR TESTING --- //
        currentTimeInMs = 10 * 3600 * 1000;

        long arrivalTimeAfterWalkInSeconds = (currentTimeInMs / 1000) + durationOfWalkToTerminal;

        long arrivalTimeAfterWalkInMinutes = arrivalTimeAfterWalkInSeconds / 60;
        long arrivalHours = arrivalTimeAfterWalkInMinutes / 60;
        long arrivalMinutes = arrivalTimeAfterWalkInMinutes % 60;

        String arrivalHoursString = String.valueOf(arrivalHours);
        String arrivalMinutesString = String.valueOf(arrivalMinutes);

        if (String.valueOf(arrivalMinutes).length() == 1) {
            arrivalMinutesString += "0";
        }

        String formattedArrivalAtTerminal = arrivalHoursString + ":" + arrivalMinutesString;
        String timeOfNextShuttle = new Shuttle().getNextShuttle(startingCampus, standardSchedule, formattedArrivalAtTerminal);

        String[] parts = timeOfNextShuttle.split(":");
        long timeOfNextShuttleHours = Long.parseLong(parts[0]);
        long timeOfNextShuttleMinutes = Long.parseLong(parts[1]);

        long nextShuttleTimeInSeconds = (timeOfNextShuttleHours * 3600) + (timeOfNextShuttleMinutes * 60);

        return nextShuttleTimeInSeconds - arrivalTimeAfterWalkInSeconds;
    }

    private void shuttleDrive(long walkAndWaitInSeconds) {
        DirectionsApiRequest directions = new DirectionsApiRequest(mGoogleAPIContext);

        directions.origin(new com.google.maps.model.LatLng(mTerminalA.latitude, mTerminalA.longitude));
        directions.destination(new com.google.maps.model.LatLng(mTerminalB.latitude, mTerminalB.longitude));
        directions.mode(TravelMode.DRIVING);

        directions.setCallback(new PendingResult.Callback<DirectionsResult>() {

            @Override
            public void onResult(DirectionsResult result) {

                if (result == null || result.routes.length == 0 || result.routes[0].legs.length == 0) {
                    Toast.makeText(ModeSelectActivity.this, "Could not load directions", Toast.LENGTH_SHORT).show();
                } else {
                    // Compute arrival time from duration
                    Duration shuttleDriveDuration = result.routes[0].legs[0].duration;
                    shuttleWalkToDestination(walkAndWaitInSeconds + shuttleDriveDuration.inSeconds);
                }
            }

            @Override
            public void onFailure(Throwable e) {
                Log.d("OUTDOORSERVICES", "Failed to get directions");
            }
        });
    }

    private void shuttleWalkToDestination(long walkWaitAndDrive) {
        DirectionsApiRequest directions = new DirectionsApiRequest(mGoogleAPIContext);

        directions.origin(new com.google.maps.model.LatLng(mTerminalB.latitude, mTerminalB.longitude));
        directions.destination(new com.google.maps.model.LatLng(mDestination.latitude, mDestination.longitude));
        directions.mode(TravelMode.WALKING);

        directions.setCallback(new PendingResult.Callback<DirectionsResult>() {

            @Override
            public void onResult(DirectionsResult result) {

                if (result == null || result.routes.length == 0 || result.routes[0].legs.length == 0) {
                    Toast.makeText(ModeSelectActivity.this, "Could not load directions", Toast.LENGTH_SHORT).show();
                } else {
                    // Compute arrival time from duration
                    Duration walkToDestinationDuration = result.routes[0].legs[0].duration;
                    runOnUiThread(() -> updateShuttleView(walkWaitAndDrive + walkToDestinationDuration.inSeconds));
                }
            }

            @Override
            public void onFailure(Throwable e) {
                Log.d("OUTDOORSERVICES", "Failed to get directions");
            }
        });
    }

    private void updateShuttleView(long shuttleTripDurationInSeconds) {
        Date currentTime = Calendar.getInstance().getTime();
        long currentTimeInMs = currentTime.getTime();

        // --- FOR TESTING --- //
        currentTimeInMs = 10 * 3600 * 1000;

        // refactor into sep method
        long durationHours = shuttleTripDurationInSeconds / 3600;
        long durationMinutes = (shuttleTripDurationInSeconds / 60) - (durationHours * 60);

        StringBuilder formattedDuration = new StringBuilder();
        if (durationHours > 0) {
            if (durationHours == 1) {
                formattedDuration.append(durationHours + " hour ");
            } else {
                formattedDuration.append(durationHours + " hours ");
            }
        }
        formattedDuration.append(durationMinutes + " mins");

        // Format start and end times
        SimpleDateFormat formatPattern = new SimpleDateFormat("h:mm a");
        Date arrivalTimeAtDestination = new Date(currentTime.getTime() + (shuttleTripDurationInSeconds * 1000));

        String startTimeFormatted = formatPattern.format(currentTime);
        String endTimeFormatted = formatPattern.format(arrivalTimeAtDestination);

        TextView shuttleDuration = (TextView) findViewById(R.id.modeSelect_shuttleDuration);
        shuttleDuration.setText(formattedDuration);

        TextView shuttleTimes = (TextView) findViewById(R.id.modeSelect_shuttleTimes);
        shuttleTimes.setText(startTimeFormatted + " - " + endTimeFormatted);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        MarkerOptions destinationMarker = new MarkerOptions();
        destinationMarker.position(mDestination);
        destinationMarker.title(mToLongName);
        // Animating to the touched position
        googleMap.animateCamera(CameraUpdateFactory.newLatLng(mDestination));
        // Placing a marker on the touched position
        googleMap.addMarker(destinationMarker);
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(mDestination));
    }
}