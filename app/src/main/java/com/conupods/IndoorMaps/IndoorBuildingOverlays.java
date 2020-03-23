package com.conupods.IndoorMaps;

import android.util.Log;
import android.view.View;

import com.conupods.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.GroundOverlay;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;

import java.util.ArrayList;
import java.util.List;

public class IndoorBuildingOverlays {

    private GoogleMap mMap;


    private static final LatLng Building_JMSB = new LatLng(45.49575150228435, -73.5789343714714);

    private static final LatLng Building_LOY_CC = new LatLng(45.45863873466155,  -73.64075660705566);
    private static final LatLng Building_LOY_VL = new LatLng(45.45890400660071,   -73.63919287919998);

    private static final LatLng Building_HALL = new LatLng(45.497273, -73.578955);
    private static final LatLng NEAR_Building_HALL = new LatLng(Building_HALL.latitude + 0.0005, Building_HALL.longitude - 0.0001);


    private List<BitmapDescriptor> mImages = new ArrayList<BitmapDescriptor>();
    private GroundOverlay mHALLOverlay;
    private GroundOverlay mJMSBOverlay;
    private GroundOverlay mLOYCCOverlay;
    private GroundOverlay mLOYVLOverlay;
    private View mLevelButtons;
    private View floorButtonsHall;
    private View floorButtonsJmsb;
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

        //index = 4 is first floor of JMSB
        mImages.add(BitmapDescriptorFactory.fromResource(R.drawable.mb1));
        mImages.add(BitmapDescriptorFactory.fromResource(R.drawable.mbs2));

        //index = 6 is first floor of LOY_CC
        mImages.add(BitmapDescriptorFactory.fromResource(R.drawable.cc1));

        //index = 7 is first floor of LOY_VL
        mImages.add(BitmapDescriptorFactory.fromResource((R.drawable.loy_vl1)));
        mImages.add(BitmapDescriptorFactory.fromResource(R.drawable.loy_vl2));


        floorButtonsJmsb =  mLevelButtons.findViewById(R.id.floorButtonsJmsb);
        floorButtonsHall = mLevelButtons.findViewById(R.id.floorButtonsHall);
        floorButtonsLOY_VL = mLevelButtons.findViewById(R.id.floorButtonsLOYVL);
    }

    public void hideLevelButton() {
        floorButtonsJmsb.setVisibility(View.INVISIBLE);
        floorButtonsHall.setVisibility(View.INVISIBLE);
        floorButtonsLOY_VL.setVisibility(View.INVISIBLE);
    }

    public void showButtonsJMSB() {
        floorButtonsJmsb.setVisibility(View.VISIBLE);
    }

    public void showButtonsHALL() {
        floorButtonsHall.setVisibility(View.VISIBLE);
    }

    public void showButtonsLOYVL() {
        floorButtonsLOY_VL.setVisibility(View.VISIBLE);
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
            default:
                return;
        }
        mMap.setMapStyle(style);
    }


    public void displayOverlayJMSB() {
        hidePOIs(1);
        if(mJMSBOverlay==null) {
            mJMSBOverlay = mMap.addGroundOverlay(new GroundOverlayOptions()
                    .image(mImages.get(4)).anchor(0, 1)
                    .position(Building_JMSB, 70f, 70f)
                    .bearing(130));
        }else {
            mJMSBOverlay.setVisible(true);
        }
    }

    public void displayOverlayLOYVL() {
        hidePOIs(1);
        if(mLOYVLOverlay==null) {
            mLOYVLOverlay = mMap.addGroundOverlay(new GroundOverlayOptions()
                    .image(mImages.get(7)).anchor(0, 1)
                    .position(Building_LOY_VL, 83f, 76f)
                    .bearing(30));
        }else {
            mLOYVLOverlay.setVisible(true);
        }
    }

    public void displayOverlayLOYCC() {
        hidePOIs(1);
        if(mLOYCCOverlay==null) {
            mLOYCCOverlay = mMap.addGroundOverlay(new GroundOverlayOptions()
                    .image(mImages.get(6)).anchor(0, 0)
                    .position(Building_LOY_CC, 95)
                    .bearing(29));
        }else {
            mLOYCCOverlay.setVisible(true);
        }
    }

    public void displayOverlayHall() {
        hidePOIs(1);
        if(mHALLOverlay == null) {
            mHALLOverlay = mMap.addGroundOverlay(new GroundOverlayOptions()
                    .image(mImages.get(0)).anchor(0, 1)
                    .position(NEAR_Building_HALL, 80f, 80f)
                    .bearing(124));
        }else {
            mHALLOverlay.setVisible(true);
        }
    }

    public void changeOverlay(int index, String building) {
        hidePOIs(1);

        if(building.equals("JMSB")) {
            mJMSBOverlay.setImage(mImages.get(index));
        }
        if(building.equals("HALL")) {
            mHALLOverlay.setImage(mImages.get(index));
        }
        if(building.equals("VL")){
            mLOYVLOverlay.setImage(mImages.get(index));
        }
    }

    public void removeOverlay() {
        if(mHALLOverlay!=null) {
            mHALLOverlay.setVisible(false);
        }
        if(mJMSBOverlay!=null) {
            mJMSBOverlay.setVisible(false);
        }
        if(mLOYCCOverlay!=null) {
            mLOYCCOverlay.setVisible(false);
        }
        if(mLOYVLOverlay!=null) {
            mLOYVLOverlay.setVisible(false);
        }

    }
}