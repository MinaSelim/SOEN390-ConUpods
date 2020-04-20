/**
 * @author: Mina Selim
 */
package com.conupods.IndoorMaps.View;

import android.content.Context;
import android.content.SharedPreferences;

import com.conupods.App;
import com.conupods.IndoorMaps.ConcreteBuildings.CCBuilding;
import com.conupods.IndoorMaps.ConcreteBuildings.HBuilding;
import com.conupods.IndoorMaps.ConcreteBuildings.MBBuilding;
import com.conupods.IndoorMaps.ConcreteBuildings.VLBuilding;
import com.conupods.IndoorMaps.IndoorCoordinates;
import com.conupods.OutdoorMaps.Models.Building.Building;
import com.conupods.OutdoorMaps.Models.Building.Floor;
import com.conupods.R;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import astar.AStar;
import astar.Spot;

public class IndoorPath {

    public static final String DEFAULT_BUILDING_EXIT = "exit";
    public List<Building> indoorBuildings;

    /**
     * gets all the indoor building constructor stuff
     */
    public IndoorPath() {
        indoorBuildings = new ArrayList<>();
        indoorBuildings.add(HBuilding.getInstance());
        indoorBuildings.add(MBBuilding.getInstance());
        indoorBuildings.add(CCBuilding.getInstance());
        indoorBuildings.add(VLBuilding.getInstance());
    }

    /**
     *
     * @param point
     * @return proper building index
     */
    public int getBuildingIndex(String point) {

        String[] pointArr = point.split(" ");
        String prefix = pointArr[0];
        int index;

        if (prefix.contains("H")) {
            index = 0;
        } else if (prefix.contains("MB")) {
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

    /**
     * It gives the indoor path(es) based on two indoor points.
     * @param startPoint
     * @param endPoint
     * @return an arraylist containing spots (pathes)
     */
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
            if (startCoordinates.getFloor() == endCoordinates.getFloor()) {
                walks.add(getWalk(startPoint, endPoint, startBuilding));
            } else {
                String floorTransitionPoint = getModeOfMovement(startPoint, endPoint, startBuilding);
                walks.add(getWalk(startPoint, floorTransitionPoint, startBuilding));
                walks.add(getWalk(endPoint, floorTransitionPoint, startBuilding));
            }
        } else {
            walks.addAll(getSpotsFromExitToFloor(startPoint, startBuilding, startCoordinates));
            walks.addAll(getSpotsFromExitToFloor(endPoint, endBuilding, endCoordinates));
        }
        return walks;
    }

    /**
     * It gives the indoor path(es) based on an endpoint. Assumes that the user is outside the building
     * @param endPoint
     * @return an arraylist containing spots (pathes)
     */
    public ArrayList<Spot> getIndoorPath(String endPoint) {
        ArrayList<Spot> walks = new ArrayList<>();
        int endBuildingIndex = getBuildingIndex(endPoint);

        if (endBuildingIndex == -1) {
            walks.add(new Spot());
            return walks;
        }

        Building endBuilding = indoorBuildings.get(endBuildingIndex);
        IndoorCoordinates endCoordinates = endBuilding.getLocationCoordinates(endPoint);

        return getSpotsFromExitToFloor(endPoint, endBuilding, endCoordinates);
    }

    private ArrayList<Spot> getSpotsFromExitToFloor(String endPoint, Building endBuilding, IndoorCoordinates endCoordinates) {
        ArrayList<Spot> walks = new ArrayList<>();
        if (endCoordinates.getFloor() == 0) {
            walks.add(getWalk(DEFAULT_BUILDING_EXIT, endPoint, endBuilding));
        } else {
            String floorTransitionPoint = getModeOfMovement(DEFAULT_BUILDING_EXIT, endPoint, endBuilding);
            walks.add(getWalk(DEFAULT_BUILDING_EXIT, floorTransitionPoint, endBuilding));
            walks.add(getWalk(endPoint, floorTransitionPoint, endBuilding));
        }

        return walks;
    }

    private Spot getWalk(String startPoint, String endPoint, Building building) {
        AStar aStar = new AStar();
        IndoorCoordinates startCoordinates = building.getLocationCoordinates(startPoint);
        int level = startCoordinates.getFloor();
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

    private String getModeOfMovement(String startPoint, String endPoint, Building building) {

        IndoorCoordinates startCoordinates;
        IndoorCoordinates endCoordinates;

        if (startPoint.equals(DEFAULT_BUILDING_EXIT)) {
            startCoordinates = new IndoorCoordinates(0, 0, 0, DEFAULT_BUILDING_EXIT);
        } else {
            startCoordinates = building.getLocationCoordinates(startPoint);
        }

        if (endPoint.equals(DEFAULT_BUILDING_EXIT)) {
            endCoordinates = new IndoorCoordinates(0, 0, 0, DEFAULT_BUILDING_EXIT);
        } else {
            endCoordinates = building.getLocationCoordinates(endPoint);
        }

        Set<String> startFloorMovements = building.getModesOfMovementAvailableOnFloor(startCoordinates.getFloor());
        Set<String> endFloorMovements = building.getModesOfMovementAvailableOnFloor(endCoordinates.getFloor());

        HashSet<String> floorIntersectionSet = new HashSet<String>(startFloorMovements);
        floorIntersectionSet.retainAll(endFloorMovements);

        String filterToRemove = startCoordinates.getFloor() > endCoordinates.getFloor() ? "up" : "down";
        floorIntersectionSet.removeIf((mode) -> mode.contains(filterToRemove));
        List<String> modesBasedOnPreference = selectModeOfMovementBasedOnPreference(floorIntersectionSet);

        //If empty, just use the available one
        if (modesBasedOnPreference.isEmpty()) {
            modesBasedOnPreference = new ArrayList<>(floorIntersectionSet);
        }

        return modesBasedOnPreference.get(0);
    }

    /**
     *Finds movements based on the preference of the user.
     * @param modeOfMovement
     * @return Movements
     */
    public List<String> selectModeOfMovementBasedOnPreference(Set<String> modeOfMovement) {
        ArrayList<String> modes = new ArrayList<String>();
        List<String> preferences = getPreferences();
        for (String preferredMode : preferences) {
            for (String availableMode : modeOfMovement) {
                if (availableMode.startsWith(preferredMode)) {
                    modes.add(availableMode);
                }
            }
        }

        return modes;
    }

    private List<String> getPreferences() {
        SharedPreferences preferences = App.getApplication().getSharedPreferences("Preferences", Context.MODE_PRIVATE);
        ArrayList<String> modes = new ArrayList<String>();

        if (preferences.getBoolean(String.valueOf(R.id.escalators), false)) {
            modes.add("escalator");
        }

        if (preferences.getBoolean(String.valueOf(R.id.stairs), false)) {
            modes.add("stair");
        }

        if (preferences.getBoolean(String.valueOf(R.id.elevators), false)) {
            modes.add("elevator");
        }

        return modes;
    }
}