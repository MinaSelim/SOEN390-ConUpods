package com.conupods.OutdoorMaps.Services;

import com.conupods.OutdoorMaps.Models.Buildings.AbstractCampusLocation;
import com.conupods.OutdoorMaps.Models.Buildings.Building;
import com.conupods.OutdoorMaps.Models.Buildings.Campus;
import com.conupods.OutdoorMaps.Models.Buildings.Classroom;
import com.conupods.OutdoorMaps.View.SearchView.AbstractCampusLocationAdapter;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

public class CampusLocationCreationService {

   private List<AbstractCampusLocation> mCampusLocations;
   private AbstractCampusLocationAdapter mCampusLocationAdapter;

    public CampusLocationCreationService(List<AbstractCampusLocation> locations, AbstractCampusLocationAdapter campusLocationAdapter){
        mCampusLocations = locations;
        mCampusLocationAdapter = campusLocationAdapter;
    }

    public AbstractCampusLocationAdapter getCampusLocationAdapter() {
        return mCampusLocationAdapter;
    }

    public void prepareCampusLocationsForSearch() {

        //TODO CSV OR JSON PARSER LOGIC TO GO IN HERE INSTEAD OF HARDCODE

        Classroom classroom1 = new Classroom("H_107", new LatLng(73.5790,45.4973), new Building(null, new LatLng(73.5790,45.4973), "Hall", new Campus("SGW",  new LatLng(73.5790,45.4973))));
        mCampusLocations.add(classroom1);

        Classroom classroom2 =new Classroom("H_109", new LatLng(73.5790,45.4973), new Building(null, new LatLng(73.5790,45.4973), "Hall", new Campus("SGW",  new LatLng(73.5790,45.4973))));

        mCampusLocations.add(classroom2);


        Classroom classroom3 =new Classroom("H_113", new LatLng(73.5790,45.4973), new Building(null, new LatLng(73.5790,45.4973), "Hall", new Campus("SGW",  new LatLng(73.5790,45.4973))));

        mCampusLocations.add(classroom3);


        Classroom classroom4 = new Classroom("H_130", new LatLng(73.5790,45.4973), new Building(null, new LatLng(73.5790,45.4973), "Hall", new Campus("SGW",  new LatLng(73.5790,45.4973))));

        mCampusLocations.add(classroom4);


        Classroom classroom5 =new Classroom("H_806", new LatLng(73.5790,45.4973), new Building(null, new LatLng(73.5790,45.4973), "Hall", new Campus("SGW",  new LatLng(73.5790,45.4973))));

        mCampusLocations.add(classroom5);


        Classroom classroom6 = new Classroom("H_907", new LatLng(73.5790,45.4973), new Building(null, new LatLng(73.5790,45.4973), "Hall", new Campus("SGW",  new LatLng(73.5790,45.4973))));

        mCampusLocations.add(classroom6);


        Classroom classroom7 = new Classroom("H_1030", new LatLng(73.5790,45.4973), new Building(null, new LatLng(73.5790,45.4973), "Hall", new Campus("SGW",  new LatLng(73.5790,45.4973))));

        mCampusLocations.add(classroom7);


        Classroom classroom8 = new Classroom("H_507", new LatLng(73.5790,45.4973), new Building(null, new LatLng(73.5790,45.4973), "Hall", new Campus("SGW",  new LatLng(73.5790,45.4973))));

        mCampusLocations.add(classroom8);


        Classroom classroom9 = new Classroom("H_107", new LatLng(73.5790,45.4973), new Building(null, new LatLng(73.5790,45.4973), "Hall", new Campus("SGW",  new LatLng(73.5790,45.4973))));

        mCampusLocations.add(classroom9);


        mCampusLocationAdapter.notifyDataSetChanged();
    }

}
