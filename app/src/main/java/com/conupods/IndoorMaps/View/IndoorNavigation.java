package com.conupods.IndoorMaps.View;


import android.content.res.AssetManager;

import com.conupods.App;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import astar.Destination;

public class IndoorNavigation {

    private AssetManager assetManager;

    public IndoorNavigation() {
        this.assetManager = App.getContext().getAssets();
    }

    public InputStreamReader getInputStreamReader(String filePath) {
        InputStreamReader in = null;

        try {
            in = new InputStreamReader(assetManager.open(filePath), StandardCharsets.UTF_8);
        } catch (IOException e) {
            System.err.println(e);
        }

        return in;
    }


    public boolean[][] createBinaryGrid(String filePath) {

        BufferedReader in = new BufferedReader(getInputStreamReader(filePath));

        List<String> mapString = new ArrayList<>();

        while (true) {
            try {
                String nextLine = in.readLine();
                if (nextLine == null) {
                    in.close();
                    break;
                }
                mapString.add(nextLine);
            } catch (IOException e) {
                break;
            }
        }

        boolean[][] bool = new boolean[mapString.size()][mapString.get(mapString.size() - 1).length()];

        for (int i = 0; i < bool.length; i++) {

            String[] chars = mapString.get(i).split("");

            for (int j = 0; j < bool[i].length; j++) {

                if (chars[j].equals("0")) {
                    bool[j][i] = false;
                } else {
                    bool[j][i] = true;
                }
            }
        }

        return bool;

    }

    public String getBuildingGrid(Destination destination) {

        String buildingFile = "imagebooleanarray/";
        buildingFile += destination.getmBuilding();
        buildingFile += destination.getmFloor();

        return buildingFile;
    }

}
