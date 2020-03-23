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
    int AUTOCOMPLETE_REQUEST_CODE = 1;

    private String mDestination;

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
        Toast.makeText(getApplicationContext(), "Selected: " + abstractCampusLocation.getIdentifier() , Toast.LENGTH_LONG).show();
        mSearchBar.setQuery(abstractCampusLocation.getIdentifier(), false);
        mSearchBar.clearFocus();

    }

    @Override
    public void onBackPressed() {
        // close search view on back button pressed
        if (!mSearchBar.isIconified()) {
            mSearchBar.setIconified(true);
            return;
        }

        mSearchBar.clearFocus();
        super.onBackPressed();
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

