package com.conupods.OutdoorMaps;

import com.google.android.gms.maps.model.LatLng;

/**
 * A entity class for the building object which holds building metadata.
 */
public class Building {
    private String mCampus;
    private String mCode;
    private String mName;
    private String mLongName;
    private String mAddress;
    private LatLng mLatLng;

    public Building(String campus, String code, String name, String longName, String address, LatLng latLng) {
        this.mCampus = campus;
        this.mCode = code;
        this.mName = name;
        this.mLongName = longName;
        this.mAddress = address;
        this.mLatLng = latLng;
    }

    public String getCampus() {
        return mCampus;
    }

    public String getCode() {
        return mCode;
    }

    public String getName() {
        return mName;
    }

    public String getLongName() {
        return mLongName;
    }

    public String getAddress() {
        return mAddress;
    }

    public LatLng getLatLng() {
        return mLatLng;
    }

}
