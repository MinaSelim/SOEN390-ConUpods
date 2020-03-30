package com.conupods.OutdoorMaps.Services;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SearchView;

import com.conupods.OutdoorMaps.View.SearchView.AbstractCampusLocationAdapter;
import com.conupods.R;
import com.google.android.gms.maps.model.LatLng;

public class ActivityComponentBuilder {
    private LinearLayout mSearchDirectionsOptions;

    public SearchView initializeSearchBarWithFocus(SearchView searchBar, Context context, Activity activity, AbstractCampusLocationAdapter abstractCampusLocationAdapter) {
        searchBar.setQueryHint("Where To?");
        searchBar.setFocusable(true);
        searchBar.setIconified(false);
        searchBar.requestFocusFromTouch();
        searchBar.setMaxWidth(Integer.MAX_VALUE);

        // listening to search query text change
        searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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

        return searchBar;
    }

    // Add the destination information to the intent
    public void initializeGetDirectionsButton(Activity actvity, Button btn, Intent intent, LatLng coordinates, String longName, String code) {
        btn = actvity.findViewById(R.id.get_directions_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent.putExtra("toCoordinates", coordinates);
                intent.putExtra("toLongName", longName);
                intent.putExtra("toCode", code);

                actvity.startActivity(intent);
            }
        });
    }
}
