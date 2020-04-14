package com.conupods.IndoorMaps.ConcreteBuildings;


import com.conupods.OutdoorMaps.Models.Building.Building;
import com.conupods.OutdoorMaps.BuildingDataMap;
import com.conupods.OutdoorMaps.Models.Building.Campus;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;
import java.util.Map;

public class MBBuilding extends Building {


    private MBBuilding(Building building)
    {
        this(building.getCode(), building.getName(), building.getLongName(), building.getAddress(),
                building.getLatLng(), building.getOverlayLatLng(), building.getClassRooms(), building.getCampus());
    }

    public static final int MAX_NUMBER_OF_FLOORS = 2;
    private static BuildingDataMap mDataInstance = BuildingDataMap.getInstance();
    private static Map<LatLng, Building> mDataMapHash = mDataInstance.getDataMap();
    private static Building instance;

    private MBBuilding(String code, String name, String longName, String address, LatLng latLng, LatLng overlayLatLng, List<String> classRooms, Campus campus) {
        super(classRooms,latLng, name, campus, longName, address, code, overlayLatLng);
        mFloorMetaDataGrid = new String [MAX_NUMBER_OF_FLOORS][][];
        mTraversalBinaryGrid = new boolean [MAX_NUMBER_OF_FLOORS][][];
        initializeGridsByFloor(0, "data/metadata/1-JMSB", "data/BooleanArray/MB1");
        initializeGridsByFloor(1, "data/metadata/S2-JMSB", "data/BooleanArray/MB2");
        initializeClassroomsFromMetadata("MB ");
    }

    public static Building getInstance() {

        if (instance == null) {
            Building temp = mDataMapHash.get (new LatLng(45.495304, -73.579044));
            instance = new MBBuilding(temp);
            mDataMapHash.replace (new LatLng(45.495304, -73.579044), temp, instance);
        }
        return instance;
    }

    public static int getBearing() {
        return 130;
    }

    public static float getWidth() {
        return 70f;
    }

    public static float getHeight() {
        return 70f;
    }

}
