package com.conupods.OutdoorMaps.Models.Buildings;

import com.conupods.OutdoorMaps.Models.Buildings.AbstractCampusLocation;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

public class Building extends AbstractCampusLocation {
    private List<Classroom> mClassrooms;


    public Building() {}


    public Building(List<Classroom> classrooms, LatLng coordinates, String name) {
        super(name, coordinates);


        if(classrooms == null) {
            mClassrooms = null;
        }
        else{
            populateBuildingWithCLassrooms(classrooms);
        }


    }

    private void populateBuildingWithCLassrooms(List<Classroom> classrooms) {
        for(Classroom classroom: classrooms){
            addClassroom(classroom);
        }
    }

    private void addClassroom(Classroom classroom) {
        if(mClassrooms != null) {
            mClassrooms.add(classroom);
        }
    }

    private void removeClassroom(Classroom classroom) {
        if(mClassrooms != null && !mClassrooms.isEmpty() && mClassrooms.contains(classroom)) {
            mClassrooms.remove(classroom);
        }
    }




}
