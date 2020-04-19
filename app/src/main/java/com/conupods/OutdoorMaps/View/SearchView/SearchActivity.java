package com.conupods.OutdoorMaps.View.SearchView;

import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.transition.Fade;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;

import com.conupods.OutdoorMaps.Services.ActivityComponentBuilder;
import com.conupods.OutdoorMaps.Models.Building.AbstractCampusLocation;
import com.conupods.OutdoorMaps.Services.CampusAbstractLocationCreationService;
import com.conupods.OutdoorMaps.View.Directions.ModeSelectActivity;
import com.conupods.MapsActivity;
import com.conupods.R;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends FragmentActivity implements CampusLocationsAdapterListener {

    private static final String TAG = "SEARCH_ACTIVITY";
    private AbstractCampusLocationAdapter mAdapter;
    private SearchView mSearchBar;
    private TextView mCurrentTextQueryField;

    private AbstractCampusLocation mDestination;

    private Intent mModeSelectIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Fade fade = new Fade();
        View decor = getWindow().getDecorView();
        fade.excludeTarget(android.R.id.statusBarBackground, true);
        fade.excludeTarget(android.R.id.navigationBarBackground, true);
        getWindow().setEnterTransition(fade);
        getWindow().setExitTransition(fade);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        List<AbstractCampusLocation> mCampusLocationList = new ArrayList<>();
        mAdapter = new AbstractCampusLocationAdapter(mCampusLocationList, this);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        initializeComponents();

        CampusAbstractLocationCreationService campusLocationCreationService = new CampusAbstractLocationCreationService(mCampusLocationList, mAdapter);
        campusLocationCreationService.prepareCampusLocationsForSearch();

        // Add origin information to the intent
        Intent passedIntent = getIntent();
        mModeSelectIntent = new Intent(this, ModeSelectActivity.class);
        if (passedIntent.hasExtra("fromLongName")) {
            String fromLongName = passedIntent.getStringExtra("fromLongName");
            String fromCode = passedIntent.getStringExtra("fromCode");
            LatLng fromCoordinates = passedIntent.getParcelableExtra("fromCoordinates");

            mModeSelectIntent.putExtra("fromLongName", fromLongName);
            mModeSelectIntent.putExtra("fromCode", fromCode);
            mModeSelectIntent.putExtra("fromCoordinates", fromCoordinates);

        } else {
            mModeSelectIntent.putExtra("fromLongName", "Current Location");
        }
    }

    private void initializeComponents() {
        ActivityComponentBuilder componentBuilder = new ActivityComponentBuilder();
        mSearchBar = componentBuilder.initializeSearchBarWithFocus(findViewById(R.id.searchBar), this, this, mAdapter);
        mCurrentTextQueryField = findViewById(R.id.current_search_text);
        LinearLayout searchDirectioButtons = (LinearLayout) findViewById(R.id.SearchDirectionsOptions);
        searchDirectioButtons.setFocusable(true);
        searchDirectioButtons.setFocusableInTouchMode(true);
    }

    @Override
    public void onCampusLocationSelected(AbstractCampusLocation abstractCampusLocation) {
        if (abstractCampusLocation.getmLongIdentifier() != null) {
            mSearchBar.setQuery(abstractCampusLocation.getmLongIdentifier(), false);
            mCurrentTextQueryField.setText(abstractCampusLocation.getmLongIdentifier());

            mDestination = abstractCampusLocation;
            ActivityComponentBuilder componentBuilder = new ActivityComponentBuilder();

            // Add the destination information and launch the ModeSelect intent
            if (mDestination != null) {
                Button getDirectionsBtn = null;
                componentBuilder.initializeGetDirectionsButton(this, getDirectionsBtn, mModeSelectIntent,
                        mDestination.getCoordinates(), mDestination.getmLongIdentifier(), mDestination.getIdentifier());

                Button showLocationBtn = null;
                componentBuilder.initializeShowLocationButton(this, showLocationBtn, mModeSelectIntent,
                        mDestination.getCoordinates(), mDestination.getmLongIdentifier(), mDestination.getIdentifier());
            }
        } else {
            ActivityComponentBuilder componentBuilder = new ActivityComponentBuilder();
            mSearchBar.setQuery(abstractCampusLocation.getIdentifier(), false);
            mCurrentTextQueryField.setText(abstractCampusLocation.getIdentifier());
            mDestination = abstractCampusLocation;
            if (mDestination != null) {
                Button getDirectionsBtn = null;
                componentBuilder.initializeGetDirectionsButton(this, getDirectionsBtn, mModeSelectIntent,
                        mDestination.getCoordinates(), mDestination.getmLongIdentifier(), mDestination.getIdentifier());


                Button showLocationBtn = null;
                componentBuilder.initializeShowLocationButton(this, showLocationBtn, mModeSelectIntent,
                        mDestination.getCoordinates(), mDestination.getmLongIdentifier(), mDestination.getIdentifier());
            }

        }
        mSearchBar.clearFocus();
    }

    @Override
    public void onBackPressed() {

        Intent intent = new Intent(SearchActivity.this, MapsActivity.class);
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(SearchActivity.this, this.mSearchBar, ViewCompat.getTransitionName(this.mSearchBar));
        startActivity(intent, options.toBundle());
        finish();
    }
}

