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


            // check if same floor
            if(startCoordinates.getFloor() == endCoordinates.getFloor()){

                int level =  startCoordinates.getFloor();
                boolean[][] binaryGrid = startBuilding.getTraversalBinaryGridFromFloor(level);
                String[][] metadataGrid = startBuilding.getFloorMetaDataGrid(level);

                Floor floor = new Floor(level, metadataGrid, binaryGrid);


                //create method to burrow the start end points
                floor.burrowRoom(startPoint);
                floor.burrowRoom(endPoint);

                int[] startCoords = floor.getCenterCoords(startPoint);
                int[] endCoords = floor.getCenterCoords(endPoint);

                aStar.initializeSpotGrid(floor.getBinaryGrid());
                aStar.linkHorizontalNeighbors();
                walk = aStar.runAlgorithm(startCoords, endCoords);
                walk.setBuilding(startBuilding.getCode());



            } else {
                // Floors aren't same


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

        boolean[][] startingGrid = startBuilding.getTraversalBinaryGridFromFloor(startCoordinates.getFloor());
        boolean[][] endingGrid = endBuilding.getTraversalBinaryGridFromFloor(endCoordinates.getFloor());

        return walk;

        //TODO: USE THE CODE ABOVE TO MAKE THE CALLS INSTEAD OF THE CODE BELOW


/*
        aStar.mMetadataFilePath = "data/Metadata.json";
        IndoorNavigation indoorNavigation = new IndoorNavigation();
        InputStreamReader in = indoorNavigation.getInputStreamReader(aStar.mMetadataFilePath);

        Destination start = aStar.setDestFromString(startPoint);
        Destination end = aStar.setDestFromString(endPoint);

 */

        /**
         * Takes metadata file with start and end coordinates
         * and burrows squares where the rooms are located
         *
         * start, end -> (room_name, building_code, floor#)
         * in -> json stream used to get the xy coords
         */
        /*
        Edges[] startEnd = aStar.getDictFromJSON(start, end, in);

        try {
            in.close();
        } catch (IOException e) {
             InputStreamReader already closed
        }
         */


        /**
         * gets middle of the boxes
         */
        /*
        int x1 = (startEnd[0].getRight() - startEnd[0].getLeft()) / 2 + startEnd[0].getLeft();
        int y1 = (startEnd[0].getTop() - startEnd[0].getBottom()) / 2 + startEnd[0].getBottom();
        int x2 = (startEnd[1].getRight() - startEnd[1].getLeft()) / 2 + startEnd[1].getLeft();
        int y2 = (startEnd[1].getTop() - startEnd[1].getBottom()) / 2 + startEnd[1].getBottom();

        String buildingFile = indoorNavigation.getBuildingGrid(end);

        grid = startingGrid;


        aStar.createSpotGrid(grid, startEnd);
        aStar.linkHorizontalNeighbors();
        Spot walk = aStar.runAlgorithm(x1, y1, x2, y2);

        walk.setBuilding(end.getmBuilding());

        return walk;

         */

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
