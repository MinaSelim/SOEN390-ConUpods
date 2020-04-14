package com.conupods.OutdoorMaps.Models.Building;

import astar.Edges;

public class Floor {

    private String[][] mMetaDataGrid;
    private boolean[][] mBinaryGrid;
    private int mLevel;

    private static final int GRID_SIZE = 25;
    private static final int RATIO = 11;


    public Floor() {
        this.mMetaDataGrid = null;
        this.mBinaryGrid = null;
        this.mLevel = -1;
    }

    public Floor(int level, String[][] floorMetaDataGrid, boolean[][] traversalBinaryGrid) {
        this.mLevel = level;
        this.mMetaDataGrid = floorMetaDataGrid;
        this.mBinaryGrid = traversalBinaryGrid;
    }

    public void burrowRoom(String location) {

        Edges roomCoords = getEdges(location);

            for (int i = roomCoords.getTop(); i <= roomCoords.getBottom(); i++) {
                for (int j = roomCoords.getLeft(); j <= roomCoords.getRight(); j++) {
                    this.mBinaryGrid[i][j] = false;
                }
            }

    }


    public int[] getCenterCoords(String location) {

        Edges roomCoords = getEdges(location);

        int[] coords = new int[2];

        coords[0] = getAvg(roomCoords.getRight(), roomCoords.getLeft())+roomCoords.getLeft();
        coords[1] = getAvg(roomCoords.getBottom(), roomCoords.getTop())+roomCoords.getTop();

        return coords;
    }

    public int getAvg(int a, int b){
        return Math.abs((a-b)/2);
    }


    public Edges getEdges(String location) {

        int minX = GRID_SIZE;
        int minY = GRID_SIZE;
        int maxX = 0;
        int maxY = 0;

        if (this.mMetaDataGrid != null) {

            for (int j = 0; j < this.mMetaDataGrid.length; j++) {
                for (int k = 0; k < this.mMetaDataGrid[j].length; k++) {

                    if (location.equals(this.mMetaDataGrid[j][k])) {
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
        return new Edges(minX, maxX, minY, maxY);

    }

    public String[][] getMetaDataGrid() {
        return mMetaDataGrid;
    }

    public void setMetaDataGrid(String[][] mMetaDataGrid) {
        this.mMetaDataGrid = mMetaDataGrid;
    }

    public boolean[][] getBinaryGrid() {
        return mBinaryGrid;
    }

    public void setBinaryGrid(boolean[][] mBinaryGrid) {
        this.mBinaryGrid = mBinaryGrid;
    }

    public int getLevel() {
        return mLevel;
    }

    public void setLevel(int mLevel) {
        this.mLevel = mLevel;
    }


}
