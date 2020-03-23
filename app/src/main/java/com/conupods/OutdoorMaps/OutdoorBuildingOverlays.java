package com.conupods.OutdoorMaps;

import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.maps.android.data.geojson.GeoJsonFeature;
import com.google.maps.android.data.geojson.GeoJsonLayer;
import com.google.maps.android.data.geojson.GeoJsonPolygonStyle;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

public class OutdoorBuildingOverlays {

    private final static String BUILDING_LOG_TAG = "GeoJsonOverlay";
    private GoogleMap mMap;
    private String mGeoStringLink;
    private GeoJsonLayer mGeoJsonLayer;
    private Thread mGeoJsonDownloader;

    public OutdoorBuildingOverlays(GoogleMap map, String geoLink) {
        mMap = map;
        mGeoStringLink = geoLink;
        mGeoJsonDownloader = new Thread(new DownloadGeoJsonFile());
        mGeoJsonDownloader.start();
    }

    public void overlayPolygons() {
        try {
            if (mGeoJsonLayer == null)
                mGeoJsonDownloader.join();
            if (mGeoJsonLayer != null && !mGeoJsonLayer.isLayerOnMap()) {
                addColorsToMarkers(mGeoJsonLayer);
                mGeoJsonLayer.addLayerToMap();
            }
        } catch (InterruptedException e) {
            Log.e(BUILDING_LOG_TAG, "Interruped Thread Exception");
        }
    }

    public void removePolygons() {
        if (mGeoJsonLayer != null) {
            mGeoJsonLayer.removeLayerFromMap();
        }
    }

    private void addColorsToMarkers(GeoJsonLayer layer) {
        // Iterate over all the features stored in the layer
        for (GeoJsonFeature feature : layer.getFeatures()) {
            // Check if the  property exists
            GeoJsonPolygonStyle buildingStyle = layer.getDefaultPolygonStyle();
            buildingStyle.setFillColor(0x80eac700);
            buildingStyle.setStrokeColor(0x80555555);
            buildingStyle.setStrokeWidth(5);
        }
    }

    private class DownloadGeoJsonFile implements Runnable {

        @Override
        public void run() {
            try {
                // Open a stream from the URL
                InputStream stream = new URL(mGeoStringLink).openStream();
                String line;
                StringBuilder result = new StringBuilder();
                BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
                while ((line = reader.readLine()) != null) {
                    // Read and save each line of the stream
                    result.append(line);
                }
                // Close the stream
                reader.close();
                stream.close();
                mGeoJsonLayer = new GeoJsonLayer(mMap, new JSONObject(result.toString()));
            } catch (IOException e) {
                Log.e(BUILDING_LOG_TAG, "GeoJSON file could not be read");
            } catch (JSONException e) {
                Log.e(BUILDING_LOG_TAG, "GeoJSON file could not be converted to a JSONObject");
            }
        }
    }
}
