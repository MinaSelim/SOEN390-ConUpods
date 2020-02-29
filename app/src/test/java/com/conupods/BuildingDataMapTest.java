package com.conupods;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONException;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

public class BuildingDataMapTest {

    @Test
    public void parseBuildingDataTest() throws JSONException {
        BuildingDataMap buildingDataMap = BuildingDataMap.getInstance();
        HashMap data = buildingDataMap.getDataMap();
        assertNotNull(data);
        LatLng latLng = new LatLng(45.457984, -73.639834);
        Building buildingExpected = new Building("LOY", "AD", "AD Building", "Administration Building", "7141, Sherbrooke West", latLng);
        Building buildingActual = (Building) data.get(latLng);
        assertEquals(buildingExpected.getCampus(), buildingActual.getCampus());
        assertEquals(buildingExpected.getCode(), buildingActual.getCode());
        assertEquals(buildingExpected.getName(), buildingActual.getName());
        assertEquals(buildingExpected.getLongName(), buildingActual.getLongName());
        assertEquals(buildingExpected.getAddress(), buildingActual.getAddress());
    }

}