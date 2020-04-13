package com.conupods.IndoorMaps;


import org.junit.Test;

import static org.junit.Assert.assertTrue;

import com.conupods.IndoorMaps.View.BuildingsBean;
import com.google.android.gms.maps.model.LatLng;

public class indoorPathTestClass {

    @Test
    public void testInstanceOfBuilding() {
        BuildingsBean coordinatesCheck = new BuildingsBean();
        assertTrue(coordinatesCheck.getNECC() instanceof LatLng);
        assertTrue(coordinatesCheck.getNEVE() instanceof LatLng);
        assertTrue(coordinatesCheck.getNEVL() instanceof LatLng);
        assertTrue(coordinatesCheck.getNEJMSB() instanceof LatLng);
        assertTrue(coordinatesCheck.getNEHALL() instanceof LatLng);

        assertTrue(coordinatesCheck.getNWCC() instanceof LatLng);
        assertTrue(coordinatesCheck.getNWVE() instanceof LatLng);
        assertTrue(coordinatesCheck.getNWVL() instanceof LatLng);
        assertTrue(coordinatesCheck.getNWJMSB() instanceof LatLng);
        assertTrue(coordinatesCheck.getNWHALL() instanceof LatLng);

        assertTrue(coordinatesCheck.getSECC() instanceof LatLng);
        assertTrue(coordinatesCheck.getSEVE() instanceof LatLng);
        assertTrue(coordinatesCheck.getSEVL() instanceof LatLng);
        assertTrue(coordinatesCheck.getSEJMSB() instanceof LatLng);
        assertTrue(coordinatesCheck.getSEHALL() instanceof LatLng);

        assertTrue(coordinatesCheck.getSWCC() instanceof LatLng);
        assertTrue(coordinatesCheck.getSWVE() instanceof LatLng);
        assertTrue(coordinatesCheck.getSWVL() instanceof LatLng);
        assertTrue(coordinatesCheck.getSWJMSB() instanceof LatLng);
        assertTrue(coordinatesCheck.getSWHALL() instanceof LatLng);

    }
}
