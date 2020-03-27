package com.conupods.OutdoorMaps;

import android.content.res.AssetManager;
import android.os.Build;
import android.util.JsonReader;
import android.util.Log;

import com.conupods.App;
import com.conupods.OutdoorMaps.Models.Building.Building;
import com.conupods.OutdoorMaps.Models.Building.Campus;
import com.conupods.OutdoorMaps.Models.Building.Classroom;
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
    private List<Classroom> mClassroomData = new ArrayList<>();
    private List<Building> mBuildingsData = new ArrayList<>();

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

    public List<Classroom> getClassroomDataDataList() { return mClassroomData; }

    public List<Building> getmBuildingsDataList() {
        return mBuildingsData;
    }

    private void parseBuildingData() {
        AssetManager assetManager = App.getContext().getAssets();

        try (InputStream in = assetManager.open("buildings.json")) {

            JsonReader jsonReader = new JsonReader(new InputStreamReader(in, StandardCharsets.UTF_8));
            jsonReader.beginArray();

            while (jsonReader.hasNext()) {
                Building building = readBuildingJsonObject(jsonReader);
                Log.d(TAG,"Find Building Campus: "+building.getConcreteParent()+" "+building.getIdentifier() +" "+ building.getClassRooms());

                if (building != null) {
                    mData.put(building.getLatLng(), building);
                    mBuildingsData.add(building);

                    if (building.getClassRooms() != null) {
                        Log.d(TAG, "Classroom array :"+building.getClassRooms().toString());
                        if (!building.getClassRooms().isEmpty()) {
                            List<String> classroomList = building.getClassRooms();
                            instantiateAllClassrooms(building, classroomList);
                        }
                    }
                }


            }
            jsonReader.endArray();
            jsonReader.close();

        } catch (IOException e) {
            Log.e(TAG, "Problem parsing building info asset");
        }
    }

    private void instantiateAllClassrooms(Building building, List<String> classroomList) {
        Log.d(TAG, "CREATING CLASSROOMS");
        for (String classoomName : classroomList) {
            Classroom classroomObject = new Classroom(classoomName, building.getLatLng(), building);
            mClassroomData.add(classroomObject);
            Log.d(TAG, classoomName+" WHEN CREATING CLASSROOMS");
        }
    }

    private Building readBuildingJsonObject(JsonReader jsonReader) throws IOException {
        List<String> classRooms = new ArrayList<>();
        String campusName = null;
        Campus campus = null;
        String code = null;
        String name = null;
        String longName = null;
        String address = null;
        double latitude = Double.NaN;
        double longitude = Double.NaN;
        double overlayLatitude = Double.NaN;
        double overlayLongitude = Double.NaN;

        jsonReader.beginObject();

        while (jsonReader.hasNext()) {
            String buildingData = jsonReader.nextName();
            switch (buildingData) {
                case "Classrooms":
                    classRooms.addAll(readClassroomJsonArray(jsonReader));
                    break;
                case "Campus":
                    campusName = jsonReader.nextString();
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
                case "Overlay Latitude":
                    overlayLatitude = jsonReader.nextDouble();
                    break;
                case "Overlay Longitude":
                    overlayLongitude = jsonReader.nextDouble();
                    break;
                default:
                    jsonReader.skipValue();
                    break;
            }
        }
        jsonReader.endObject();

        LatLng latLng = new LatLng(latitude, longitude);
        LatLng overlayLatLng = new LatLng(overlayLatitude, overlayLongitude);
        LatLng campusCoordinates = getCampusCoordinates(campusName);
        Campus campusObject = new Campus(campusName, campusCoordinates);

        Log.d(TAG, "Longname before creating building : "+ longName);
        return new Building(
                classRooms,
                latLng,
                name,
                campusObject,
                longName,
                address,
                code
                overlayLatLng
        );

    }


    private LatLng getCampusCoordinates(String campusName) {
        LatLng campusCoordinates;
        switch (campusName) {
            case "SGW":
                campusCoordinates = new LatLng(45.4946, -73.5774);
                break;
            case "LOY":
                campusCoordinates = new LatLng(45.4582, -73.6405);
                break;
            default:
                campusCoordinates = null;
        }
        return campusCoordinates;
    }

    private List<String> readClassroomJsonArray(JsonReader jsonReader) throws IOException {
        jsonReader.beginArray();
        List<String> classRooms = new ArrayList<>();
        while (jsonReader.hasNext()) {
            String classRoom = jsonReader.nextString();
            classRooms.add(classRoom);
        }
        jsonReader.endArray();
        return classRooms;
    }
}

