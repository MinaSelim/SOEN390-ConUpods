package com.conupods.OutdoorMaps.View;


import com.google.android.gms.maps.model.LatLng;

public class Building {
    private String campus;
    private String code;
    private String name;
    private String longName;
    private String address;
    private LatLng latLng;

    public Building(String campus, String code, String name, String longName, String address, LatLng latLng) {
        this.campus = campus;
        this.code = code;
        this.name = name;
        this.longName = longName;
        this.address = address;
        this.latLng = latLng;
    }

    public String getCampus() {
        return campus;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getLongName() {
        return longName;
    }

    public String getAddress() {
        return address;
    }

    public LatLng getLatLng() {
        return latLng;
    }

}
