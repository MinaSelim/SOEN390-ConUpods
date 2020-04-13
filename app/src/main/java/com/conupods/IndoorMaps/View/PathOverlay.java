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

//        endSpot = dummySpot();

        float[][] f = xyPoints(endSpot);

        /* OLD CODE TO DELETE
        List<LatLng> points = new ArrayList<LatLng>();
        while (endSpot != null) {

            //converting the provided coordinates too their latitude
            // and longitude and then adding them to an array
            //
            //We'll have to think about a template method
            int angle = 0;

            switch (endSpot.getBuilding()) {
                case "H":
                    /*
                    points.add(
                            new LatLng(
                                    BUILDINGS.SEHALL.latitude + (BUILDINGS.NWHALL.latitude - BUILDINGS.SWHALL.latitude) * ((float) endSpot.getX() / PIXELS) + (BUILDINGS.SEHALL.latitude - BUILDINGS.NEHALL.latitude) * ((float) endSpot.getY() / PIXELS),
                                    BUILDINGS.SEHALL.longitude + (BUILDINGS.NWHALL.longitude - BUILDINGS.SWHALL.longitude) * ((float) endSpot.getX()/ PIXELS) + (BUILDINGS.NWHALL.longitude - BUILDINGS.NEHALL.longitude) * ((float) endSpot.getY()) / PIXELS
                            ));

                    angle = HBuilding.getBearing();
                    break;
                case "JMSB":
                    points.add(
                            new LatLng(
                                    BUILDINGS.NWJMSB.latitude - (BUILDINGS.NWJMSB.latitude - BUILDINGS.SWJMSB.latitude) * ((float) endSpot.getX() / PIXELS) - (BUILDINGS.NWJMSB.latitude - BUILDINGS.NEJMSB.latitude) * ((float) endSpot.getY() / PIXELS),
                                    BUILDINGS.NWJMSB.longitude - (BUILDINGS.NWJMSB.longitude - BUILDINGS.SWJMSB.longitude) * ((float) endSpot.getX()) / PIXELS - (BUILDINGS.NWJMSB.longitude - BUILDINGS.NEJMSB.longitude) * ((float) endSpot.getY()) / PIXELS
                            ));
                    break;
                case "CC":
                    points.add(
                            new LatLng(
                                    BUILDINGS.NWCC.latitude - (BUILDINGS.NWCC.latitude - BUILDINGS.SWCC.latitude) * ((float) endSpot.getX() / PIXELS) - (BUILDINGS.NWCC.latitude - BUILDINGS.NECC.latitude) * ((float) endSpot.getY() / PIXELS),
                                    BUILDINGS.NWCC.longitude - (BUILDINGS.NWCC.longitude - BUILDINGS.SWCC.longitude) * ((float) endSpot.getX()) / PIXELS - (BUILDINGS.NWCC.longitude - BUILDINGS.NECC.longitude) * ((float) endSpot.getY()) / PIXELS
                            ));
                    break;
                case "VE":
                    points.add(
                            new LatLng(
                                    BUILDINGS.NWVE.latitude - (BUILDINGS.NWVE.latitude - BUILDINGS.SWVE.latitude) * ((float) endSpot.getX() / PIXELS) - (BUILDINGS.NWVE.latitude - BUILDINGS.NEVE.latitude) * ((float) endSpot.getY() / PIXELS),
                                    BUILDINGS.NWVE.longitude - (BUILDINGS.NWVE.longitude - BUILDINGS.SWVE.longitude) * ((float) endSpot.getX()) / PIXELS - (BUILDINGS.NWVE.longitude - BUILDINGS.NEVE.longitude) * ((float) endSpot.getY()) / PIXELS
                            ));
                    break;
                case "VL":
                    points.add(
                            new LatLng(
                                    BUILDINGS.NWVL.latitude - (BUILDINGS.NWVL.latitude - BUILDINGS.SWVL.latitude) * ((float) endSpot.getX() / PIXELS) - (BUILDINGS.NWVL.latitude - BUILDINGS.NEVL.latitude) * ((float) endSpot.getY() / PIXELS),
                                    BUILDINGS.NWVL.longitude - (BUILDINGS.NWVL.longitude - BUILDINGS.SWVL.longitude) * ((float) endSpot.getX()) / PIXELS - (BUILDINGS.NWVL.longitude - BUILDINGS.NEVL.longitude) * ((float) endSpot.getY()) / PIXELS
                            ));
                default:
                    // TO DO
                    break;
            }


            double[] pointstf = MatrixOperation.rotate(angle, endSpot.getX() * 80f, endSpot.getY() * 80f);


            points.add(new LatLng(pointstf[0] + hlat, pointstf[1] + hlon));


            if (endSpot.getPrevious() != null) {
                endSpot.getPrevious().setBuilding(endSpot.getBuilding());
            }

            endSpot = endSpot.getPrevious();
        }

         */

        float[][] linesC = createLines(f);
        float[] lines = expandToSingleLayer(linesC);
//        float[] lines = {0f,0f,1024f,1024f};


        // for now harcoded, but has to be determined dynamically for different buildings and floors
        String building = "h9";

        int rId = context.getResources().getIdentifier(building, "drawable", context.getPackageName());

        Bitmap floorWithPath = drawLinesToBitmap(context,rId, lines);


        indoorBuildingOverlays.changeOverlay(3, floorWithPath, IndoorBuildingOverlays.BuildingCodes.H);

    }

}
