package com.conupods.OutdoorMaps.View.PointsOfInterest;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.conupods.OutdoorMaps.Models.PointsOfInterest.Place;
import com.conupods.OutdoorMaps.View.Directions.ModeSelectActivity;
import com.conupods.R;
import com.google.android.gms.maps.model.LatLng;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

public class SliderAdapter extends PagerAdapter {

    private List<Place> mPlacesOfInterest;
    private LayoutInflater mLayoutInflater;
    private Context mContext;

    public SliderAdapter(Context context, List<Place> places) {
        mPlacesOfInterest = places;
        Log.d("SliderAdapter mPLacesOFInterest", "" + mPlacesOfInterest);
        mContext = context;
    }

    @Override
    public int getCount() {
        Log.d("SliderAdapter mPLacesOFInterest count", "" + mPlacesOfInterest);
        return mPlacesOfInterest.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        mLayoutInflater = LayoutInflater.from(mContext);
        View view = mLayoutInflater.inflate(R.layout.slider_item, container, false);

        TextView placeName;
        ImageButton placePhoto;
        LatLng placeCoordinates;

        placeName = view.findViewById(R.id.poiDescription);
        placePhoto = view.findViewById(R.id.sliderImageBTN);

        Double lat = Double.parseDouble(mPlacesOfInterest.get(position).getGeometry().getLocation().getLat());
        Double lng = Double.parseDouble(mPlacesOfInterest.get(position).getGeometry().getLocation().getLng());

        placeCoordinates = new LatLng(lat, lng);

        placePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("PLACE CLICKED", "PLACE CLICKED COORDINATES: " + placeCoordinates);
                Intent modeSelectIntent = new Intent(mContext, ModeSelectActivity.class);
                modeSelectIntent.putExtra("fromLongName", "Current Location");
                modeSelectIntent.putExtra("toCoordinates", placeCoordinates);
                modeSelectIntent.putExtra("toLongName", placeName.getText().toString());
                mContext.startActivity(modeSelectIntent);

            }
        });
        placeName.setText(mPlacesOfInterest.get(position).getName());

        Picasso
                .get()
                .load(mPlacesOfInterest.get(position).getPhotRequestURL())
                .placeholder(mContext.getDrawable(R.drawable.default_image_loader))
                .noFade()
                .fit()
                .into(placePhoto);


        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

}
