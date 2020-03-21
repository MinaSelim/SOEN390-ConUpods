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

    private static final LatLng Building_HALL = new LatLng(45.497273, -73.578955);
    private static final LatLng NEAR_Building_HALL = new LatLng(Building_HALL.latitude + 0.0005, Building_HALL.longitude - 0.0001);


    private List<BitmapDescriptor> mImages = new ArrayList<BitmapDescriptor>();
    private List<BitmapDescriptor> mImagesJMSB = new ArrayList<BitmapDescriptor>();
    private GroundOverlay mHALLOverlay;
    private GroundOverlay mJMSBOverlay;
    private View mLevelButtons;
    private View floorButtonsHall;
    private View floorButtonsJmsb;


    public IndoorBuildingOverlays(View LevelButtons, GoogleMap map) {
        mMap = map;
        mLevelButtons = LevelButtons;
        mMap.setIndoorEnabled(false);

        mImages.clear();
        //mImagesJMSB.clear();

        mImages.add(BitmapDescriptorFactory.fromResource(R.drawable.h1));
        mImages.add(BitmapDescriptorFactory.fromResource(R.drawable.h2));
        mImages.add(BitmapDescriptorFactory.fromResource(R.drawable.h8));
        mImages.add(BitmapDescriptorFactory.fromResource(R.drawable.h9));

        mImages.add(BitmapDescriptorFactory.fromResource(R.drawable.mb1));

        floorButtonsJmsb =  mLevelButtons.findViewById(R.id.floorButtonsJmsb);
        floorButtonsHall = mLevelButtons.findViewById(R.id.floorButtonsHall);
    }

    public void hideLevelButton() {
        floorButtonsJmsb.setVisibility(View.INVISIBLE);
        floorButtonsHall.setVisibility(View.INVISIBLE);
        //removeOverlay();
        /*if(mLevelButtons.getVisibility() == View.VISIBLE) {
            mLevelButtons.setVisibility(View.INVISIBLE);
            removeOverlay();
        }*/
    }

    public void showLevelButton(String building) {

        if(building == "JMSB"){
            floorButtonsJmsb.setVisibility(View.VISIBLE);
        }
        if(building == "HALL"){
            floorButtonsHall.setVisibility(View.VISIBLE);
        }

        //if(mLevelButtons.getVisibility() == View.INVISIBLE) {
         //   mLevelButtons.setVisibility(View.VISIBLE);
        //}
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

    public void displayOverlayJMSB(){

        hidePOIs(1);
        mJMSBOverlay = mMap.addGroundOverlay(new GroundOverlayOptions()
                .image(mImages.get(4)).anchor(0, 1)
                .position(Building_JMSB, 70f, 70f)
                .bearing(130));



    }

    public void displayOverlayHall(){
        hidePOIs(1);

        mHALLOverlay = mMap.addGroundOverlay(new GroundOverlayOptions()
                .image(mImages.get(0)).anchor(0, 1)
                .position(NEAR_Building_HALL, 80f, 80f)
                .bearing(124));

    }




    public void displayOverlay(String building) {
        hidePOIs(1);

        /*?boolean firstDisplay=false;
        if (firstDisplay) {
            mGroundOverlay = mMap.addGroundOverlay(new GroundOverlayOptions()
                    .image(mImages.get(0)).anchor(0, 1)
                    .position(NEAR_Building_HALL, 80f, 80f)
                    .bearing(124));

        } else {*/
        //r



    }


    public void changeOverlay(int index, String building) {
        hidePOIs(1);
        //removeOverlay();

        if(building.equals("JMSB")){
            mJMSBOverlay.setImage(mImagesJMSB.get(index));

        }
        if(building.equals("HALL")) {
            mHALLOverlay.setImage(mImages.get(index));
        }
    }

    public void removeOverlay() {


        if(mHALLOverlay!=null){
            mHALLOverlay.setVisible(false);
        }
        if(mJMSBOverlay!=null){
            mJMSBOverlay.setVisible(false);
        }
    }
}