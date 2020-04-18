package com.conupods.IndoorMaps.ConcreteBuildings;


import com.conupods.OutdoorMaps.BuildingDataMap;
import com.conupods.OutdoorMaps.Models.Building.Building;
import com.conupods.OutdoorMaps.Models.Building.Campus;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;
import java.util.Map;

public class CCBuilding extends Building {

    private static BuildingDataMap mDataInstance = BuildingDataMap.getInstance();
    private static Map<LatLng, Building> mDataMapHash = mDataInstance.getDataMap();
    ;
    private static Building instance;

    public static final int MAX_NUMBER_OF_FLOORS = 1;

    private CCBuilding(Building building) {
        this(building.getCode(), building.getName(), building.getLongName(), building.getAddress(),
                building.getLatLng(), building.getOverlayLatLng(), building.getClassRooms(), building.getCampus());
    }

    private CCBuilding(String code, String name, String longName, String address, LatLng latLng, LatLng overlayLatLng, List<String> classRooms, Campus campus) {
        super(classRooms, latLng, name, campus, longName, address, code, overlayLatLng);
        mFloorMetaDataGrid = new String[MAX_NUMBER_OF_FLOORS][][];
        mTraversalBinaryGrid = new boolean[MAX_NUMBER_OF_FLOORS][][];
        initializeGridsByFloor(0, "data/metadata/1-CC", "data/BooleanArray/CC1");
        initializeClassroomsAndMovementsLocationsFromMetadata("CC ");
    }

    public static Building getInstance() {
        if (instance == null) {
            Building temp = mDataMapHash.get(new LatLng(45.458204, -73.6403));
            instance = new CCBuilding(temp);
            mDataMapHash.replace(new LatLng(45.458204, -73.6403), temp, instance);
        }
        return instance;
    }

    public static int getBearing() { return 29; }

    public static float getWidth() { return 94f; }

    public static float getHeight() { return 32f; }
}