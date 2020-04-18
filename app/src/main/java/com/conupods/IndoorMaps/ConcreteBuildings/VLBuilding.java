package com.conupods.IndoorMaps.ConcreteBuildings;


import com.conupods.OutdoorMaps.Models.Building.Building;
import com.conupods.OutdoorMaps.BuildingDataMap;
import com.conupods.OutdoorMaps.Models.Building.Campus;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;
import java.util.Map;

public class VLBuilding extends Building {

    private static BuildingDataMap mDataInstance = BuildingDataMap.getInstance();
    private static Map<LatLng, Building> mDataMapHash = mDataInstance.getDataMap();
    private static Building instance;

    public static final int MAX_NUMBER_OF_FLOORS = 2;

    private VLBuilding(Building building) {
        this(building.getCode(), building.getName(), building.getLongName(), building.getAddress(),
                building.getLatLng(), building.getOverlayLatLng(), building.getClassRooms(), building.getCampus());
    }

    private VLBuilding(String code, String name, String longName, String address, LatLng latLng, LatLng overlayLatLng, List<String> classRooms, Campus campus) {
        super(classRooms, latLng, name, campus, longName, address, code, overlayLatLng);
        mFloorMetaDataGrid = new String[MAX_NUMBER_OF_FLOORS][][];
        mTraversalBinaryGrid = new boolean[MAX_NUMBER_OF_FLOORS][][];
        initializeGridsByFloor(0, "data/metadata/1-VL", "data/BooleanArray/loy_vl1");
        initializeGridsByFloor(1, "data/metadata/2-VL", "data/BooleanArray/loy_vl2");
        initializeClassroomsAndMovementsLocationsFromMetadata("VL ");
    }

    public static Building getInstance() {

        if (instance == null) {
            Building temp = mDataMapHash.get(new LatLng(45.459026, -73.638606));
            instance = new VLBuilding(temp);
            mDataMapHash.replace(new LatLng(45.459026, -73.638606), temp, instance);
        }
        return instance;
    }

    public static int getBearing() { return 29; }

    public static float getWidth() { return 83f; }

    public static float getHeight() { return 76f; }
}