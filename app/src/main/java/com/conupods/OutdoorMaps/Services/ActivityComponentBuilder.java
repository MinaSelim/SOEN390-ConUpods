package com.conupods.OutdoorMaps.Services;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SearchView;

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


    public SearchView initializeSearchBarWithFocus(SearchView searchBar, Context context, Activity activity) {
        mSearchBar = searchBar;
        mSearchBar.setQueryHint("Where To?");
        mSearchBar.setFocusable(true);
        mSearchBar.setIconified(false);
        mSearchBar.requestFocusFromTouch();

      /*  mSearchBar.setOnQueryTextListener(new OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                String locationQuery = searchBar.getQuery().toString();
                List<Address> listOfAddresses = null;
                SearchService searchService = new SearchService();

                listOfAddresses = searchService.getListOfAddresses(locationQuery, context);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });*/

        mSearchBar.setOnQueryTextListener(new OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String s) {
                    initilializeAutocompleteSearchBar(activity, context, 1);
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    return false;
                }
        });

        return mSearchBar;
    }

    public void initilializeAutocompleteSearchBar(Activity  activity, Context context, int AUTOCOMPLETE_REQUEST_CODE){
        List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME);

        // Start the autocomplete intent.
        Intent intent = new Autocomplete.IntentBuilder(
                AutocompleteActivityMode.OVERLAY, fields)
                .build(context);
        activity.startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE);
    }


    public void initializeSearchDirectionCards(Context context){
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        RelativeLayout directionsCardsLayout = (RelativeLayout) layoutInflater.inflate(R.layout.activity_search, null);
    }






}
