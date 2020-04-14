package com.conupods.IndoorMaps.View;

import com.conupods.IndoorMaps.ConcreteBuildings.CCBuilding;
import com.conupods.IndoorMaps.ConcreteBuildings.HBuilding;
import com.conupods.IndoorMaps.ConcreteBuildings.MBBuilding;
import com.conupods.IndoorMaps.ConcreteBuildings.VLBuilding;
import com.conupods.IndoorMaps.IndoorCoordinates;
import com.conupods.OutdoorMaps.Models.Building.Building;
import com.conupods.OutdoorMaps.Models.Building.Floor;

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

    public IndoorPath() {
        indoorBuildings = new ArrayList<>();
        indoorBuildings.add(HBuilding.getInstance());
        indoorBuildings.add(MBBuilding.getInstance());
        indoorBuildings.add(CCBuilding.getInstance());
        indoorBuildings.add(VLBuilding.getInstance());
    }

    public int getBuildingIndex(String point){

        String[] pointArr = point.split(" ");
        String prefix = pointArr[0];
        int index;

        if(prefix.contains("H")) {
            index = 0;
        } else if (prefix.contains("MS")) {
            index = 1;
        } else if (prefix.contains("CC")) {
            index = 2;
        } else if (prefix.contains("VL")) {
            index = 3;
        } else {
            index = -1;
        }

        return index;

    }

    public Spot getIndoorPath(String startPoint, String endPoint) {

        AStar aStar = new AStar();
        Spot walk = new Spot();

        int startBuildingIndex = getBuildingIndex(startPoint);
        int endBuildingIndex = getBuildingIndex(endPoint);

        // Building objects created
        Building startBuilding = indoorBuildings.get(startBuildingIndex);
        Building endBuilding = indoorBuildings.get(endBuildingIndex);

        IndoorCoordinates startCoordinates = startBuilding.getLocationCoordinates(startPoint);
        IndoorCoordinates endCoordinates = endBuilding.getLocationCoordinates(endPoint);

        if (startBuildingIndex == endBuildingIndex) {

            /**
             * Same building
             * simple path from one room to another
             * can include multi floor or same floor
             */

            if(startCoordinates.getFloor() == endCoordinates.getFloor()){

                /**
                 * Same building Same floor
                 */

                int level =  startCoordinates.getFloor();
                boolean[][] binaryGrid = startBuilding.getTraversalBinaryGridFromFloor(level);
                String[][] metadataGrid = startBuilding.getFloorMetaDataGrid(level);

                Floor floor = new Floor(level, metadataGrid, binaryGrid);

                floor.burrowRoom(startPoint);
                floor.burrowRoom(endPoint);

                int[] startCoords = floor.getCenterCoords(startPoint);
                int[] endCoords = floor.getCenterCoords(endPoint);

                aStar.initializeSpotGrid(floor.getBinaryGrid());
                aStar.linkHorizontalNeighbors();
                walk = aStar.runAlgorithm(startCoords, endCoords);
                walk.setBuilding(startBuilding.getCode());
                walk.setFloor(level);



            } else {

                // Floors aren't same
                boolean[][] startingGrid = startBuilding.getTraversalBinaryGridFromFloor(startCoordinates.getFloor());
                boolean[][] endingGrid = endBuilding.getTraversalBinaryGridFromFloor(endCoordinates.getFloor());

            }




        } else {

            //2 different buildings
            // direct start -> exit of building
            // entry to end building and then to floor

        }


        // if index is -1 then building isn't recognized
        if (startBuildingIndex == -1 || endBuildingIndex == -1) {
            return new Spot();
        }


        return walk;

        //TODO: USE THE CODE ABOVE TO MAKE THE CALLS INSTEAD OF THE CODE BELOW

    }


    /*
    create getIndoorPath() with one endPoint and dynamic startPoint based on endPoint
    pseudo code

    getIndoorPath(String endPoint) {
        builging = endPoint.getBuilding();
        start = entry of building
        runAlgorithm

     */


}
