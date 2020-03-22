package com.conupods.OutdoorMaps.View.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toolbar;

import com.conupods.OutdoorMaps.ActivityComponentBuilder;
import com.conupods.OutdoorMaps.Models.Buildings.AbstractCampusLocation;
import com.conupods.OutdoorMaps.Services.CampusLocationCreationService;
import com.conupods.R;
import com.google.android.gms.common.api.Status;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class SearchActivity extends FragmentActivity {

    private final String TAG = "SeacrhcActivity";
    int AUTOCOMPLETE_REQUEST_CODE = 1;

    private String mDestination;

    private List<AbstractCampusLocation> mCampusLocationList = new ArrayList<>();
    private RecyclerView recyclerView;
    private AbstractCampusLocationAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);


        initializeComponents();


        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mAdapter = new AbstractCampusLocationAdapter(mCampusLocationList);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());

        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        CampusLocationCreationService campusLocationCreationService = new CampusLocationCreationService(mCampusLocationList, mAdapter);
        campusLocationCreationService.prepareCampusLocationsForSearch();
    }

    private void initializeComponents() {
        ActivityComponentBuilder componentBuilder = new ActivityComponentBuilder();
        SearchView searchBar = componentBuilder.initializeSearchBarWithFocus(findViewById(R.id.searchBar), this, this);
    }


   /** @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, requestCode, data);
        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = Autocomplete.getPlaceFromIntent(data);
                Log.i(TAG, "Place: " + place.getName() + ", " + place.getId());
            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                // TODO: Handle the error.
                Status status = Autocomplete.getStatusFromIntent(data);
                Log.i(TAG, status.getStatusMessage());
            } else if (resultCode == RESULT_CANCELED) {
                //DO Nothing
            }
        }
    }
    */

}

