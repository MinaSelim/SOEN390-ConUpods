package com.conupods.IndoorMaps.ConcreteBuildings;

import com.conupods.OutdoorMaps.BuildingDataMap;
import com.conupods.OutdoorMaps.Models.Building.Building;
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
                building.getLatLng(), building.getOverlayLatLng(), building.getClassRooms());
    }

    private HBuilding(String code, String name, String longName, String address, LatLng latLng, LatLng overlayLatLng, List<String> classRooms) {
        super(classRooms,latLng, name, null, longName, address, code, overlayLatLng);
        mFloorMetaDataGrid = new String [MAX_NUMBER_OF_FLOORS][][];
        mTraversalBinaryGrid = new boolean [MAX_NUMBER_OF_FLOORS][][];
        initializeGridsByFloor(0, "json/DONE/1-H", "json/BooleanArray/H1");
        initializeGridsByFloor(1, "json/DONE/2-H", "json/BooleanArray/H2");
        initializeGridsByFloor(2, "json/DONE/8-H", "json/BooleanArray/H8");
        initializeGridsByFloor(3, "json/DONE/9-H", "json/BooleanArray/H9");
    }

    public static synchronized Building getInstance() {
        if (instance == null) {
            Building temp = mDataMapHash.get(new LatLng(45.497092, -73.5788));
            instance = new HBuilding(temp);
            mDataMapHash.replace(new LatLng(45.497092, -73.5788), temp, instance);
        }
        return instance;
    }
}