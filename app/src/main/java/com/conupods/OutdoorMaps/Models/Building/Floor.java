package com.conupods.OutdoorMaps.Models.Building;

import astar.Edges;

public class Floor {

    private String[][] mMetaDataGrid;
    private boolean[][] mBinaryGrid;
    private int mLevel;

    private final int GRID_SIZE = 25;



    public Floor(String[][] floorMetaDataGrid, boolean[][] traversalBinaryGrid, int level) {
        this.mMetaDataGrid = floorMetaDataGrid;
        this.mBinaryGrid = traversalBinaryGrid;
        this.mLevel= level;
    }

    public void burrowRoom(String location) {

        Edges roomCoords = getEdges(location);


        for (int i = roomCoords.getTop(); i < roomCoords.getBottom(); i++) {
            for (int j = roomCoords.getLeft(); j < roomCoords.getRight(); j++) {
                this.mBinaryGrid[i][j] = false;
            }
        }

    }

    public Edges getEdges(String location) {

        int minX = GRID_SIZE;
        int minY = GRID_SIZE;
        int maxX = 0;
        int maxY = 0;

        if (mMetaDataGrid != null) {
                for (int j = 0; j < mMetaDataGrid.length; j++) {
                    for (int k = 0; k < mMetaDataGrid[j].length; k++) {
                        if (location.equals(mMetaDataGrid[j][k])) {
                            if (k < minX) {
                                minX = k;
                            }
                            if (k > maxX) {
                                maxX = k;
                            }
                            if (j < minY) {
                                minY = j;
                            }
                            if (j > maxY) {
                                maxY = j;
                            }
                        }
                    }
                }
            }

        // Assuming (0,0) is top left
        return new Edges(minX, maxX, maxY, minY);

    }

}
