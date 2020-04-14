package com.conupods.OutdoorMaps.Models.Building;

import android.content.res.AssetManager;

import com.conupods.App;
import com.conupods.IndoorMaps.IndoorCoordinates;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Building extends AbstractCampusLocation {

    private List<String> mClassrooms;
    private Campus mCampus;
    private String mCode;
    private String mLongName;
    private String mAddress;
    private LatLng mOverlayLatLng;
    protected AssetManager mAssetManager;

    protected String[][][] mFloorMetaDataGrid;
    protected boolean[][][] mTraversalBinaryGrid;
    protected int mLevel;

    private List<Floor> mFloor;

    public Building(List<String> classrooms, LatLng coordinates, String name, Campus campus, String longName, String address, String code, LatLng overlayLatLng) {
        super(name, coordinates, longName);

        mCampus = campus;
        mCode = code;  // Letter of Building
        mLongName = longName;
        mAddress = address;
        mOverlayLatLng = overlayLatLng;
        mClassrooms = classrooms;
        this.mAssetManager = App.getContext().getAssets();
        this.mFloor = null;


    }

    public boolean[][] createBinaryGrid(String filePath) throws IOException {


        InputStream reader = mAssetManager.open(filePath);
        Scanner in = new Scanner(new InputStreamReader(reader));

        List<String> mapString = new ArrayList<>();

        while (in.hasNext()) {

                String nextLine = in.nextLine();

                if(!nextLine.equals("")) {
                    mapString.add(nextLine);
                }
        }

        boolean[][] bool = new boolean[mapString.size()][mapString.get(mapString.size() - 1).length()];

        for (int i = 0; i < bool.length; i++) {

            String[] chars = mapString.get(i).split("");

            for (int j = 0; j < bool[i].length; j++) {

                if (chars[j].equals("0")) {
                    bool[i][j] = false;
                } else {
                    bool[i][j] = true;
                }
            }
        }

        return bool;

    }

    private void removeClassroom(Classroom classroom) {
        if (mClassrooms != null && !mClassrooms.isEmpty() && mClassrooms.contains(classroom.toString())) {
            mClassrooms.remove(classroom.toString());
        }
    }

    public String getCode() {
        return mCode;
    }

    public String getLongName() {
        return mLongName;
    }

    public String getAddress() {
        return mAddress;
    }

    public LatLng getLatLng() {
        return super.getCoordinates();
    }

    public List<String> getClassRooms() {
        return mClassrooms;
    }

    @Override
    public String getConcreteParent() {
        return mCampus.toString();
    }

    public LatLng getOverlayLatLng() {
        return mOverlayLatLng;
    }

    public String getName() {
        return super.getIdentifier();
    }

    public IndoorCoordinates getLocationCoordinates(String location) {


       if (mFloorMetaDataGrid != null) {

           for (int floor = 0; floor < mFloorMetaDataGrid.length; floor++) {
               for (int j = 0; j<mFloorMetaDataGrid[floor].length; j++) {
                   for (int k = 0; k<mFloorMetaDataGrid[floor][j].length; k++) {

                       if (location.equals(mFloorMetaDataGrid[floor][j][k])) {
                           return new IndoorCoordinates(j,k,floor,location);
                       }

                   }
               }
           }

       }

       return null;

    }

    public boolean[][] getTraversalBinaryGridFromFloor(int floor) {
        return mTraversalBinaryGrid[floor];
    }

    public String[][] getFloorMetaDataGrid(int floor) {
        return mFloorMetaDataGrid[floor];
    }

    protected void initializeGridsByFloor(int floor, String metaDataGridPath, String traversalGridPath) {
        try {
            Scanner s = new Scanner(new InputStreamReader(mAssetManager.open(metaDataGridPath), StandardCharsets.UTF_8));
            StringBuilder gridContentInJson = new StringBuilder();
            while(s.hasNext()) {
                gridContentInJson.append(s.nextLine());
            }

            Gson gson = new Gson();
            mFloorMetaDataGrid[floor] = gson.fromJson(gridContentInJson.toString(), String[][].class);
            mTraversalBinaryGrid[floor] = createBinaryGrid(traversalGridPath);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}





//        int minX = GRID_SIZE;
//        int minY = GRID_SIZE;
//        int maxX = 0;
//        int maxY = 0;
//
//        for (int i = 0; i < coords.size(); i++) {
//
//            JSONObject xy = (JSONObject) coords.get(String.valueOf(i));
//
//            int x = Math.toIntExact((long) xy.get("x"));
//            int y = Math.toIntExact((long) xy.get("y"));
//
//            if (x < minX) {
//                minX = x;
//            }
//            if (x > maxX) {
//                maxX = x;
//            }
//            if (y < minY) {
//                minY = y;
//            }
//            if (y > maxY) {
//                maxY = y;
//            }
//
