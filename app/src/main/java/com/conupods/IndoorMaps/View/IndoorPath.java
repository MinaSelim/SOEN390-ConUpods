package com.conupods.IndoorMaps.View;

import com.conupods.IndoorMaps.ConcreteBuildings.CCBuilding;
import com.conupods.IndoorMaps.ConcreteBuildings.HBuilding;
import com.conupods.IndoorMaps.ConcreteBuildings.MBBuilding;
import com.conupods.IndoorMaps.ConcreteBuildings.VLBuilding;
import com.conupods.IndoorMaps.IndoorCoordinates;
import com.conupods.OutdoorMaps.Models.Building.Building;
import com.conupods.OutdoorMaps.Models.Building.Floor;

import java.util.ArrayList;
import java.util.List;

import astar.AStar;
import astar.Spot;

public class IndoorPath {

    public static final String DEFAULT_BUILDING_EXIT = "exit";
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

    public ArrayList<Spot> getIndoorPath(String startPoint, String endPoint) {


       ArrayList<Spot> walks = new ArrayList<>();

        int startBuildingIndex = getBuildingIndex(startPoint);
        int endBuildingIndex = getBuildingIndex(endPoint);

        if (startBuildingIndex == -1 || endBuildingIndex == -1) {
            walks.add(new Spot());
            return walks;
        }

        // Building objects created
        Building startBuilding = indoorBuildings.get(startBuildingIndex);
        Building endBuilding = indoorBuildings.get(endBuildingIndex);

        IndoorCoordinates startCoordinates = startBuilding.getLocationCoordinates(startPoint);
        IndoorCoordinates endCoordinates = endBuilding.getLocationCoordinates(endPoint);

        if (startBuildingIndex == endBuildingIndex) {
            if(startCoordinates.getFloor() == endCoordinates.getFloor()){

                walks.add(getWalk(startPoint, endPoint, startBuilding));

            } else {
                walks.add(getWalk(startPoint,  getModeOfMovement(), startBuilding));
                walks.add(getWalk( getModeOfMovement(), endPoint, startBuilding));
            }
        } else {
            if(startCoordinates.getFloor() == 0) {
                walks.add(getWalk(startPoint, DEFAULT_BUILDING_EXIT, startBuilding));
            } else {
                walks.add(getWalk(startPoint,  getModeOfMovement(), startBuilding));
                walks.add(getWalk( getModeOfMovement(), DEFAULT_BUILDING_EXIT, startBuilding));
            }

            if(endCoordinates.getFloor() == 0)
            {
                walks.add(getWalk(DEFAULT_BUILDING_EXIT, endPoint, endBuilding));
            }
            else
            {
                walks.add(getWalk(DEFAULT_BUILDING_EXIT,  getModeOfMovement(), endBuilding));
                walks.add(getWalk(getModeOfMovement(), endPoint, endBuilding));
            }
        }


        // if index is -1 then building isn't recognized



        return walks;

        //TODO: USE THE CODE ABOVE TO MAKE THE CALLS INSTEAD OF THE CODE BELOW

    }

    private Spot getWalk(String startPoint, String endPoint, Building building) {
        AStar aStar = new AStar();
        IndoorCoordinates startCoordinates =  building.getLocationCoordinates(startPoint);
        int level =  startCoordinates.getFloor();
        boolean[][] binaryGrid = building.getTraversalBinaryGridFromFloor(level);
        String[][] metadataGrid = building.getFloorMetaDataGrid(level);

        Floor floor = new Floor(level, metadataGrid, binaryGrid);

        floor.burrowRoom(startPoint);
        floor.burrowRoom(endPoint);

        int[] startCoords = floor.getCenterCoords(startPoint);
        int[] endCoords = floor.getCenterCoords(endPoint);

        aStar.initializeSpotGrid(floor.getBinaryGrid());
        aStar.linkHorizontalNeighbors();
        Spot walk = aStar.runAlgorithm(startCoords, endCoords);
        walk.setBuilding(building.getCode());
        walk.setFloor(level);
        return walk;
    }

    public String getModeOfMovement() {
        return "escalator-up";
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
