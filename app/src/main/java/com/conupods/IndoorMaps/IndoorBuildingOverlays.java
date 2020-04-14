package com.conupods.IndoorMaps;

import android.graphics.Bitmap;
import android.view.View;

import com.conupods.IndoorMaps.ConcreteBuildings.CCBuilding;
import com.conupods.IndoorMaps.ConcreteBuildings.HBuilding;
import com.conupods.IndoorMaps.ConcreteBuildings.MBBuilding;
import com.conupods.IndoorMaps.ConcreteBuildings.VLBuilding;
import com.conupods.OutdoorMaps.Models.Building.Building;
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

    public enum BuildingCodes {
        H, MB, VL, CC
    }

    //Building instances that contain data taken from json file
    private static Building hInstance = HBuilding.getInstance();
    private static Building mbInstance = MBBuilding.getInstance();
    private static Building vlInstance = VLBuilding.getInstance();
    private static Building ccInstance = CCBuilding.getInstance();

    //This array stores all the images for the overlays
    private List<BitmapDescriptor> mImages = new ArrayList<>();
    private GroundOverlay mHALLOverlay;
    private GroundOverlay mMBOverlay;
    private GroundOverlay mLOYCCOverlay;
    private GroundOverlay mLOYVLOverlay;
    private View mLevelButtons;
    private View floorButtonsHall;
    private View floorButtonsMB;
    private View floorButtonsLoyVl;

    public IndoorBuildingOverlays(View levelButtons, GoogleMap map) {
        mMap = map;
        mLevelButtons = levelButtons;
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
        mImages.add(BitmapDescriptorFactory.fromResource((R.drawable.vl1)));
        mImages.add(BitmapDescriptorFactory.fromResource(R.drawable.vl2));

        floorButtonsMB = mLevelButtons.findViewById(R.id.floorButtonsMB);
        floorButtonsHall = mLevelButtons.findViewById(R.id.floorButtonsHall);
        floorButtonsLoyVl = mLevelButtons.findViewById(R.id.floorButtonsLOYVL);
    }

    public void hideLevelButton() {
        floorButtonsMB.setVisibility(View.INVISIBLE);
        floorButtonsHall.setVisibility(View.INVISIBLE);
        floorButtonsLoyVl.setVisibility(View.INVISIBLE);
    }

    public void showFloorButtons(BuildingCodes buildings) {
        hidePOIs(1);
        switch (buildings) {
            case H:
                floorButtonsHall.setVisibility(View.VISIBLE);
                break;
            case MB:
                floorButtonsMB.setVisibility(View.VISIBLE);
                break;
            case VL:
                floorButtonsLoyVl.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }
    }

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

    public void displayOverlay(BuildingCodes buildings) {
        switch (buildings) {

            // TO DO, DON'T FORGET THE OTHER BUILDINGS

            case H:
                initializeOverlay(mHALLOverlay, 0, HBuilding.getBearing(), hInstance.getOverlayLatLng(), 0, 0, HBuilding.getWidth(), HBuilding.getHeight());
                break;
            case MB:
                initializeOverlay(mMBOverlay, 4, MBBuilding.getBearing(), mbInstance.getOverlayLatLng(), 0, 0, MBBuilding.getWidth(), MBBuilding.getHeight());
                break;
            case VL:
                initializeOverlay(mLOYVLOverlay, 7, 29, vlInstance.getOverlayLatLng(), 0, 0, 83f, 76f);
                break;
            case CC:
                initializeOverlay(mLOYCCOverlay, 6, 29, ccInstance.getOverlayLatLng(), 0, 0, 94f, 32f);
                break;
            default:
                break;
        }
    }


    private void initializeOverlay(GroundOverlay overlay, int index, int bearing,
                                   LatLng location, int anchor1, int anchor2, float width, float height) {

        if (overlay == null) {

            //Set the parameters for the overlay being passed
            GroundOverlayOptions overlayOptions = new GroundOverlayOptions()
                    .image(mImages.get(index)).anchor(anchor1, anchor2)
                    .position(location, width, height)
                    .bearing(bearing);

            createOverlay(overlayOptions);
        } else {
            overlay.setVisible(true);
        }
    }

    //Adds the overlay to the map
    private void createOverlay(GroundOverlayOptions overlayOptions) {

        if (overlayOptions.getLocation().equals(mbInstance.getOverlayLatLng())) {
            mMBOverlay = mMap.addGroundOverlay(overlayOptions);
        } else if (overlayOptions.getLocation().equals(hInstance.getOverlayLatLng())) {
            mHALLOverlay = mMap.addGroundOverlay(overlayOptions);
        } else if (overlayOptions.getLocation().equals(ccInstance.getOverlayLatLng())) {
            mLOYCCOverlay = mMap.addGroundOverlay(overlayOptions);
        } else if (overlayOptions.getLocation().equals(vlInstance.getOverlayLatLng())) {
            mLOYVLOverlay = mMap.addGroundOverlay(overlayOptions);
        }
    }

    //Changes the image displayed on the overlay
    public void changeOverlay(int index, BuildingCodes buildings) {
        hidePOIs(1);

        switch (buildings) {
            case H:
                mHALLOverlay.setImage(mImages.get(index));
                break;
            case MB:
                mMBOverlay.setImage(mImages.get(index));
                break;
            case VL:
                mLOYVLOverlay.setImage(mImages.get(index));
                break;
            default:
                break;
        }
    }

    public void removeOverlay() {
        hidePOIs(2);

        if (mHALLOverlay != null) {
            mHALLOverlay.setVisible(false);
        }
        if (mMBOverlay != null) {
            mMBOverlay.setVisible(false);
        }
        if (mLOYCCOverlay != null) {
            mLOYCCOverlay.setVisible(false);
        }
        if (mLOYVLOverlay != null) {
            mLOYVLOverlay.setVisible(false);
        }
    }

    public void changeOverlay(int index, Bitmap bm, BuildingCodes buildings) {


        BitmapDescriptor bmd = BitmapDescriptorFactory.fromBitmap(bm);

        mImages.set(index, bmd);

/*
        switch (buildings) {
            case H:
                mHALLOverlay.setImage(bmd);
                break;
            case MB:
                mMBOverlay.setImage(bmd);
                break;
            case VL:
                mLOYVLOverlay.setImage(bmd);
                break;
            default:
                break;
        }
        */
    }
}