package com.conupods;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class BuildingDataMap {
    private static BuildingDataMap instance;
    private HashMap<LatLng, Building> data;

    private BuildingDataMap() throws JSONException {
        // load the data from json
        data = new HashMap<LatLng, Building>();
        parseBuildingData();
    }

    public static BuildingDataMap getInstance() {
        if (instance == null)
            try {
                instance = new BuildingDataMap();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }

        return instance;
    }

    public HashMap<LatLng, Building> getDataMap() {
        return data;
    }

    private void parseBuildingData() throws JSONException {
        JSONParser jsonParser = new JSONParser();
        // TODO add json file to resource and call correct path
        try (FileReader reader = new FileReader("C:\\Users\\bella\\git_repos\\SOEN390\\app\\src\\main\\java\\com\\conupods\\buildings.json")) {
            //Read JSON file
            Object obj = jsonParser.parse(reader);

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
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
