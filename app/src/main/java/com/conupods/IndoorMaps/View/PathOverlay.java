package com.conupods.IndoorMaps.View;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.LatLng;


import com.conupods.IndoorMaps.View.BuildingsBean;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Color;

import astar.AStar;


public class PathOverlay {

    private GoogleMap mMap;
    private AStar aStar;


    private final BuildingsBean buildings = new BuildingsBean();

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
                                    buildings.NWHALL.latitude-(buildings.NWHALL.latitude-buildings.SWHALL.latitude)*((float) coordinates[x][0]/PIXELS)-(buildings.NWHALL.latitude-buildings.NEHALL.latitude)*((float) coordinates[x][1]/PIXELS),
                                    buildings.NWHALL.longitude-(buildings.NWHALL.longitude-buildings.SWHALL.longitude)*((float) coordinates[x][0])/PIXELS-(buildings.NWHALL.longitude-buildings.NEHALL.longitude)*((float) coordinates[x][1])/PIXELS
                            ));
                }
                break;
            case "JMSB":
                for (int x = 0; x < coordinates.length; x++) {
                    points.add(
                            new LatLng(
                                    buildings.NWJMSB.latitude-(buildings.NWJMSB.latitude-buildings.SWJMSB.latitude)*((float) coordinates[x][0]/PIXELS)-(buildings.NWJMSB.latitude-buildings.NEJMSB.latitude)*((float) coordinates[x][1]/PIXELS),
                                    buildings.NWJMSB.longitude-(buildings.NWJMSB.longitude-buildings.SWJMSB.longitude)*((float) coordinates[x][0])/PIXELS-(buildings.NWJMSB.longitude-buildings.NEJMSB.longitude)*((float) coordinates[x][1])/PIXELS
                            ));
                }
                break;
            case "CC":
                for (int x = 0; x < coordinates.length; x++) {
                    points.add(
                            new LatLng(
                                    buildings.NWCC.latitude-(buildings.NWCC.latitude-buildings.SWCC.latitude)*((float) coordinates[x][0]/PIXELS)-(buildings.NWCC.latitude-buildings.NECC.latitude)*((float) coordinates[x][1]/PIXELS),
                                    buildings.NWCC.longitude-(buildings.NWCC.longitude-buildings.SWCC.longitude)*((float) coordinates[x][0])/PIXELS-(buildings.NWCC.longitude-buildings.NECC.longitude)*((float) coordinates[x][1])/PIXELS
                            ));
                }
                break;
            case "VE":
                for (int x = 0; x < coordinates.length; x++) {
                    points.add(
                            new LatLng(
                                    buildings.NWVE.latitude-(buildings.NWVE.latitude-buildings.SWVE.latitude)*((float) coordinates[x][0]/PIXELS)-(buildings.NWVE.latitude-buildings.NEVE.latitude)*((float) coordinates[x][1]/PIXELS),
                                    buildings.NWVE.longitude-(buildings.NWVE.longitude-buildings.SWVE.longitude)*((float) coordinates[x][0])/PIXELS-(buildings.NWVE.longitude-buildings.NEVE.longitude)*((float) coordinates[x][1])/PIXELS
                            ));
                }
                break;
            case "VL":
                for (int x = 0; x < coordinates.length; x++) {
                    points.add(
                            new LatLng(
                                    buildings.NWVL.latitude-(buildings.NWVL.latitude-buildings.SWVL.latitude)*((float) coordinates[x][0]/PIXELS)-(buildings.NWVL.latitude-buildings.NEVL.latitude)*((float) coordinates[x][1]/PIXELS),
                                    buildings.NWVL.longitude-(buildings.NWVL.longitude-buildings.SWVL.longitude)*((float) coordinates[x][0])/PIXELS-(buildings.NWVL.longitude-buildings.NEVL.longitude)*((float) coordinates[x][1])/PIXELS
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
