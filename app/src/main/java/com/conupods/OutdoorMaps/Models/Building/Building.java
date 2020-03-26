package com.conupods.OutdoorMaps.Models.Building;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

public class Building extends AbstractCampusLocation {
    private List<Classroom> mClassrooms;
    private Campus mCampus;


    public Building() {}


    public Building(List<Classroom> classrooms, LatLng coordinates, String name, Campus campus) {
        super(name, coordinates);

        mCampus = campus;

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


    @Override
    public String getConcreteParent() {
        return mCampus.toString();
    }




}
