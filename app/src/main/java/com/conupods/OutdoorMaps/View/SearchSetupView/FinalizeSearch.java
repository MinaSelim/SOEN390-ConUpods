package com.conupods.OutdoorMaps.View.SearchSetupView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;

import com.conupods.OutdoorMaps.Models.Building.AbstractCampusLocation;
import com.conupods.OutdoorMaps.Services.ActivityComponentBuilder;
import com.conupods.OutdoorMaps.Services.CampusAbstractLocationCreationService;
import com.conupods.OutdoorMaps.View.SearchView.AbstractCampusLocationAdapter;
import com.conupods.OutdoorMaps.View.SearchView.CampusLocationsAdapterListener;
import com.conupods.R;

import java.util.ArrayList;
import java.util.List;

public class FinalizeSearch extends AppCompatActivity implements CampusLocationsAdapterListener {

    AbstractCampusLocationAdapter mAdapter;

    SearchView mFromSearchBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finalize_search);

        List<AbstractCampusLocation> mCampusLocationList = new ArrayList<>();
        mAdapter = new AbstractCampusLocationAdapter(mCampusLocationList, this);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        initializeComponents();

        CampusAbstractLocationCreationService campusLocationCreationService = new CampusAbstractLocationCreationService(mCampusLocationList, mAdapter);
        campusLocationCreationService.prepareCampusLocationsForSearch();

        mFromSearchBar = findViewById(R.id.fromSearchBar);
        mFromSearchBar.setQueryHint("Current Location");

        Intent passedIntent = getIntent();
        String destinationDescription = passedIntent.getStringExtra("toLongName");

        TextView destinationText = findViewById(R.id.toTextArea);
        destinationText.setText("To: " + destinationDescription);
    }

    private void initializeComponents() {
        ActivityComponentBuilder componentBuilder = new ActivityComponentBuilder();

        mFromSearchBar = componentBuilder.initializeSearchBarWithFocus(findViewById(R.id.fromSearchBar), this, this, mAdapter);

        mFromSearchBar.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {

                Log.d("FinalizedSearchView", "Query: " + keyEvent);
                return false;
            }
        });
    }


    @Override
    public void onCampusLocationSelected(AbstractCampusLocation abstractCampusLocation) {
        if(abstractCampusLocation.getmLongIdentifier() != null) {
            mFromSearchBar.setQuery(abstractCampusLocation.getmLongIdentifier(), false);
        }
        else {
            mFromSearchBar.setQuery(abstractCampusLocation.getIdentifier(), false);
        }
        mFromSearchBar.clearFocus();
    }
}
