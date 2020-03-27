package com.conupods.OutdoorMaps.Services;

import com.conupods.OutdoorMaps.BuildingDataMap;
import com.conupods.OutdoorMaps.Models.Building.AbstractCampusLocation;
import com.conupods.OutdoorMaps.Models.Building.Building;
import com.conupods.OutdoorMaps.Models.Building.Classroom;
import com.conupods.OutdoorMaps.View.SearchView.AbstractCampusLocationAdapter;

import java.util.List;

public class CampusAbstractLocationCreationService {

    private List<AbstractCampusLocation> mCampusLocations;
    private AbstractCampusLocationAdapter mCampusLocationAdapter;

    public CampusAbstractLocationCreationService(List<AbstractCampusLocation> locations, AbstractCampusLocationAdapter campusLocationAdapter) {
        mCampusLocations = locations;
        mCampusLocationAdapter = campusLocationAdapter;
    }

    public AbstractCampusLocationAdapter getCampusLocationAdapter() {
        return mCampusLocationAdapter;
    }

    public void prepareCampusLocationsForSearch() {

        //TODO CSV OR JSON PARSER LOGIC TO GO IN HERE INSTEAD OF HARDCODE

        BuildingDataMap buildingDataMap = BuildingDataMap.getInstance();
        List<Classroom> allKnownClassrooms = buildingDataMap.getClassroomDataDataList();
        List<Building> allKnownBuildings = buildingDataMap.getmBuildingsDataList();

        for (AbstractCampusLocation campusLocation: allKnownBuildings) {
            mCampusLocations.add(campusLocation);
        }

        for (AbstractCampusLocation campusLocation: allKnownClassrooms) {
            mCampusLocations.add(campusLocation);
        }

        mCampusLocationAdapter.notifyDataSetChanged();
    }

}
