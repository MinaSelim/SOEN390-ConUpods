package com.conupods.OutdoorMaps.Services;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.conupods.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.DirectionsApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.PendingResult;
import com.google.maps.internal.PolylineEncoding;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.DirectionsRoute;
import com.google.maps.model.TravelMode;

import java.util.ArrayList;
import java.util.List;


public class OutdoorDirectionsService {

    private GoogleMap mMap;
    private GeoApiContext mGoogleAPIContext;
    private DirectionsResult mDirectionsResult = new DirectionsResult();
    private List<DirectionsRoute> mDirectionRoutes = new ArrayList<>();
    private int mTimesEntered = 0;

    public OutdoorDirectionsService(Activity activity, GoogleMap mapFragment) {
        Activity passedActivity = activity;

        mGoogleAPIContext = new GeoApiContext.Builder()
                .apiKey(activity.getString(R.string.Google_API_Key))
                .build();

        mMap = mapFragment;
    }

    public void computeDirections(LatLng origin, LatLng destination, TravelMode mode) {

        // this sends the request to the api
        DirectionsApiRequest directions = new DirectionsApiRequest(mGoogleAPIContext);

        directions.origin(new com.google.maps.model.LatLng(origin.latitude, origin.longitude));
        directions.destination(new com.google.maps.model.LatLng(destination.latitude, destination.longitude));

        directions.mode(mode);

        directions.setCallback(new PendingResult.Callback<DirectionsResult>() {
            @Override
            public void onResult(DirectionsResult result) {

                mDirectionsResult = result;
                setDirectionRoutes(result.routes);

                ++mTimesEntered;
            }

            @Override
            public void onFailure(Throwable e) {

            }
        });
    }

    // polyline function - can be modified to work with a single route
    private void addPolyLinesToMap(final DirectionsResult result) {
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


    private void setDirectionRoutes(DirectionsRoute[] routes) {
        if (routes == null)
            Log.d("Outdoorservice", "NUll route during setting");
        if (mTimesEntered == 0) {
            for (DirectionsRoute route : mDirectionRoutes) {
                mDirectionRoutes.add(route);
                Log.d("OUTDOORSERVICES", "FROM WITHIN SETTER LOOP " + route.summary);
            }
        }
        Log.d("OutdoorServices", "Summary within the setter " + mDirectionRoutes.get(0).summary);
    }

    public List<DirectionsRoute> getDirectionRoutes() {
        return mDirectionRoutes;
    }
}