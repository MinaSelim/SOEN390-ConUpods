package com.conupods.IndoorMaps.View;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.LatLng;


import com.conupods.IndoorMaps.View.BuildingsBean;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Color;

public class PathOverlay {

    private GoogleMap mMap;

    final static BuildingsBean buildings = new BuildingsBean();

    final static int PIXELS = 275;

    public PathOverlay(GoogleMap map) {
        mMap = map;
    }

    //createPolyline will take a list of LatLng and generate a polyline between those points
    //in the order of the input list
    public void CreatePolyLine(String building, int floor, int[][] coordinates) {

        List<LatLng> points=new ArrayList<LatLng>();

        //converting the provided coordinates too their latitude
        // and longitude and then adding them to an array
        if(building.equals("H")) {
            for (int x = 0; x < coordinates.length; x++) {
                points.add(
                        new LatLng(
                                buildings.NWHALL.latitude-(buildings.NWHALL.latitude-buildings.SWHALL.latitude)*(coordinates[x][0]/PIXELS)-(buildings.NWHALL.latitude-buildings.NEHALL.latitude)*(coordinates[x][1]/PIXELS),
                                buildings.NWHALL.longitude-(buildings.NWHALL.longitude-buildings.SWHALL.longitude)*(coordinates[x][0])/PIXELS-(buildings.NWHALL.longitude-buildings.NEHALL.longitude)*(coordinates[x][1])/PIXELS
                        ));
            }
        }
        else if(building.equals("JMSB")) {
            for (int x = 0; x < coordinates.length; x++) {
                points.add(
                        new LatLng(
                                buildings.NWJMSB.latitude-(buildings.NWJMSB.latitude-buildings.SWJMSB.latitude)*(coordinates[x][0]/PIXELS)-(buildings.NWJMSB.latitude-buildings.NEJMSB.latitude)*(coordinates[x][1]/PIXELS),
                                buildings.NWJMSB.longitude-(buildings.NWJMSB.longitude-buildings.SWJMSB.longitude)*(coordinates[x][0])/PIXELS-(buildings.NWJMSB.longitude-buildings.NEJMSB.longitude)*(coordinates[x][1])/PIXELS
                        ));
            }
        }
        else if(building.equals("CC")) {
            for (int x = 0; x < coordinates.length; x++) {
                points.add(
                        new LatLng(
                                buildings.NWCC.latitude-(buildings.NWCC.latitude-buildings.SWCC.latitude)*(coordinates[x][0]/PIXELS)-(buildings.NWCC.latitude-buildings.NECC.latitude)*(coordinates[x][1]/PIXELS),
                                buildings.NWCC.longitude-(buildings.NWCC.longitude-buildings.SWCC.longitude)*(coordinates[x][0])/PIXELS-(buildings.NWCC.longitude-buildings.NECC.longitude)*(coordinates[x][1])/PIXELS
                        ));
            }
        }
        else if(building.equals("VE")) {
            for (int x = 0; x < coordinates.length; x++) {
                points.add(
                        new LatLng(
                                buildings.NWVE.latitude-(buildings.NWVE.latitude-buildings.SWVE.latitude)*(coordinates[x][0]/PIXELS)-(buildings.NWVE.latitude-buildings.NEVE.latitude)*(coordinates[x][1]/PIXELS),
                                buildings.NWVE.longitude-(buildings.NWVE.longitude-buildings.SWVE.longitude)*(coordinates[x][0])/PIXELS-(buildings.NWVE.longitude-buildings.NEVE.longitude)*(coordinates[x][1])/PIXELS
                        ));
            }
    }
        else if(building.equals("VL")) {
            for (int x = 0; x < coordinates.length; x++) {
                points.add(
                        new LatLng(
                                buildings.NWVL.latitude-(buildings.NWVL.latitude-buildings.SWVL.latitude)*(coordinates[x][0]/PIXELS)-(buildings.NWVL.latitude-buildings.NEVL.latitude)*(coordinates[x][1]/PIXELS),
                                buildings.NWVL.longitude-(buildings.NWVL.longitude-buildings.SWVL.longitude)*(coordinates[x][0])/PIXELS-(buildings.NWVL.longitude-buildings.NEVL.longitude)*(coordinates[x][1])/PIXELS
                        ));
            }
        }

        PolylineOptions desiredPoints = new PolylineOptions();

        //add array of latlng to the path
        for (LatLng coordinate:points) {
            desiredPoints.add(coordinate);
        }

        Polyline line = mMap.addPolyline(desiredPoints);
        line.setColor(Color.RED);
        line.setWidth(5);
    }
}
