// TODO: This activity needs massive refactoring

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
import android.widget.Switch;
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
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.DirectionsApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.PendingResult;
import com.google.maps.internal.PolylineEncoding;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.DirectionsRoute;
import com.google.maps.model.Duration;
import com.google.maps.model.TravelMode;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ModeSelectActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private LatLng mOrigin;
    private LatLng mDestination;
    private GeoApiContext mGoogleAPIContext;

    private String fromLongName, fromCode, toLongName, toCode;

    private LatLng mTerminalA;
    private LatLng mTerminalB;

    private LatLng terminalSGW = new LatLng(45.497092, -73.5788); // H
    private LatLng terminalLOY = new LatLng(45.458204, -73.6403); // CC

    private String shuttleDebug = "shuttle debug";

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
//        for (TravelMode mode : TravelMode.values()) {
//            if (!mode.equals(TravelMode.BICYCLING) && !mode.equals(TravelMode.UNKNOWN)) {
//                computeDirections(mOrigin, mDestination, mode);
//            }
//        }

        int dayOfWeek = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
        long currentTime = Calendar.getInstance().getTime().getTime();

        dayOfWeek = Calendar.MONDAY;
        currentTime = 10 * 3600 * 1000;

        long morningCutoff = 7 * 3600 * 1000;
        long eveningCutoff = 22 * 3600 * 1000; // shhh, don't tell anyone

        if (dayOfWeek == Calendar.SUNDAY || dayOfWeek == Calendar.SATURDAY ||
            currentTime < morningCutoff  || currentTime > eveningCutoff) {
            Toast.makeText(ModeSelectActivity.this, "Shuttle not availble on the weekend", Toast.LENGTH_SHORT).show();
        }
        else {
            computeShuttleDirections(mOrigin, mDestination);

            // TODO: fill in the onClick for the shuttle mode
            Button shuttleBTN = (Button) findViewById(R.id.modeSelect_shuttleButton);
            shuttleBTN.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    // if destination is on the same campus just use walking
                    // use driving or public transport of the user is far from either campus?
                    // discuss with team
                    launchModeSelectIntent("SHUTTLE");
                }
            });
        }

        // Set the from and to fields
        //String from_location = mPreviousActivityIntent.getStringExtra("fromString");
        Button fromButton = (Button) findViewById(R.id.modeSelect_from);
        fromButton.setText("From: " + fromLongName);

        //String to_location = mPreviousActivityIntent.getStringExtra("toString");
        if (toLongName == null) {
            Button toButton = (Button) findViewById(R.id.modeSelect_to);
            toButton.setText("To: " + toCode);

        } else {
            Button toButton = (Button) findViewById(R.id.modeSelect_to);
            toButton.setText("To: " + toLongName);
        }


        Button walkingBTN = (Button) findViewById(R.id.modeSelect_walkingButton);
        walkingBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchModeSelectIntent(TravelMode.WALKING.toString());
            }
        });

        Button drivingBTN = (Button) findViewById(R.id.modeSelect_drivingButton);
        drivingBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchModeSelectIntent(TravelMode.DRIVING.toString());
            }
        });

        Button transitBTN = (Button) findViewById(R.id.modeSelect_transitButton);
        transitBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchModeSelectIntent(TravelMode.TRANSIT.toString());
            }
        });
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
        fromLongName = intent.getStringExtra("fromLongName");

        if (fromLongName.equals("Current Location")) {
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
            fromCode = "NA";
        } else {
            mOrigin = intent.getParcelableExtra("fromCoordinates");
            fromCode = intent.getStringExtra("fromCode");
        }

        mDestination = intent.getParcelableExtra("toCoordinates");
        toLongName = intent.getStringExtra("toLongName");
        toCode = intent.getStringExtra("toCode");
    }

    private void loadLocationsIntoIntent(Intent intent) {
        intent.putExtra("fromCoordinates", mOrigin);
        intent.putExtra("fromCode", fromCode);
        intent.putExtra("fromLongName", fromLongName);

        intent.putExtra("toCoordinates", mDestination);
        intent.putExtra("toCode", toCode);
        intent.putExtra("toLongName", toLongName);
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
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {

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
                        TextView drivingDuration = (TextView) findViewById(R.id.modeSelect_drivingDuration);
                        drivingDuration.setText(duration.humanReadable);

                        TextView drivingTimes = (TextView) findViewById(R.id.modeSelect_drivingTimes);
                        drivingTimes.setText(startTimeFormatted + " - " + endTimeFormatted);

                        break;
                    case WALKING:
                        TextView walkingDuration = (TextView) findViewById(R.id.modeSelect_walkingDuration);
                        walkingDuration.setText(duration.humanReadable);

                        TextView walkingTimes = (TextView) findViewById(R.id.modeSelect_walkingTimes);
                        walkingTimes.setText(startTimeFormatted + " - " + endTimeFormatted);

                        break;
                    case TRANSIT:
                        TextView transitDuration = (TextView) findViewById(R.id.modeSelect_publicTransitDuration);
                        transitDuration.setText(duration.humanReadable);

                        TextView transitTimes = (TextView) findViewById(R.id.modeSelect_publicTransitTimes);
                        transitTimes.setText(startTimeFormatted + " - " + endTimeFormatted);

                        break;
                    default:
                        Log.d("Unexpected Mode", "updateViews: received result with unexpected transit mode");
                }
            }
        });
    }

    // polyline function - can be modified to work with a single route
    private void addPolyLinesToMap(final DirectionsResult result, TravelMode mode) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                for (DirectionsRoute route : result.routes) {
                    List<com.google.maps.model.LatLng> decodedPath = PolylineEncoding.decode(route.overviewPolyline.getEncodedPath());

                    List<LatLng> newDecodedPath = new ArrayList<>();
                    for (com.google.maps.model.LatLng latLng : decodedPath) {
                        newDecodedPath.add(new LatLng(latLng.lat, latLng.lng));
                    }

                    Polyline polyline = mMap.addPolyline(new PolylineOptions().addAll(newDecodedPath));
                }
            }
        });
    }

    private void computeShuttleDirections(LatLng origin, LatLng destination) {
        Log.d(shuttleDebug, "enter: computeShuttleDirections");
        // TODO: finalize terminal locations
//        LatLng terminalSGW = new LatLng(45.497092, -73.5788); // H
//        LatLng terminalLOY = new LatLng(45.458204, -73.6403); // CC

        float[] distanceFromSGW = new float[1];
        float[] distanceFromLOY = new float[1];

        Location.distanceBetween(origin.latitude, origin.longitude,
                terminalSGW.latitude, terminalSGW.longitude, distanceFromSGW);

        Location.distanceBetween(origin.latitude, origin.longitude,
                terminalLOY.latitude, terminalLOY.longitude, distanceFromLOY);

        final double maxRadius = 3000.0; // in meters

        String campus;

        if (distanceFromSGW[0] < maxRadius) {
            // user is on the sgw campus
            mTerminalA = terminalSGW;
            mTerminalB = terminalLOY;
            campus = "SGW";
        } else if (distanceFromLOY[0] < maxRadius) {
            // user is on the loy campus
            mTerminalA = terminalLOY;
            mTerminalB = terminalSGW;
            campus = "LOY";
        } else {
            Toast.makeText(ModeSelectActivity.this,
                    "You must be on a campus to use the shuttle option.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (mTerminalA != null && mTerminalB != null) {
            shuttleWalkToTerminal(campus);
        } else {
            Toast.makeText(ModeSelectActivity.this,
                    "Error computing shuttle directions. Terminal is null.", Toast.LENGTH_SHORT).show();
        }
    }

    private void shuttleWalkToTerminal(String campus) {
        Log.d(shuttleDebug, "enter: shuttleWalkToTerminal");

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
        long currentTimeInMs = currentTime.getTime();

        // --- FOR TESTING --- //
        currentTimeInMs = 10 * 3600 * 1000;

        // refactor in sep method
        long arrivalTimeAfterWalkInSecond = (currentTimeInMs / 1000) + durationOfWalkToTerminal;

        long arrivalTimeAfterWalkInMinutes = arrivalTimeAfterWalkInSecond / 60;
        long arrivalHours = arrivalTimeAfterWalkInMinutes / 60;
        long arrivalMinutes = arrivalTimeAfterWalkInMinutes % 60;

        String formattedArrivalAtTerminal = arrivalHours + ":" + arrivalMinutes;

        String timeOfNextShuttle = new Shuttle().getNextShuttle(startingCampus, standardSchedule, formattedArrivalAtTerminal);

        // refactor in sep method
        String[] parts = timeOfNextShuttle.split(":");
        long timeOfNextShuttleHours = Long.parseLong(parts[0]);
        long timeOfNextShuttleMinutes = Long.parseLong(parts[1]);

        long nextShuttleTimeInSeconds = (timeOfNextShuttleHours * 3600) + (timeOfNextShuttleMinutes * 60);

        return nextShuttleTimeInSeconds - (arrivalTimeAfterWalkInMinutes * 60);
    }

    private void shuttleDrive(long walkAndWaitInSeconds) {
        Log.d(shuttleDebug, "enter: shuttleDrive");

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
                    Duration shuttleDriveduration = result.routes[0].legs[0].duration;

                    shuttleWalkToDestination(walkAndWaitInSeconds + shuttleDriveduration.inSeconds);
                }
            }

            @Override
            public void onFailure(Throwable e) {
                Log.d("OUTDOORSERVICES", "Failed to get directions");
            }
        });
    }

    private void shuttleWalkToDestination(long walkWaitAndDrive) {
        Log.d(shuttleDebug, "enter: shuttleWalkToDestination");


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

                    updateShuttleView(walkWaitAndDrive + walkToDestinationDuration.inSeconds);
                }
            }

            @Override
            public void onFailure(Throwable e) {
                Log.d("OUTDOORSERVICES", "Failed to get directions");
            }
        });
    }

    private void updateShuttleView(long shuttleTripDurationInSeconds) {
        Log.d(shuttleDebug, "enter: updateShuttleView");


        Date currentTime = Calendar.getInstance().getTime();
        long currentTimeInMs = currentTime.getTime();

        currentTimeInMs = 10 * 3600 * 1000;

        // refactor into sep method
        long durationHours = shuttleTripDurationInSeconds / 3600;
        long durationMinutes = shuttleTripDurationInSeconds % 3600;

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
        Date arrivalTimeAtDestination = new Date(currentTimeInMs + (shuttleTripDurationInSeconds * 1000));

        String startTimeFormatted = formatPattern.format(currentTime);
        String endTimeFormatted = formatPattern.format(arrivalTimeAtDestination);

        TextView shuttleDuration = (TextView) findViewById(R.id.modeSelect_shuttleDuration);
        shuttleDuration.setText(formattedDuration);

        TextView shuttleTimes = (TextView) findViewById(R.id.modeSelect_shuttleTimes);
        shuttleTimes.setText(startTimeFormatted + " - " + endTimeFormatted);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        MarkerOptions destinationMarker = new MarkerOptions();
        destinationMarker.position(mDestination);
        destinationMarker.title(toLongName);
        // Animating to the touched position
        googleMap.animateCamera(CameraUpdateFactory.newLatLng(mDestination));
        // Placing a marker on the touched position
        googleMap.addMarker(destinationMarker);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(mDestination));
    }
}