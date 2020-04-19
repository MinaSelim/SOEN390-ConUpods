package com.conupods.IndoorMaps.View;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.conupods.IndoorMaps.IndoorBuildingOverlays;

import java.util.ArrayList;
import java.util.List;

import astar.Spot;

public class PathOverlay {

    private static final float PIXELS = 275f;

    // Array is ordered exactly like IndoorBuildingOverlays.mImages
    private final String[] level = {"1", "2", "8", "9", "1", "s2", "1", "1", "2"};

    public float[][] xyPoints(Spot endSpot) {

        List<Float> points = new ArrayList<>();


        while (endSpot != null) {
            points.add((float) endSpot.getY() / PIXELS);
            points.add((float) endSpot.getX() / PIXELS);
            endSpot = endSpot.getPrevious();
        }

        float[][] xyPoints = new float[points.size() / 2][2];

        for (int i = 0; i < points.size(); i++) {
            if (i % 2 == 0) {
                xyPoints[i / 2][0] = points.get(i);
            } else {
                xyPoints[i / 2][1] = points.get(i);
            }
        }


        // returns float[] of the form [[x1,y1],[x2,y2],[x3,y3],..]
        return xyPoints;
    }

    public float[][] createLines(float[][] f) {

        float[][] f2 = new float[(f.length - 1) * 2][2];

        for (int i = 1; i < f2.length; i++) {
            int rep = 2;

            if (i / rep == 0) {
                f2[i / rep][0] = f[i][0];
                f2[i / rep][1] = f[i][1];
            } else if ((i + 3) / rep == f.length) {
                f2[i][0] = f[(i + 1) / rep][0];
                f2[i][1] = f[(i + 1) / rep][1];
            } else {
                f2[i - 1][0] = f[i / rep][0];
                f2[i - 1][1] = f[i / rep][1];
                f2[i][0] = f[i / rep][0];
                f2[i][1] = f[i / rep][1];
            }

        }

        return f2;

    }

    public float[] expandToSingleLayer(float[][] df) {
        float[] f = new float[df.length * 2];
        for (int i = 0; i < f.length; i++) {
            if (i % 2 == 0) {
                f[i] = df[i / 2][0];
            } else {
                f[i] = df[i / 2][1];
            }

        }
        return f;
    }

    public Bitmap drawLinesToBitmap(Context context, int gResId, float[] f) {
        Resources resources = context.getResources();
        Bitmap bitmap = BitmapFactory.decodeResource(resources, gResId);

        android.graphics.Bitmap.Config bitmapConfig = bitmap.getConfig();
        // set default bitmap config if none
        if (bitmapConfig == null) {
            bitmapConfig = android.graphics.Bitmap.Config.ARGB_8888;
        }
        // resource bitmaps are imutable,
        // so we need to convert it to mutable one
        bitmap = bitmap.copy(bitmapConfig, true);

        float w = bitmap.getWidth();
        float h = bitmap.getHeight();

        Canvas canvas = new Canvas(bitmap);

        // new antialised Paint
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

        // set color to blue
        paint.setColor(Color.rgb(0, 0, 255));
        paint.setStrokeWidth(10);

        for (int i = 0; i < f.length; i++) {
            if (i % 2 == 0) {
                f[i] = f[i] * w;
            } else {
                f[i] = f[i] * h;
            }
        }

        canvas.drawLines(f, paint);

        return bitmap;
    }

    public void drawIndoorPath(IndoorBuildingOverlays indoorBuildingOverlays, Context context, Spot endSpot) {

        float[] lines = expandToSingleLayer(createLines(xyPoints(endSpot)));
        int floorIndex = endSpot.getFloor() + getOffset(endSpot.getBuilding());

        String levelNumber = level[floorIndex];
        String building = endSpot.getBuilding().toLowerCase() + levelNumber;

        int rId = context.getResources().getIdentifier(building, "drawable", context.getPackageName());

        Bitmap floorWithPath = drawLinesToBitmap(context, rId, lines);

        indoorBuildingOverlays.changeOverlay(floorIndex, floorWithPath);

    }

    public int getOffset(String building) {
        int offset;

        building = building.toLowerCase();

        if (building.equals("h")) {
            offset = 0;
        } else if (building.equals("mb")) {
            offset = 4;
        } else if (building.equals("cc")) {
            offset = 6;
        } else if (building.equals("vl")) {
            offset = 7;
        } else {
            offset = 0;
        }

        return offset;

    }

}
