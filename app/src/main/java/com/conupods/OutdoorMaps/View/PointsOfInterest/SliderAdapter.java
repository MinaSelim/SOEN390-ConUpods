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
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.conupods.MainActivity;
import com.conupods.OutdoorMaps.CameraController;
import com.conupods.OutdoorMaps.Models.PointsOfInterest.Place;
import com.conupods.R;
import com.squareup.picasso.Picasso;

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

           /* Glide
                .with(view)
                .load((mPlacesOfInterest.get(position).getPhotRequestURL()))
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            Log.d("SliderAdapter", "We failed to load the ressource damn");
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            return false;
                        }
                    })
                .into(placePhoto);*/
           Log.d("SliderAdapter", "PHOT IURL RIGHT BEFORE MAKING REQUEST: "+mPlacesOfInterest.get(position).getPhotRequestURL());
        Picasso
                .get()
                .load(mPlacesOfInterest.get(position).getPhotRequestURL())
                .noFade()
                .into(placePhoto);


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
