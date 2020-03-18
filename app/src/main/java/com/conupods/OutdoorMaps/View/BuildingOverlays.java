package com.conupods.OutdoorMaps.View;

import android.content.res.Resources;
import android.os.AsyncTask;
import android.util.Log;

import com.conupods.R;
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

public class BuildingOverlays {

    private final static String mBuildingLogTag = "GeoJsonOverlay";
    private final static String mGeoStringLink = "https://gist.githubusercontent.com/carlitalabbe/87585a7c681a72c9a335cd282acee35d/raw/1740cf0f0d8bea67adbb133db7c60956d72c130c/map.geojson";
    private GoogleMap mMap;


    public BuildingOverlays(GoogleMap map) {
        mMap = map;
    }


    public void overlayPolygons() {
        new DownloadGeoJsonFile().execute(mGeoStringLink);
    }


    public void addColorsToMarkers(GeoJsonLayer layer) {
        // Iterate over all the features stored in the layer
        for (GeoJsonFeature feature : layer.getFeatures()) {
            // Check if the  property exists
            GeoJsonPolygonStyle buildingStyle = layer.getDefaultPolygonStyle();
            buildingStyle.setFillColor(0x80eac700);
            buildingStyle.setStrokeColor(0x80555555);
            buildingStyle.setStrokeWidth(5);
        }
    }

    private void addGeoJsonLayerToMap(GeoJsonLayer layer) {
        addColorsToMarkers(layer);
        layer.addLayerToMap();
    }

    private class DownloadGeoJsonFile extends AsyncTask<String, Void, GeoJsonLayer> {

        @Override
        protected GeoJsonLayer doInBackground(String... params) {
            try {
                // Open a stream from the URL
                InputStream stream = new URL(params[0]).openStream();
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
                return new GeoJsonLayer(mMap, new JSONObject(result.toString()));
            } catch (IOException e) {
                Log.e(mBuildingLogTag, "GeoJSON file could not be read");
            } catch (JSONException e) {
                Log.e(mBuildingLogTag, "GeoJSON file could not be converted to a JSONObject");
            }
            return null;
        }

        @Override
        protected void onPostExecute(GeoJsonLayer layer) {
            if (layer != null) {
                addGeoJsonLayerToMap(layer);
            }
        }
    }
}
