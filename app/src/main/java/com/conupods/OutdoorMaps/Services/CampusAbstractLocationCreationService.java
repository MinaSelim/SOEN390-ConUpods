package com.conupods.OutdoorMaps.Services;

import com.conupods.IndoorMaps.ConcreteBuildings.CCBuilding;
import com.conupods.IndoorMaps.ConcreteBuildings.HBuilding;
import com.conupods.IndoorMaps.ConcreteBuildings.MBBuilding;
import com.conupods.IndoorMaps.ConcreteBuildings.VLBuilding;
import com.conupods.OutdoorMaps.BuildingDataMap;
import com.conupods.OutdoorMaps.Models.Building.AbstractCampusLocation;
import com.conupods.OutdoorMaps.Models.Building.Building;
import com.conupods.OutdoorMaps.Models.Building.Classroom;
import com.conupods.OutdoorMaps.View.SearchView.AbstractCampusLocationAdapter;

import java.util.ArrayList;
import java.util.List;

public class CampusAbstractLocationCreationService {

    private List<AbstractCampusLocation> mCampusLocations;
    private AbstractCampusLocationAdapter mCampusLocationAdapter;
    private static final String TAG = "CampusAbstractLocationCreationService";

    public CampusAbstractLocationCreationService(List<AbstractCampusLocation> locations, AbstractCampusLocationAdapter campusLocationAdapter) {
        mCampusLocations = locations;
        mCampusLocationAdapter = campusLocationAdapter;
    }

    public AbstractCampusLocationAdapter getCampusLocationAdapter() {
        return mCampusLocationAdapter;
    }

    public void prepareCampusLocationsForSearch() {

        ArrayList<Building> indoorBuildings = new ArrayList<>();
        List<Classroom> allKnownClassrooms = new ArrayList<>();

        indoorBuildings.add(HBuilding.getInstance());
        indoorBuildings.add(MBBuilding.getInstance());
        indoorBuildings.add(CCBuilding.getInstance());
        indoorBuildings.add(VLBuilding.getInstance());

        BuildingDataMap buildingDataMap = BuildingDataMap.getInstance();
        for(Building b : indoorBuildings) {
            List<String> classrooms = b.getClassRooms();
            for(String classroom : classrooms) {
                allKnownClassrooms.add(new Classroom(classroom, b.getLatLng(), b));
            }
        }

        List<Building> allKnownBuildings = buildingDataMap.getmBuildingsDataList();

        for (AbstractCampusLocation building: allKnownBuildings) {
            mCampusLocations.add(building);
        }

        for (AbstractCampusLocation classroom: allKnownClassrooms) {
            mCampusLocations.add(classroom);
        }

        mCampusLocationAdapter.notifyDataSetChanged();
    }

}
