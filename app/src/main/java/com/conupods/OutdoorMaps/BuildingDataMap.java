package com.conupods.OutdoorMaps;

import android.content.res.AssetManager;
import android.util.Log;

import com.conupods.App;
import com.google.android.gms.maps.model.LatLng;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

/**
 * A singleton class that holds hardcoded building data of concordia buildings.
 * The class parses the data (only once) that is saved in the file ../assets/buildings.json
 * who was imported from the concordia API manually.
 */
public class BuildingDataMap {
    private static BuildingDataMap mInstance;
    private HashMap<LatLng, Building> mData;
    private static final String TAG = "BUILDING_DATA_MAP";

    private BuildingDataMap() throws BuildingException {
        // todo possibly change the index to be the marker object and not latlng
        mData = new HashMap<>();
        parseBuildingData();
    }

    public static synchronized BuildingDataMap getInstance() {
        if (mInstance == null) {
            try {
                mInstance = new BuildingDataMap();
            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
                return null;
            }
        }
        return mInstance;
    }

    public HashMap<LatLng, Building> getDataMap() {
        return mData;
    }

    private void parseBuildingData() throws BuildingException {
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
                mData.put(latLng, buildingObj);
            }
        } catch (ParseException | IOException e) {
            Log.e(TAG, "Problem parsing building info asset");
            throw new BuildingException(e.getMessage());
        }
    }

}
