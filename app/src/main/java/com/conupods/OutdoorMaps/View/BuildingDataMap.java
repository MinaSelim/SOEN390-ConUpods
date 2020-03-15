package com.conupods.OutdoorMaps.View;

import android.content.res.AssetManager;

import com.conupods.App;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

public class BuildingDataMap {
    private static BuildingDataMap instance;
    private HashMap<LatLng, Building> data;

    private BuildingDataMap() throws Exception {
        // todo possibly change the index to be the marker object and not latlng
        data = new HashMap<>();
        parseBuildingData();
    }

    public static synchronized BuildingDataMap getInstance() {
        if (instance == null) {
            try {
                instance = new BuildingDataMap();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        return instance;
    }

    public HashMap<LatLng, Building> getDataMap() {
        return data;
    }

    private void parseBuildingData() throws Exception {
        JSONParser jsonParser = new JSONParser();
        AssetManager assetManager = App.getContext().getAssets();

        // TODO add json file to resource and call correct path
        try (InputStream reader = assetManager.open("buildings.json")) {
            //Read JSON file
            Object obj = jsonParser.parse(new InputStreamReader(reader));

            JSONArray buildingList = (JSONArray) obj;

            for (Object element : buildingList) {
                JSONObject building = (JSONObject) element;
                LatLng latLng = new LatLng((Double) building.get("Latitude"), (Double) building.get("Longitude"));

                Building buildingObj = new Building(
                        (String) (building.get("Campus")),
                        (String) (building.get("Building")),
                        (String) (building.get("BuildingName")),
                        (String) (building.get("Building Long Name")),
                        (String) (building.get("Address")),
                        latLng
                );
                data.put(latLng, buildingObj);
            }
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException(e.getMessage());
        } catch (IOException e) {
            throw new IOException(e.getMessage());
        } catch (ParseException e) {
            throw new Exception(e.getMessage());
        }
    }
}
