package com.conupods.OutdoorMaps.Services;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SearchView;

import com.conupods.OutdoorMaps.View.SearchView.AbstractCampusLocationAdapter;
import com.conupods.R;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import android.content.Intent;
import android.widget.SearchView.OnQueryTextListener;

import java.util.Arrays;
import java.util.List;

public class ActivityComponentBuilder {
    private SearchView mSearchBar;
    private LinearLayout mSarchDirectionsOptions;


    public SearchView initializeSearchBarWithFocus(SearchView searchBar, Context context, Activity activity, AbstractCampusLocationAdapter abstractCampusLocationAdapter) {
        mSearchBar = searchBar;
        mSearchBar.setQueryHint("Where To?");
        mSearchBar.setFocusable(true);
        mSearchBar.setIconified(false);
        mSearchBar.requestFocusFromTouch();

        mSearchBar.setMaxWidth(Integer.MAX_VALUE);

        // listening to search query text change
        mSearchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // filter recycler view when query submitted
                abstractCampusLocationAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                // filter recycler view when text is changed
                abstractCampusLocationAdapter.getFilter().filter(query);
                return false;
            }
        });


        return mSearchBar;
    }






}
