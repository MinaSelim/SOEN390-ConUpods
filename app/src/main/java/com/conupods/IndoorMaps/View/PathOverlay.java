package com.conupods.IndoorMaps.View;

import android.os.Bundle;

import android.content.res.Resources;
import android.os.AsyncTask;
import android.util.Log;

import com.conupods.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.CameraUpdateFactory;

import java.util.ArrayList;
import java.util.List;


import android.graphics.Color;

import android.view.View;

import com.conupods.R;

public class PathOverlay {

    private GoogleMap mMap;

    LatLng mNWHall = new LatLng(45.497161, -73.579554);
    LatLng mSWHall = new LatLng(45.496827, -73.578850);
    LatLng mSEHall = new LatLng(45.497370, -73.578337);
    LatLng mNEHall = new LatLng(45.497710, -73.579035);
    public PathOverlay(GoogleMap map)
    {
        mMap = map;
    }

    //createPolyline will take a list of LatLng and generate a polyline between those points
    //in the order of the input list
    public void createPolyLine(int[][] coordinates){

        List<LatLng> points=new ArrayList<LatLng>();

        for (int x = 0; x < coordinates.length; x++) {
                points.add(new LatLng(mNWHall.latitude-(mNWHall.latitude-mSWHall.latitude)*(coordinates[x][0]/275)-(mNWHall.latitude-mNEHall.latitude)*(coordinates[x][1]/275),mNWHall.longitude-(mNWHall.longitude-mSWHall.longitude)*(coordinates[x][0])/275-(mNWHall.longitude-mNEHall.longitude)*(coordinates[x][1])/275));
        }

        PolylineOptions desiredPoints = new PolylineOptions();

        for (LatLng coordinate:points) {
            desiredPoints.add(coordinate);
        }
        Polyline line = mMap.addPolyline(desiredPoints);
        line.setColor(Color.RED);
        line.setWidth(5);
    }
    protected void onPostExecute(int[][] indoorPath){
        createPolyLine(indoorPath);
    }
}
