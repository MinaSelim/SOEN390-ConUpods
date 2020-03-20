package com.conupods.IndoorMaps.View;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.LatLng;


import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.graphics.Color;

import astar.AStar;
import android.view.View;

import com.conupods.R;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Color;

public class PathOverlay {

    private GoogleMap mMap;
    private AStar aStar;

    final static BuildingsBean buildings = new BuildingsBean();

    private final BuildingsBean BUILDINGS = new BuildingsBean();

    private final static int PIXELS = 275;

    public PathOverlay(GoogleMap map) {
        mMap = map;
    }

    //createPolyline will take a list of LatLng and generate a polyline between those points
    //in the order of the input list
    public void createPolyLine(String building, int floor, int[][] coordinates) {

        List<LatLng> points = new ArrayList<LatLng>();

        //converting the provided coordinates too their latitude
        // and longitude and then adding them to an array
        //
        //We'll have to think about a template method
        switch (building) {
            case "H":
                for (int x = 0; x < coordinates.length; x++) {
                    points.add(
                            new LatLng(
                                    BUILDINGS.NWHALL.latitude - (BUILDINGS.NWHALL.latitude - BUILDINGS.SWHALL.latitude) * ((float) coordinates[x][0] / PIXELS) - (BUILDINGS.NWHALL.latitude - BUILDINGS.NEHALL.latitude) * ((float) coordinates[x][1] / PIXELS),
                                    BUILDINGS.NWHALL.longitude - (BUILDINGS.NWHALL.longitude - BUILDINGS.SWHALL.longitude) * ((float) coordinates[x][0]) / PIXELS - (BUILDINGS.NWHALL.longitude - BUILDINGS.NEHALL.longitude) * ((float) coordinates[x][1]) / PIXELS
                            ));
                }
                break;
            case "JMSB":
                for (int x = 0; x < coordinates.length; x++) {
                    points.add(
                            new LatLng(
                                    BUILDINGS.NWJMSB.latitude - (BUILDINGS.NWJMSB.latitude - BUILDINGS.SWJMSB.latitude) * ((float) coordinates[x][0] / PIXELS) - (BUILDINGS.NWJMSB.latitude - BUILDINGS.NEJMSB.latitude) * ((float) coordinates[x][1] / PIXELS),
                                    BUILDINGS.NWJMSB.longitude - (BUILDINGS.NWJMSB.longitude - BUILDINGS.SWJMSB.longitude) * ((float) coordinates[x][0]) / PIXELS - (BUILDINGS.NWJMSB.longitude - BUILDINGS.NEJMSB.longitude) * ((float) coordinates[x][1]) / PIXELS
                            ));
                }
                break;
            case "CC":
                for (int x = 0; x < coordinates.length; x++) {
                    points.add(
                            new LatLng(
                                    BUILDINGS.NWCC.latitude - (BUILDINGS.NWCC.latitude - BUILDINGS.SWCC.latitude) * ((float) coordinates[x][0] / PIXELS) - (BUILDINGS.NWCC.latitude - BUILDINGS.NECC.latitude) * ((float) coordinates[x][1] / PIXELS),
                                    BUILDINGS.NWCC.longitude - (BUILDINGS.NWCC.longitude - BUILDINGS.SWCC.longitude) * ((float) coordinates[x][0]) / PIXELS - (BUILDINGS.NWCC.longitude - BUILDINGS.NECC.longitude) * ((float) coordinates[x][1]) / PIXELS
                            ));
                }
                break;
            case "VE":
                for (int x = 0; x < coordinates.length; x++) {
                    points.add(
                            new LatLng(
                                    BUILDINGS.NWVE.latitude - (BUILDINGS.NWVE.latitude - BUILDINGS.SWVE.latitude) * ((float) coordinates[x][0] / PIXELS) - (BUILDINGS.NWVE.latitude - BUILDINGS.NEVE.latitude) * ((float) coordinates[x][1] / PIXELS),
                                    BUILDINGS.NWVE.longitude - (BUILDINGS.NWVE.longitude - BUILDINGS.SWVE.longitude) * ((float) coordinates[x][0]) / PIXELS - (BUILDINGS.NWVE.longitude - BUILDINGS.NEVE.longitude) * ((float) coordinates[x][1]) / PIXELS
                            ));
                }
                break;
            case "VL":
                for (int x = 0; x < coordinates.length; x++) {
                    points.add(
                            new LatLng(
                                    BUILDINGS.NWVL.latitude - (BUILDINGS.NWVL.latitude - BUILDINGS.SWVL.latitude) * ((float) coordinates[x][0] / PIXELS) - (BUILDINGS.NWVL.latitude - BUILDINGS.NEVL.latitude) * ((float) coordinates[x][1] / PIXELS),
                                    BUILDINGS.NWVL.longitude - (BUILDINGS.NWVL.longitude - BUILDINGS.SWVL.longitude) * ((float) coordinates[x][0]) / PIXELS - (BUILDINGS.NWVL.longitude - BUILDINGS.NEVL.longitude) * ((float) coordinates[x][1]) / PIXELS
                            ));
                }
            default:
                // TO DO
                break;
        }

        PolylineOptions desiredPoints = new PolylineOptions();

        //add array of latlng to the path
        for (LatLng coordinate : points) {
            desiredPoints.add(coordinate);
        }

        Polyline line = mMap.addPolyline(desiredPoints);
        line.setColor(Color.RED);
        line.setWidth(5);
    }
}
