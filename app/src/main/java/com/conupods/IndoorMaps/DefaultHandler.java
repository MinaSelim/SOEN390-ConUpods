package com.conupods.IndoorMaps;

import com.conupods.OutdoorMaps.Building;
import com.google.android.gms.maps.model.LatLngBounds;

public class DefaultHandler extends IndoorOverlayHandler {

    Building MBInstance = MBBuilding.getInstance();
    Building VLInstance = VLBuilding.getInstance();
    Building HInstance = HBuilding.getInstance();
    Building CCInstance = HBuilding.getInstance();


    @Override
    public void checkBounds(LatLngBounds bounds, IndoorBuildingOverlays indoorBuildingOverlays) {
        if(!bounds.contains(MBInstance.getLatLng())) {
            if(!bounds.contains(VLInstance.getLatLng())){
                if(!bounds.contains(HInstance.getLatLng())){
                    if(!bounds.contains(CCInstance.getLatLng())){
                        indoorBuildingOverlays.removeOverlay();
                        indoorBuildingOverlays.hideLevelButton();
                    }
                }
            }
        }else {
            if(nextInChain!=null) {
                nextInChain.checkBounds(bounds, indoorBuildingOverlays);
            }
        }
    }
}
