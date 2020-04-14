package com.conupods.IndoorMaps.View;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.conupods.IndoorMaps.IndoorBuildingOverlays;
import com.google.android.gms.maps.GoogleMap;

import java.util.ArrayList;
import java.util.List;

import astar.Spot;

public class PathOverlay {

    private GoogleMap mMap;

    final static BuildingsBean buildings = new BuildingsBean();

    private final BuildingsBean BUILDINGS = new BuildingsBean();

    private final static float PIXELS = 275f;

    public PathOverlay(GoogleMap map) {
        mMap = map;
    }

//     dummy Spot object

    public Spot dummySpot(){
        Spot dummy = new Spot(0,0, false);
        dummy.setBuilding("H");
        dummy.setPrevious(new Spot(275,275,false));
        /*
        Spot temp = dummy.getPrevious();
        temp.setPrevious(new Spot(275,0,false));
        Spot temp2 = temp.getPrevious();
        temp2.setPrevious(new Spot(275,275, false));

         */
        return dummy;
    }


    public float[][] xyPoints(Spot endSpot) {

        List<Float> points = new ArrayList<>();

        endSpot = endSpot.getPrevious();

        while (endSpot != null) {
            points.add((float)endSpot.getY()/PIXELS);
            points.add((float)endSpot.getX()/PIXELS);
            endSpot = endSpot.getPrevious();
        }

        float[][] xyPoints = new float[points.size()/2][2];

        for(int i = 0; i<points.size(); i++){
            if(i%2==0) {
                xyPoints[i/2][0] = points.get(i);
            } else{
                xyPoints[i/2][1] = points.get(i);
            }
        }

        // returns float[] of the form [[x1,y1],[x2,y2],[x3,y3],..]
        return xyPoints;
    }


    public float[][] createLines(float[][] f) {

        float[][] f2 = new float[(f.length-1)*2][2];

        for(int i = 1; i<f2.length;i++){
            int rep = 2;

            if(i/rep == 0){
                f2[i/rep][0] = f[i][0];
                f2[i/rep][1] = f[i][1];
            } else if ((i+3)/rep == f.length){
                f2[i][0] = f[(i+1)/rep][0];
                f2[i][1] = f[(i+1)/rep][1];
            } else {
                f2[i-1][0] = f[i/ rep][0];
                f2[i-1][1] = f[i/ rep][1];
                f2[i][0] = f[i/ rep][0];
                f2[i][1] = f[i/ rep][1];
            }

        }

        return f2;

    }

    public float[] expandToSingleLayer(float[][] df){
        float[] f = new float[df.length*2];
        for(int i = 0; i<df.length; i=i+2){
            for(int j = 0; j<2; j++){
                f[i+j] = df[i][j];
            }

        }
        return f;
    }

    public Bitmap drawLinesToBitmap(Context context, int gResId, float[] f) {
        Resources resources = context.getResources();
        float scale = resources.getDisplayMetrics().density;
        Bitmap bitmap =
                BitmapFactory.decodeResource(resources, gResId);

        android.graphics.Bitmap.Config bitmapConfig =
                bitmap.getConfig();
        // set default bitmap config if none
        if(bitmapConfig == null) {
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

        for(int i = 0; i<f.length; i++){
            if(i%2==0){
                f[i] = f[i]*w;
            } else {
                f[i] = f[i]*h;
            }
        }


        canvas.drawLines(f,paint);


        return bitmap;
    }


    public void drawIndoorPath(IndoorBuildingOverlays indoorBuildingOverlays, Context context, Spot endSpot) {

        /**
         * create for loop on endSpot which could be an array/list
         * to draw on all the floors
         */

        float[][] f = xyPoints(endSpot);
        float[][] linesC = createLines(f);
        float[] lines = expandToSingleLayer(linesC);

        // for now harcoded, but has to be determined dynamically for different buildings and floors
        // don't forget to check if building == null

        //this selects the building and floor overlay
        //theres 2 parts to it building Code and floor Number
        String building = "h8";

        int rId = context.getResources().getIdentifier(building, "drawable", context.getPackageName());
        Bitmap floorWithPath = drawLinesToBitmap(context, rId, lines);

        // 3 (index) is where the overlay will be drawn given the original image array
        indoorBuildingOverlays.changeOverlay(3, floorWithPath, IndoorBuildingOverlays.BuildingCodes.H);

    }

}
