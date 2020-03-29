package com.conupods.OutdoorMaps.Models.Building;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

public class Building extends AbstractCampusLocation {
    private List<String> mClassrooms;
    private Campus mCampus;
    private String mCode;
    private String mLongName;
    private String mAddress;
    private LatLng mOverlayLatLng;


    public Building(List<String> classrooms, LatLng coordinates, String name, Campus campus, String longName, String address, String code, LatLng overlayLatLng) {
        super(name, coordinates, longName);

        mCampus = campus;
        mCode = code;
        mLongName = longName;
        mAddress = address;
        mOverlayLatLng = overlayLatLng;

        if (classrooms == null) {
            mClassrooms = null;
        } else {
            mClassrooms = classrooms;
        }


    }

    private void removeClassroom(Classroom classroom) {
        if (mClassrooms != null && !mClassrooms.isEmpty() && mClassrooms.contains(classroom.toString())) {
            mClassrooms.remove(classroom.toString());
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


    public List<String> getClassRooms() {
        return mClassrooms;
    }

    @Override
    public String getConcreteParent() {
        return mCampus.toString();
    }


    public LatLng getOverlayLatLng() {
        return mOverlayLatLng;
    }


    public String getName() {
        return super.getIdentifier();
    }
}
