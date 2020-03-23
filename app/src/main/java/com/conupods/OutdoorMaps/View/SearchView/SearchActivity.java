package com.conupods.OutdoorMaps.View.SearchView;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.Toast;

import com.conupods.OutdoorMaps.Services.ActivityComponentBuilder;
import com.conupods.OutdoorMaps.Models.Buildings.AbstractCampusLocation;
import com.conupods.OutdoorMaps.Services.CampusAbstractLocationCreationService;
import com.conupods.R;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends FragmentActivity implements CampusLocationsAdapterListener{

    private final String TAG = "SeacrhcActivity";
    private List<AbstractCampusLocation> mCampusLocationList;
    private RecyclerView recyclerView;
    private AbstractCampusLocationAdapter mAdapter;
    private SearchView mSearchBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        mCampusLocationList = new ArrayList<>();
        mAdapter = new AbstractCampusLocationAdapter(this, mCampusLocationList, this);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        initializeComponents();

        CampusAbstractLocationCreationService campusLocationCreationService = new CampusAbstractLocationCreationService(mCampusLocationList, mAdapter);
        campusLocationCreationService.prepareCampusLocationsForSearch();
    }

    private void initializeComponents() {
        ActivityComponentBuilder componentBuilder = new ActivityComponentBuilder();
        mSearchBar = componentBuilder.initializeSearchBarWithFocus(findViewById(R.id.searchBar), this, this, mAdapter);

        LinearLayout searchDirectioButtons = (LinearLayout) findViewById(R.id.SearchDirectionsOptions);
        searchDirectioButtons.setFocusable(true);
        searchDirectioButtons.setFocusableInTouchMode(true);

    }

    @Override
    public void onContactSelected(AbstractCampusLocation abstractCampusLocation) {
        mSearchBar.setQuery(abstractCampusLocation.getIdentifier(), false);
        mSearchBar.clearFocus();

    }

    @Override
    public void onBackPressed() {
        // close search view on back button pressed
        if (!mSearchBar.isIconified()) {
            mSearchBar.setIconified(true);

            mSearchBar.clearFocus();
            return;
        }

        super.onBackPressed();
    }



}

