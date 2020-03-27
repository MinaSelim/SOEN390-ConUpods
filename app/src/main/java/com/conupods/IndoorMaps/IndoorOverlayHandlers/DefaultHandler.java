package com.conupods.IndoorMaps.IndoorOverlayHandlers;

import com.conupods.IndoorMaps.ConcreteBuildings.CCBuilding;
import com.conupods.IndoorMaps.ConcreteBuildings.HBuilding;
import com.conupods.IndoorMaps.ConcreteBuildings.MBBuilding;
import com.conupods.IndoorMaps.ConcreteBuildings.VLBuilding;
import com.conupods.IndoorMaps.IndoorBuildingOverlays;
import com.conupods.OutdoorMaps.Building;
import com.google.android.gms.maps.model.LatLngBounds;

import java.util.ArrayList;
import java.util.List;

public class DefaultHandler extends IndoorOverlayHandler {

    Building mbInstance = MBBuilding.getInstance();
    Building vlInstance = VLBuilding.getInstance();
    Building hInstance = HBuilding.getInstance();
    Building ccInstance = CCBuilding.getInstance();

    List<Building> buildingList = new ArrayList<Building>();

    @Override
    public void checkBounds(LatLngBounds bounds, IndoorBuildingOverlays indoorBuildingOverlays) {
        buildingList.add(mbInstance);
        buildingList.add(vlInstance);
        buildingList.add(hInstance);
        buildingList.add(ccInstance);

        for (Building building : buildingList) {
            if (!bounds.contains(building.getLatLng())) {
                indoorBuildingOverlays.removeOverlay();
                indoorBuildingOverlays.hideLevelButton();
            }
            break;
        }
        if (nextInChain != null) {
            nextInChain.checkBounds(bounds, indoorBuildingOverlays);
        }
    }
}