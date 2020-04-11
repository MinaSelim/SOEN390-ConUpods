package com.conupods.IndoorMaps.View;

import com.conupods.IndoorMaps.ConcreteBuildings.CCBuilding;
import com.conupods.IndoorMaps.ConcreteBuildings.HBuilding;
import com.conupods.IndoorMaps.ConcreteBuildings.MBBuilding;
import com.conupods.IndoorMaps.ConcreteBuildings.VLBuilding;
import com.conupods.IndoorMaps.IndoorCoordinates;
import com.conupods.OutdoorMaps.Models.Building.Building;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import astar.AStar;
import astar.Destination;
import astar.Edges;
import astar.Spot;

public class IndoorPath {

    public List<Building> indoorBuildings;
    /*
    This is a Mock scenario, used until further implementation gets completed
     */

    public IndoorPath() {
        indoorBuildings = new ArrayList<>();
        indoorBuildings.add(HBuilding.getInstance());
        indoorBuildings.add(MBBuilding.getInstance());
        indoorBuildings.add(CCBuilding.getInstance());
        indoorBuildings.add(VLBuilding.getInstance());
    }


    public Spot getIndoorPath(String startPoint, String endPoint) {

        Building startBuilding = indoorBuildings.get(0);
        Building endBuilding = indoorBuildings.get(0);

        IndoorCoordinates startCoordinates = null;
        IndoorCoordinates endCoordinates = null;

        for(int i = 0; i<indoorBuildings.size(); i++) {
            startCoordinates = indoorBuildings.get(i).getLocationCoordinates(startPoint);
            if(startCoordinates != null) {
                startBuilding = indoorBuildings.get(i);
                break;
            }
        }

        for(int i = 0; i<indoorBuildings.size(); i++) {
            endCoordinates = indoorBuildings.get(i).getLocationCoordinates(endPoint);
            if(endCoordinates != null) {
                endBuilding = indoorBuildings.get(i);
                break;
            }
        }

        boolean[][] startingGrid = startBuilding.getTraversalBinaryGridFromFloor(startCoordinates.mFloor);
        boolean[][] endingGrid = endBuilding.getTraversalBinaryGridFromFloor(endCoordinates.mFloor);

        //TODO: USE THE CODE ABOVE TO MAKE THE CALLS INSTEAD OF THE CODE BELOW

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

        boolean[][] binGrid = startingGrid;
        aStar.createSpotGrid(binGrid, startEnd);
        aStar.linkHorizontalNeighbors();
        Spot walk = aStar.runAlgorithm(x1, y1, x2, y2);

        walk.setBuilding(end.getmBuilding());

        return walk;

    }


    /*
    create getIndoorPath() with one endPoint and dynamic startPoint based on endPoint
    pseudo code

    getIndoorPath(String endPoint) {
        builging = endPoint.getBuilding();
        start = entry of building
        runAlgorithm

     */

    public Spot getIndoorPath(String endPoint) {

        return new Spot(0,0,false);

    }



}
