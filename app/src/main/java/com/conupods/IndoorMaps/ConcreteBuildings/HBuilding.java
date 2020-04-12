package com.conupods.IndoorMaps.ConcreteBuildings;

import com.conupods.OutdoorMaps.BuildingDataMap;
import com.conupods.OutdoorMaps.Models.Building.Building;
import com.conupods.OutdoorMaps.Models.Building.Campus;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;
import java.util.Map;

public class HBuilding extends Building {

    private static BuildingDataMap mDataInstance = BuildingDataMap.getInstance();
    private static Map<LatLng, Building> mDataMapHash = mDataInstance.getDataMap();
    private static Building instance;
    public static final int MAX_NUMBER_OF_FLOORS = 4;


    private HBuilding(Building building)
    {
        this(building.getCode(), building.getName(), building.getLongName(), building.getAddress(),
                building.getLatLng(), building.getOverlayLatLng(), building.getClassRooms(), building.getCampus());
    }

    private HBuilding(String code, String name, String longName, String address, LatLng latLng, LatLng overlayLatLng, List<String> classRooms, Campus campus) {
        super(classRooms,latLng, name, campus, longName, address, code, overlayLatLng);
        mFloorMetaDataGrid = new String [MAX_NUMBER_OF_FLOORS][][];
        mTraversalBinaryGrid = new boolean [MAX_NUMBER_OF_FLOORS][][];
        initializeGridsByFloor(0, "data/metadata/1-H", "data/BooleanArray/H1");
        initializeGridsByFloor(1, "data/metadata/2-H", "data/BooleanArray/H2");
        initializeGridsByFloor(2, "data/metadata/8-H", "data/BooleanArray/H8");
        initializeGridsByFloor(3, "data/metadata/9-H", "data/BooleanArray/H9");
    }

    public static synchronized Building getInstance() {
        if (instance == null) {
            Building temp = mDataMapHash.get(new LatLng(45.497092, -73.5788));
            instance = new HBuilding(temp);
            mDataMapHash.replace(new LatLng(45.497092, -73.5788), temp, instance);
        }
        return instance;
    }

    public static int getBearing() {
        return 303;
    }

    public static float getWidth() {
        return 80f;
    }

    public static float getHeight() {
        return 80f;
    }

}