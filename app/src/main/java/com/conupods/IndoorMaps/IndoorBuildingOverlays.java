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

    private static final LatLng Building_HALL = new LatLng(45.497273, -73.578955);
    private static final LatLng NEAR_Building_HALL = new LatLng(Building_HALL.latitude + 0.0005, Building_HALL.longitude - 0.0001);


    private List<BitmapDescriptor> mImages = new ArrayList<BitmapDescriptor>();
    private GroundOverlay mGroundOverlay;
    private View mLevelButtons;


    public IndoorBuildingOverlays(View LevelButtons, GoogleMap map) {
        mMap = map;
        mLevelButtons = LevelButtons;
        mMap.setIndoorEnabled(false);
        mImages.clear();
        mImages.add(BitmapDescriptorFactory.fromResource(R.drawable.h8));
        mImages.add(BitmapDescriptorFactory.fromResource(R.drawable.h9));
    }

    public void hideLevelButton() {
        if(mLevelButtons.getVisibility() == View.VISIBLE) {
            mLevelButtons.setVisibility(View.GONE);
        }
    }

    public void showLevelButton() {

        if(mLevelButtons.getVisibility() == View.GONE) {
            mLevelButtons.setVisibility(View.VISIBLE);
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
            default:
                return;
        }
        mMap.setMapStyle(style);
    }

    public void displayOverlay(int index) {
        hidePOIs(1);
        mGroundOverlay = mMap.addGroundOverlay(new GroundOverlayOptions()
                .image(mImages.get(index)).anchor(0, 1)
                .position(NEAR_Building_HALL, 80f, 80f)
                .bearing(124));
    }

    public void removeOverlay() {
        if(mGroundOverlay != null) {
            mGroundOverlay.remove();
        }
    }
}