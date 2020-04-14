package com.conupods.OutdoorMaps.View.PointsOfInterest;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.conupods.OutdoorMaps.CameraController;
import com.conupods.OutdoorMaps.Models.PointsOfInterest.Place;
import com.conupods.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;

public class SliderAdapter extends PagerAdapter {

    private List<Place> mPlacesOfInterest;
    private LayoutInflater mLayoutInflater;
    private Context mContext;

    public SliderAdapter(Context context, List<Place> places) {
        mPlacesOfInterest = places;
        Log.d("SliderAdapter mPLacesOFInterest", ""+mPlacesOfInterest );
        mContext = context;
    }

    @Override
    public int getCount() {
        Log.d("SliderAdapter mPLacesOFInterest count", ""+mPlacesOfInterest );
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
        ImageView placePhoto;

        placeName = view.findViewById(R.id.poiDescription);
        placePhoto = view.findViewById(R.id.sliderImageBTN);

        placeName.setText(mPlacesOfInterest.get(position).getName());

        Log.d("SliderAdapter","REQUEST URLS FOR PHOTO: "+mPlacesOfInterest.get(position).getPhotRequestURL());

            Glide
                .with(view)
                .load((mPlacesOfInterest.get(position).getPhotRequestURL()))

                .into(new CustomTarget<Drawable>(100,100) {

                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition)
                    {

                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder)
                    {
                    }
                });
            
        //placePhoto.setImageResource();
        placePhoto.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

            }
        });
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
