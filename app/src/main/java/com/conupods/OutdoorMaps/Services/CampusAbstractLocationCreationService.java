package com.conupods.OutdoorMaps.Services;

import android.util.Log;

import com.conupods.OutdoorMaps.BuildingDataMap;
import com.conupods.OutdoorMaps.Models.Building.AbstractCampusLocation;
import com.conupods.OutdoorMaps.Models.Building.Building;
import com.conupods.OutdoorMaps.Models.Building.Classroom;
import com.conupods.OutdoorMaps.View.SearchView.AbstractCampusLocationAdapter;

import java.util.List;

public class CampusAbstractLocationCreationService {

    private List<AbstractCampusLocation> mCampusLocations;
    private AbstractCampusLocationAdapter mCampusLocationAdapter;
    private final String TAG = "CampusAbstractLocationCreationService";

    public CampusAbstractLocationCreationService(List<AbstractCampusLocation> locations, AbstractCampusLocationAdapter campusLocationAdapter) {
        mCampusLocations = locations;
        mCampusLocationAdapter = campusLocationAdapter;
    }

    public AbstractCampusLocationAdapter getCampusLocationAdapter() {
        return mCampusLocationAdapter;
    }

    public void prepareCampusLocationsForSearch() {

        BuildingDataMap buildingDataMap = BuildingDataMap.getInstance();
        List<Classroom> allKnownClassrooms = buildingDataMap.getClassroomDataDataList();
        List<Building> allKnownBuildings = buildingDataMap.getmBuildingsDataList();


        for (AbstractCampusLocation building: allKnownBuildings) {
            Log.d(TAG, "KnownBuildings: "+ building.getIdentifier());
            mCampusLocations.add(building);
        }

        for (AbstractCampusLocation clasroom: allKnownClassrooms) {
            Log.d(TAG, "KnownClass: "+ clasroom.getIdentifier());
            mCampusLocations.add(clasroom);
        }

        mCampusLocationAdapter.notifyDataSetChanged();
    }

}
