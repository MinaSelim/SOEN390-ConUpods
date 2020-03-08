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

    LatLng NWHall = new LatLng(45.497161, -73.579554);
    LatLng SWHall = new LatLng(45.496827, -73.578850);
    LatLng SEHall = new LatLng(45.497370, -73.578337);
    LatLng NEHall = new LatLng(45.497710, -73.579035);
    public PathOverlay(GoogleMap map)
    {
        mMap = map;
    }

    //createPolyline will take a list of LatLng and generate a polyline between those points
    //in the order of the input list
    public void createPolyLine(){
        List<LatLng> points=new ArrayList<LatLng>();
        points.add(NWHall);
        points.add(new LatLng(((SEHall.latitude-SWHall.latitude)/273)*100+SWHall.latitude,((SWHall.longitude-SEHall.longitude)/273)*100+SWHall.longitude));
        points.add(SEHall);
        points.add(NEHall);
        PolylineOptions desiredPoints = new PolylineOptions();

        for (LatLng coordinates:points) {
            desiredPoints.add(coordinates);
        }
        Polyline line = mMap.addPolyline(desiredPoints);
        line.setColor(Color.RED);
        line.setWidth(5);
    }
    protected void onPostExecute(){
        createPolyLine();
    }
}
