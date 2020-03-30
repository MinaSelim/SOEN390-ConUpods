



package com.conupods.OutdoorMaps.View.SearchSetupView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.SearchView;
import android.widget.TextView;

import com.conupods.OutdoorMaps.Models.Building.AbstractCampusLocation;
import com.conupods.OutdoorMaps.Services.ActivityComponentBuilder;
import com.conupods.OutdoorMaps.Services.CampusAbstractLocationCreationService;
import com.conupods.OutdoorMaps.View.Directions.ModeSelectActivity;
import com.conupods.OutdoorMaps.View.SearchView.AbstractCampusLocationAdapter;
import com.conupods.OutdoorMaps.View.SearchView.CampusLocationsAdapterListener;
import com.conupods.R;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

public class FinalizeSearchActivity extends AppCompatActivity implements CampusLocationsAdapterListener {

    private AbstractCampusLocationAdapter mAdapter;
    private SearchView mFromSearchBar;
    private Intent mModeSelectIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finalize_search);

        // Populate recycler view
        List<AbstractCampusLocation> mCampusLocationList = new ArrayList<>();
        mAdapter = new AbstractCampusLocationAdapter(mCampusLocationList, this);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        // Create view elements with decoupled logic
        initializeComponents();

        CampusAbstractLocationCreationService campusLocationCreationService = new CampusAbstractLocationCreationService(mCampusLocationList, mAdapter);
        campusLocationCreationService.prepareCampusLocationsForSearch();

        // Set origin text
        mFromSearchBar = findViewById(R.id.fromSearchBar);
        mFromSearchBar.setQueryHint("Current Location");

        // Set destination text
        Intent passedIntent = getIntent();

        String destinationDescription = passedIntent.getStringExtra("toLongName");

        if (destinationDescription == null) {
            destinationDescription = passedIntent.getStringExtra("toCode");

            TextView destinationText = findViewById(R.id.toTextArea);
            destinationText.setText("To: " + destinationDescription);
        }

        TextView destinationText = findViewById(R.id.toTextArea);
        destinationText.setText("To: " + destinationDescription);

        // Add destination information to the mode select intent
        mModeSelectIntent = new Intent(this, ModeSelectActivity.class);

        String toLongName = passedIntent.getStringExtra("toLongName");
        String toCode = passedIntent.getStringExtra("toCode");
        LatLng toCoordinates = passedIntent.getParcelableExtra("toCoordinates");

        if (toLongName == null) {
            mModeSelectIntent.putExtra("toLongName", toCode);
            mModeSelectIntent.putExtra("toCode", toCode);
            mModeSelectIntent.putExtra("toCoordinates", toCoordinates);
        }
        mModeSelectIntent.putExtra("toLongName", toLongName);
        mModeSelectIntent.putExtra("toCode", toCode);
        mModeSelectIntent.putExtra("toCoordinates", toCoordinates);
    }

    // Uses external service to create the view elements so that this activity only includes relevant code
    private void initializeComponents() {
        ActivityComponentBuilder componentBuilder = new ActivityComponentBuilder();

        mFromSearchBar = componentBuilder.initializeSearchBarWithFocus(findViewById(R.id.fromSearchBar), this, this, mAdapter);
    }

    @Override
    public void onCampusLocationSelected(AbstractCampusLocation abstractCampusLocation) {
        if (abstractCampusLocation.getmLongIdentifier() != null) {
            mFromSearchBar.setQuery(abstractCampusLocation.getmLongIdentifier(), false);
        } else {
            mFromSearchBar.setQuery(abstractCampusLocation.getIdentifier(), false);
        }

        mFromSearchBar.clearFocus();

        if(abstractCampusLocation.getmLongIdentifier() == null ) {
            mModeSelectIntent.putExtra("fromCoordinates", abstractCampusLocation.getCoordinates());
            mModeSelectIntent.putExtra("fromLongName", abstractCampusLocation.getIdentifier());
            mModeSelectIntent.putExtra("fromCode", abstractCampusLocation.getIdentifier());
            startActivity(mModeSelectIntent);
        }
        else {
            mModeSelectIntent.putExtra("fromCoordinates", abstractCampusLocation.getCoordinates());
            mModeSelectIntent.putExtra("fromLongName", abstractCampusLocation.getmLongIdentifier());
            mModeSelectIntent.putExtra("fromCode", abstractCampusLocation.getIdentifier());
            startActivity(mModeSelectIntent);
        }

    }
}
