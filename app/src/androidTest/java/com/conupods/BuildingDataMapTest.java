package com.conupods;

import android.content.res.AssetManager;
import android.util.Log;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.conupods.OutdoorMaps.Building;
import com.conupods.OutdoorMaps.BuildingDataMap;
import com.google.android.gms.maps.model.LatLng;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import java.io.IOException;
import java.util.HashMap;

import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Class that tests the building data mapper.
 * This cannot be a unit test because the application context is required.
 */
@RunWith(AndroidJUnit4.class)
public class BuildingDataMapTest {


    @Test
    public void parseBuildingDataTest() {
        BuildingDataMap buildingDataMap = BuildingDataMap.getInstance();
        assertNotNull(buildingDataMap);
        HashMap data = buildingDataMap.getDataMap();
        assertNotNull(data);
        LatLng latLng = new LatLng(45.457984, -73.639834);
        Building buildingExpected = new Building(
                "LOY",
                "AD",
                "AD Building",
                "Administration Building",
                "7141, Sherbrooke West",
                latLng);

        Building buildingActual = (Building) data.get(latLng);
        assertNotNull(buildingActual);
        assertEquals(buildingExpected.getCampus(), buildingActual.getCampus());
        assertEquals(buildingExpected.getCode(), buildingActual.getCode());
        assertEquals(buildingExpected.getName(), buildingActual.getName());
        assertEquals(buildingExpected.getLongName(), buildingActual.getLongName());
        assertEquals(buildingExpected.getAddress(), buildingActual.getAddress());
    }

    // Leaving this as an example for now
    @Ignore("Unable to mock final classes with mockito and android")
    public void parseBuildingDataTestFailureTest() throws IOException {
        // Force a file error
        AssetManager assetManager = mock(AssetManager.class); // This would work if not a final class
        when(assetManager.open("buildings.json")).thenThrow(new IOException("Mock error"));
        BuildingDataMap buildingDataMap = BuildingDataMap.getInstance();
        assertNotNull(buildingDataMap);
        assertEquals(0, buildingDataMap.getDataMap().size());
    }
}
