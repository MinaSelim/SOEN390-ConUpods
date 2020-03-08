package com.conupods.IndoorMaps.View;

import android.os.Bundle;

import android.content.res.Resources;
import android.os.AsyncTask;
import android.util.Log;

import com.conupods.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.CameraUpdateFactory;



import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.graphics.Color;

import android.view.View;

import com.conupods.R;

public class PathOverlay {

    private GoogleMap mMap;

    LatLng startLatLng = new LatLng(45.49716910019739, -73.57957541942595);
    LatLng endLatLng = new LatLng(45.4966727569776,-73.57856422662735);
    public PathOverlay(GoogleMap map)
    {
        mMap = map;
    }

    public void createPolyLine(){
        Polyline line = mMap.addPolyline(new PolylineOptions()
                .add(startLatLng)
                .add(endLatLng)
                .width(5)
                .color(Color.RED));

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(endLatLng,20));

    }
    protected void onPostExecute(){
        createPolyLine();
    }
}
