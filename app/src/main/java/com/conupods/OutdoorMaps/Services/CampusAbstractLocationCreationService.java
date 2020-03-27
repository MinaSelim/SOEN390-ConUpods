package com.conupods.OutdoorMaps.Services;

import com.conupods.OutdoorMaps.Models.Building.AbstractCampusLocation;
import com.conupods.OutdoorMaps.Models.Building.Building;
import com.conupods.OutdoorMaps.Models.Building.Campus;
import com.conupods.OutdoorMaps.Models.Building.Classroom;
import com.conupods.OutdoorMaps.View.SearchView.AbstractCampusLocationAdapter;
import com.google.android.gms.maps.model.LatLng;

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

        Classroom classroom1 = new Classroom("H 107", new LatLng(73.5790, 45.4973), new Building(null, new LatLng(73.5790, 45.4973), "Hall", new Campus("SGW", new LatLng(73.5790, 45.4973)), , , ));
        mCampusLocations.add(classroom1);

        Classroom classroom2 = new Classroom("H 109", new LatLng(73.5790, 45.4973), new Building(null, new LatLng(73.5790, 45.4973), "Hall", new Campus("SGW", new LatLng(73.5790, 45.4973)), , , ));
        mCampusLocations.add(classroom2);

        Classroom classroom3 = new Classroom("H 113", new LatLng(73.5790, 45.4973), new Building(null, new LatLng(73.5790, 45.4973), "Hall", new Campus("SGW", new LatLng(73.5790, 45.4973)), , , ));
        mCampusLocations.add(classroom3);

        Classroom classroom4 = new Classroom("H 130", new LatLng(73.5790, 45.4973), new Building(null, new LatLng(73.5790, 45.4973), "Hall", new Campus("SGW", new LatLng(73.5790, 45.4973)), , , ));
        mCampusLocations.add(classroom4);

        Classroom classroom5 = new Classroom("H 806", new LatLng(73.5790, 45.4973), new Building(null, new LatLng(73.5790, 45.4973), "Hall", new Campus("SGW", new LatLng(73.5790, 45.4973)), , , ));
        mCampusLocations.add(classroom5);

        Classroom classroom6 = new Classroom("H 907", new LatLng(73.5790, 45.4973), new Building(null, new LatLng(73.5790, 45.4973), "Hall", new Campus("SGW", new LatLng(73.5790, 45.4973)), , , ));
        mCampusLocations.add(classroom6);

        Classroom classroom7 = new Classroom("H 1030", new LatLng(73.5790, 45.4973), new Building(null, new LatLng(73.5790, 45.4973), "Hall", new Campus("SGW", new LatLng(73.5790, 45.4973)), , , ));
        mCampusLocations.add(classroom7);

        Classroom classroom8 = new Classroom("H 507", new LatLng(73.5790, 45.4973), new Building(null, new LatLng(73.5790, 45.4973), "Hall", new Campus("SGW", new LatLng(73.5790, 45.4973)), , , ));
        mCampusLocations.add(classroom8);

        Classroom classroom9 = new Classroom("H 107", new LatLng(73.5790, 45.4973), new Building(null, new LatLng(73.5790, 45.4973), "Hall", new Campus("SGW", new LatLng(73.5790, 45.4973)), , , ));
        mCampusLocations.add(classroom9);

        Building HallBuilding = new Building(null, new LatLng(73.5790, 45.4973), "Hall", new Campus("SGW", new LatLng(73.5790, 45.4973)), , , );
        mCampusLocations.add(HallBuilding);

        Campus LOY = new Campus("LOY", new LatLng(83.5790, 45.4973));
        mCampusLocations.add(LOY);

        mCampusLocationAdapter.notifyDataSetChanged();
    }

}
