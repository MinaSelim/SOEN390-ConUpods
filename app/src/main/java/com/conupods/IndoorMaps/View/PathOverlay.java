package com.conupods.IndoorMaps.View;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Color;

import astar.Spot;

public class PathOverlay {

    private GoogleMap mMap;


    private final BuildingsBean BUILDINGS = new BuildingsBean();

    private final static int PIXELS = 275;

    public PathOverlay(GoogleMap map) {
        mMap = map;
    }

    public void drawIndoorPath(Spot endSpot) {
        while(endSpot != null){
            List<LatLng> points = new ArrayList<LatLng>();

            //converting the provided coordinates too their latitude
            // and longitude and then adding them to an array
            //
            //We'll have to think about a template method

            switch (endSpot.getBuilding()) {
                case "H":
                        points.add(
                                new LatLng(
                                        BUILDINGS.NWHALL.latitude - (BUILDINGS.NWHALL.latitude - BUILDINGS.SWHALL.latitude) * ((float) endSpot.getX() / PIXELS) - (BUILDINGS.NWHALL.latitude - BUILDINGS.NEHALL.latitude) * ((float) endSpot.getY() / PIXELS),
                                        BUILDINGS.NWHALL.longitude - (BUILDINGS.NWHALL.longitude - BUILDINGS.SWHALL.longitude) * ((float) endSpot.getX()) / PIXELS - (BUILDINGS.NWHALL.longitude - BUILDINGS.NEHALL.longitude) * ((float) endSpot.getY()) / PIXELS
                                ));
                    break;
                case "JMSB":
                        points.add(
                                new LatLng(
                                        BUILDINGS.NWJMSB.latitude - (BUILDINGS.NWJMSB.latitude - BUILDINGS.SWJMSB.latitude) * ((float) endSpot.getX() / PIXELS) - (BUILDINGS.NWJMSB.latitude - BUILDINGS.NEJMSB.latitude) * ((float) endSpot.getY() / PIXELS),
                                        BUILDINGS.NWJMSB.longitude - (BUILDINGS.NWJMSB.longitude - BUILDINGS.SWJMSB.longitude) * ((float) endSpot.getX()) / PIXELS - (BUILDINGS.NWJMSB.longitude - BUILDINGS.NEJMSB.longitude) * ((float) endSpot.getY()) / PIXELS
                                ));
                    break;
                case "CC":
                        points.add(
                                new LatLng(
                                        BUILDINGS.NWCC.latitude - (BUILDINGS.NWCC.latitude - BUILDINGS.SWCC.latitude) * ((float) endSpot.getX() / PIXELS) - (BUILDINGS.NWCC.latitude - BUILDINGS.NECC.latitude) * ((float) endSpot.getY() / PIXELS),
                                        BUILDINGS.NWCC.longitude - (BUILDINGS.NWCC.longitude - BUILDINGS.SWCC.longitude) * ((float) endSpot.getX()) / PIXELS - (BUILDINGS.NWCC.longitude - BUILDINGS.NECC.longitude) * ((float) endSpot.getY()) / PIXELS
                                ));
                    break;
                case "VE":
                        points.add(
                                new LatLng(
                                        BUILDINGS.NWVE.latitude - (BUILDINGS.NWVE.latitude - BUILDINGS.SWVE.latitude) * ((float) endSpot.getX() / PIXELS) - (BUILDINGS.NWVE.latitude - BUILDINGS.NEVE.latitude) * ((float) endSpot.getY() / PIXELS),
                                        BUILDINGS.NWVE.longitude - (BUILDINGS.NWVE.longitude - BUILDINGS.SWVE.longitude) * ((float) endSpot.getX()) / PIXELS - (BUILDINGS.NWVE.longitude - BUILDINGS.NEVE.longitude) * ((float) endSpot.getY()) / PIXELS
                                ));
                    break;
                case "VL":
                        points.add(
                                new LatLng(
                                        BUILDINGS.NWVL.latitude - (BUILDINGS.NWVL.latitude - BUILDINGS.SWVL.latitude) * ((float) endSpot.getX() / PIXELS) - (BUILDINGS.NWVL.latitude - BUILDINGS.NEVL.latitude) * ((float) endSpot.getY() / PIXELS),
                                        BUILDINGS.NWVL.longitude - (BUILDINGS.NWVL.longitude - BUILDINGS.SWVL.longitude) * ((float) endSpot.getX()) / PIXELS - (BUILDINGS.NWVL.longitude - BUILDINGS.NEVL.longitude) * ((float) endSpot.getY()) / PIXELS
                                ));
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

    //createPolyline will take a list of LatLng and generate a polyline between those points
    //in the order of the input list
    /*
    public void createPolyLine(Destination dest, int[][] coordinates) {

        List<LatLng> points = new ArrayList<LatLng>();

        //converting the provided coordinates too their latitude
        // and longitude and then adding them to an array
        //
        //We'll have to think about a template method
        switch (dest.getmBuilding()) {
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

     */
}
