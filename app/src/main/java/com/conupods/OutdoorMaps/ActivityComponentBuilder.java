package com.conupods.OutdoorMaps;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.SearchView;

import com.conupods.R;

import static androidx.core.content.ContextCompat.getSystemService;

public class ActivityComponentBuilder {
    private SearchView mSearchBar;
    private LinearLayout mSarchDirectionsOptions;

    public void initializeSearchBar(SearchView searchBar, Context context) {
        this.mSearchBar = new SearchView(context);
        this.mSearchBar.setQueryHint("Where To?");

    }

    public void initializeSearchDirectionCards(Context context){
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout directionsCardsLayout = (LinearLayout) layoutInflater.inflate(R.layout.activity_search, null);


    }

}
