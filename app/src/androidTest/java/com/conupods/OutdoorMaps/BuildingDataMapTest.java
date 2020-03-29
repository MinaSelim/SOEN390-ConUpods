package com.conupods.OutdoorMaps;

import android.content.res.AssetManager;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.conupods.OutdoorMaps.Models.Building.Building;
import com.conupods.OutdoorMaps.Models.Building.Campus;
import com.conupods.OutdoorMaps.Models.Building.Classroom;
import com.google.android.gms.maps.model.LatLng;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.mock;
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
        Map<LatLng, Building> data = buildingDataMap.getDataMap();
        assertNotNull(data);
        LatLng latLng = new LatLng(45.457984, -73.639834);
        List<String> classrooms = new ArrayList<>();
        Campus campusExprexted = new Campus("LOY", latLng);

        Building buildingExpected = new Building(
                classrooms,
                latLng,
                "AD Building",
                campusExprexted,
                "Administration Building",
                "7141, Sherbrooke West",
                "AD",
                latLng

                // TODO: UPDATE THIS TO ACTUAL VALUE when json file updated!!!!
        );


        Building buildingActual = (Building) data.get(latLng);
        assertNotNull(buildingActual);
        assertEquals(buildingExpected.getConcreteParent(), buildingActual.getConcreteParent());
        assertEquals(buildingExpected.getCode(), buildingActual.getCode());
        assertEquals(buildingExpected.getName(), buildingActual.getName());
        assertEquals(buildingExpected.getLongName(), buildingActual.getLongName());
        assertEquals(buildingExpected.getAddress(), buildingActual.getAddress());
        assertEquals(buildingExpected.getClassRooms(), buildingActual.getClassRooms());
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