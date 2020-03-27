package com.conupods.OutdoorMaps.Models.Building;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

public class Building extends AbstractCampusLocation {
    private List<Classroom> mClassrooms;
    private Campus mCampus;
    private String mCode;
    private String mLongName;
    private String mAddress;


    public Building() {
    }


    public Building(List<Classroom> classrooms, LatLng coordinates, String name, Campus campus, String longName, String address, String code) {
        super(name, coordinates);

        mCampus = campus;
        mCode = code;
        mLongName = longName;
        mAddress = address;

        if (classrooms == null) {
            mClassrooms = null;
        } else {
            populateBuildingWithCLassrooms(classrooms);
        }


    }

    private void populateBuildingWithCLassrooms(List<Classroom> classrooms) {
        for (Classroom classroom : classrooms) {
            addClassroom(classroom);
        }
    }

    private void addClassroom(Classroom classroom) {
        if (mClassrooms != null) {
            mClassrooms.add(classroom);
        }
    }

    private void removeClassroom(Classroom classroom) {
        if (mClassrooms != null && !mClassrooms.isEmpty() && mClassrooms.contains(classroom)) {
            mClassrooms.remove(classroom);
        }
    }

    public String getCode() {
        return mCode;
    }

    public String getLongName() {
        return mLongName;
    }

    public String getAddress() {
        return mAddress;
    }


    public LatLng getLatLng() {
        return super.getCoordinates();
    }


    @Override
    public String getConcreteParent() {
        return mCampus.toString();
    }


    public String getName() {
        return super.getIdentifier();
    }
}
