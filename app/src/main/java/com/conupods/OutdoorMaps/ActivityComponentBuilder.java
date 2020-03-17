package com.conupods.OutdoorMaps;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SearchView;

import com.conupods.OutdoorMaps.View.MapsActivity;
import com.conupods.OutdoorMaps.View.SearchActivity;
import com.conupods.R;

import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;

import static androidx.core.content.ContextCompat.getSystemService;

public class ActivityComponentBuilder {
    private SearchView mSearchBar;
    private LinearLayout mSarchDirectionsOptions;

    public SearchView initializeSearchBar(SearchView searchBar, Context context) {
        this.mSearchBar = new SearchView(context);
        this.mSearchBar.setQueryHint("Where To?");
        return mSearchBar;

    }

    public void initializeSearchDirectionCards(Context context){
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        RelativeLayout directionsCardsLayout = (RelativeLayout) layoutInflater.inflate(R.layout.activity_search, null);


    }



}
