package com.conupods.IndoorMaps.View;

import android.graphics.Bitmap;

import java.io.IOException;
import java.io.InputStreamReader;

import astar.AStar;
import astar.Destination;
import astar.Edges;
import astar.Spot;

public class IndoorPath {

    /*
    This is a Mock scenario, used until further implementation gets completed
     */


    public Spot getIndoorPath(String startPoint, String endPoint) {

        AStar aStar = new AStar();
        aStar.mMetadataFilePath = "json/Metadata.json";

        IndoorNavigation indoorNavigation = new IndoorNavigation();
        InputStreamReader in = indoorNavigation.getInputStreamReader(aStar.mMetadataFilePath);



        Destination start = aStar.setDestFromString(startPoint);
        Destination end = aStar.setDestFromString(endPoint);

        Edges[] startEnd = aStar.getDictFromJSON(start, end, in);

        try {
            in.close();
        } catch (IOException e) {
            // InputStreamReader already closed
        }


        int x1 = (startEnd[0].getRight() - startEnd[0].getLeft()) / 2 + startEnd[0].getLeft();
        int y1 = (startEnd[0].getTop() - startEnd[0].getBottom()) / 2 + startEnd[0].getBottom();
        int x2 = (startEnd[1].getRight() - startEnd[1].getLeft()) / 2 + startEnd[1].getLeft();
        int y2 = (startEnd[1].getTop() - startEnd[1].getBottom()) / 2 + startEnd[1].getBottom();

        String buildingFile = indoorNavigation.getBuildingGrid(end);

        boolean[][] binGrid = indoorNavigation.createBinaryGrid(buildingFile);
        aStar.createSpotGrid(binGrid, startEnd);
        aStar.linkNeighbors();
        Spot walk = aStar.runAlgorithm(x1, y1, x2, y2);

        walk.setBuilding(end.getmBuilding());

        return walk;

    }


}
