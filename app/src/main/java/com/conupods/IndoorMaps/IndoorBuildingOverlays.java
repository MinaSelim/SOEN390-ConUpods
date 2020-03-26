package com.conupods.IndoorMaps;

import android.os.Build;
import android.util.Log;
import android.view.View;

import com.conupods.OutdoorMaps.Building;
import com.conupods.OutdoorMaps.BuildingDataMap;
import com.conupods.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.GroundOverlay;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IndoorBuildingOverlays {

    private GoogleMap mMap;

    public enum Buildings{
        HALL, MB, VL, CC
    }


/*
    private static final LatLng Building_MB = new LatLng(45.49575150228435, -73.5789343714714);
    private static final LatLng Building_LOY_CC = new LatLng(45.45863873466155,  -73.64075660705566);
    private static final LatLng Building_LOY_VL = new LatLng(45.45890400660071,   -73.63919287919998);
    private static final LatLng Building_HALL = new LatLng(45.497273, -73.578955);
    private static final LatLng NEAR_Building_HALL = new LatLng(Building_HALL.latitude + 0.0005, Building_HALL.longitude - 0.0001);
*/

    private List<BitmapDescriptor> mImages = new ArrayList<BitmapDescriptor>();
    private GroundOverlay mHALLOverlay;
    //private List<GroundOverlay> Overlays = new ArrayList<GroundOverlay>();
    private GroundOverlay mMBOverlay;
    private GroundOverlay mLOYCCOverlay;
    private GroundOverlay mLOYVLOverlay;
    private View mLevelButtons;
    private View floorButtonsHall;
    private View floorButtonsMB;
    private View floorButtonsLOY_VL;




    public IndoorBuildingOverlays(View LevelButtons, GoogleMap map) {
        mMap = map;
        mLevelButtons = LevelButtons;
        mMap.setIndoorEnabled(false);

        mImages.clear();

        //index = 0 is first floor of Hall
        mImages.add(BitmapDescriptorFactory.fromResource(R.drawable.h1));
        mImages.add(BitmapDescriptorFactory.fromResource(R.drawable.h2));
        mImages.add(BitmapDescriptorFactory.fromResource(R.drawable.h8));
        mImages.add(BitmapDescriptorFactory.fromResource(R.drawable.h9));

        //index = 4 is first floor of MB
        mImages.add(BitmapDescriptorFactory.fromResource(R.drawable.mb1));
        mImages.add(BitmapDescriptorFactory.fromResource(R.drawable.mbs2));

        //index = 6 is first floor of LOY_CC
        mImages.add(BitmapDescriptorFactory.fromResource(R.drawable.cc1));

        //index = 7 is first floor of LOY_VL
        mImages.add(BitmapDescriptorFactory.fromResource((R.drawable.loy_vl1)));
        mImages.add(BitmapDescriptorFactory.fromResource(R.drawable.loy_vl2));


        floorButtonsMB =  mLevelButtons.findViewById(R.id.floorButtonsMB);
        floorButtonsHall = mLevelButtons.findViewById(R.id.floorButtonsHall);
        floorButtonsLOY_VL = mLevelButtons.findViewById(R.id.floorButtonsLOYVL);
    }

    public void hideLevelButton() {
        floorButtonsMB.setVisibility(View.INVISIBLE);
        floorButtonsHall.setVisibility(View.INVISIBLE);
        floorButtonsLOY_VL.setVisibility(View.INVISIBLE);
    }

    public void showFloorButtons(Buildings building) {
        hidePOIs(1);
        switch (building) {
            case HALL:
                floorButtonsHall.setVisibility(View.VISIBLE);
                break;
            case MB:
                floorButtonsMB.setVisibility(View.VISIBLE);
                break;
            case VL:
                floorButtonsLOY_VL.setVisibility(View.VISIBLE);
                break;
        }
    }

    //Might be a better way to hidePOIs?
    private void hidePOIs(int i) {
        MapStyleOptions style;
        switch (i) {
            case 1:
                style = new MapStyleOptions("[" +
                        "  {" +
                        "    \"featureType\":\"poi.business\"," +
                        "    \"elementType\":\"all\"," +
                        "    \"stylers\":[" +
                        "      {" +
                        "        \"visibility\":\"off\"" +
                        "      }" +
                        "    ]" +
                        "  }," +
                        "  {" +
                        "    \"featureType\":\"transit\"," +
                        "    \"elementType\":\"all\"," +
                        "    \"stylers\":[" +
                        "      {" +
                        "        \"visibility\":\"off\"" +
                        "      }" +
                        "    ]" +
                        "  }" +
                        "]");
                break;
            case 2:
                style = new MapStyleOptions("[" +
                        "  {" +
                        "    \"featureType\":\"poi.business\"," +
                        "    \"elementType\":\"all\"," +
                        "    \"stylers\":[" +
                        "      {" +
                        "        \"visibility\":\"on\"" +
                        "      }" +
                        "    ]" +
                        "  }," +
                        "  {" +
                        "    \"featureType\":\"transit\"," +
                        "    \"elementType\":\"all\"," +
                        "    \"stylers\":[" +
                        "      {" +
                        "        \"visibility\":\"on\"" +
                        "      }" +
                        "    ]" +
                        "  }" +
                        "]");
                break;
            default:
                return;
        }
        mMap.setMapStyle(style);
    }

    public void displayOverlay(Buildings building){
        switch(building) {

            case HALL:
                initializeOverlay(mHALLOverlay,0, 124, HBuilding.getOverlayLatLng(), 0, 1, 80f,80f);
                break;
            case MB:
                initializeOverlay(mMBOverlay, 4, 130, MBBuilding.getOverlayLatLng(), 0, 1, 70f, 70f);
                break;
            case VL:
                initializeOverlay(mLOYVLOverlay,7,30, VLBuilding.getOverlayLatLng(),0,1,83f,76f);
                break;
            case CC:
                initializeOverlay(mLOYCCOverlay,6,29, CCBuilding.getOverlayLatLng(),0,0,94f,32f);
                break;
        }
    }


    private void initializeOverlay(GroundOverlay overlay, int index, int bearing,
                                   LatLng location, int anchor1, int anchor2, float width, float height) {

        if (overlay == null) {

            GroundOverlayOptions overlayOptions = new GroundOverlayOptions()
                    .image(mImages.get(index)).anchor(anchor1, anchor2)
                    .position(location, width, height)
                    .bearing(bearing);

            createOverlay(overlayOptions);
        } else {
            overlay.setVisible(true);
        }
    }

    private void createOverlay(GroundOverlayOptions overlayOptions){

        if(overlayOptions.getLocation().equals(MBBuilding.getOverlayLatLng())){
            mMBOverlay = mMap.addGroundOverlay(overlayOptions);
        }
        else if(overlayOptions.getLocation().equals(HBuilding.getOverlayLatLng())) {
            mHALLOverlay = mMap.addGroundOverlay(overlayOptions);
        }
        else if (overlayOptions.getLocation().equals(CCBuilding.getOverlayLatLng())) {
            mLOYCCOverlay = mMap.addGroundOverlay(overlayOptions);
        }else if (overlayOptions.getLocation().equals(VLBuilding.getOverlayLatLng())){
            mLOYVLOverlay = mMap.addGroundOverlay(overlayOptions);
        }
    }


    public void changeOverlay(int index, String building) {
        hidePOIs(1);

        //TODO: change to enums
        if(building.equals("MB")) {
            mMBOverlay.setImage(mImages.get(index));
        }
        if(building.equals("HALL")) {
            mHALLOverlay.setImage(mImages.get(index));
        }
        if(building.equals("VL")){
            mLOYVLOverlay.setImage(mImages.get(index));
        }
    }

    public void removeOverlay() {
        hidePOIs(2);

        if(mHALLOverlay!=null) {
            mHALLOverlay.setVisible(false);
        }
        if(mMBOverlay!=null) {
            mMBOverlay.setVisible(false);
        }
        if(mLOYCCOverlay!=null) {
            mLOYCCOverlay.setVisible(false);
        }
        if(mLOYVLOverlay!=null) {
            mLOYVLOverlay.setVisible(false);
        }
    }
}