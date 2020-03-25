package com.conupods.OutdoorMaps;

import android.content.res.AssetManager;
import android.os.Build;
import android.util.JsonReader;
import android.util.Log;

import com.conupods.App;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A singleton class that holds hardcoded building data of concordia buildings.
 * The class parses the data (only once) that is saved in the file ../assets/buildings.json
 * who was imported from the concordia API manually.
 * Using eager initialization.
 */
public class BuildingDataMap {
    private static final BuildingDataMap INSTANCE = new BuildingDataMap();
    private HashMap<LatLng, Building> mData;
    private static final String TAG = "BUILDING_DATA_MAP";

    private BuildingDataMap() {
        // todo possibly change the index to be the marker object and not latlng
        mData = new HashMap<>();
        parseBuildingData();
    }

    public static BuildingDataMap getInstance() {
        return INSTANCE;
    }

    public HashMap<LatLng, Building> getDataMap() {
        return mData;
    }

    private void parseBuildingData() {
        AssetManager assetManager = App.getContext().getAssets();

        try (InputStream in = assetManager.open("buildings.json")) {

            JsonReader jsonReader = new JsonReader(new InputStreamReader(in, StandardCharsets.UTF_8));
            jsonReader.beginArray();
            while (jsonReader.hasNext()) {
                Building building = readBuildingJsonObject(jsonReader);

                // For debugging only
                // logAllCLassrooms(building);

                mData.put(building.getLatLng(), building);
            }
            jsonReader.endArray();
            jsonReader.close();

        } catch (IOException e) {
            Log.e(TAG, "Problem parsing building info asset");
        }
    }

    private Building readBuildingJsonObject(JsonReader jsonReader) throws IOException {
        List<String> classRooms = null;
        String campus = null;
        String code = null;
        String name = null;
        String longName = null;
        String address = null;
        double latitude = Double.NaN;
        double longitude = Double.NaN;

        jsonReader.beginObject();

        while (jsonReader.hasNext()) {
            String buildingData = jsonReader.nextName();
            switch (buildingData) {
                case "Classrooms":
                    classRooms = readClassroomJsonArray(jsonReader);
                    break;
                case "Campus":
                    campus = jsonReader.nextString();
                    break;
                case "Building":
                    code = jsonReader.nextString();
                    break;
                case "BuildingName":
                    name = jsonReader.nextString();
                    break;
                case "Building Long Name":
                    longName = jsonReader.nextString();
                    break;
                case "Address":
                    address = jsonReader.nextString();
                    break;
                case "Latitude":
                    latitude = jsonReader.nextDouble();
                    break;
                case "Longitude":
                    longitude = jsonReader.nextDouble();
                    break;
                default:
                    jsonReader.skipValue();
                    break;
            }
        }
        jsonReader.endObject();

        LatLng latLng = new LatLng(latitude, longitude);
        return new Building(
                campus,
                code,
                name,
                longName,
                address,
                latLng,
                classRooms
        );
    }

    private void logAllCLassrooms(Building building) {
        List<String> classrooms = building.getClassrooms();
        if (classrooms != null) {
            if (!classrooms.isEmpty()) {
                for (int i = 0; i < classrooms.size(); i++) {
                    Log.d(TAG, classrooms.get(i));
                }
            }
        }
    }

    private List<String> readClassroomJsonArray(JsonReader jsonReader) throws IOException {
        jsonReader.beginArray();
        List<String> classRooms = new ArrayList<>();
        while (jsonReader.hasNext()) {
            String classRoom = jsonReader.nextString();
            classRooms.add(classRoom);
            Log.d(TAG, classRoom);
        }
        jsonReader.endArray();
        return classRooms;
    }
}

