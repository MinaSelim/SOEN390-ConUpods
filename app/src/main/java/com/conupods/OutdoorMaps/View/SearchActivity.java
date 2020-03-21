package com.conupods.OutdoorMaps.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SearchView;

import com.conupods.OutdoorMaps.ActivityComponentBuilder;
import com.conupods.R;
import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class SearchActivity extends FragmentActivity {

    private final String TAG = "SeacrhcActivity";
    int AUTOCOMPLETE_REQUEST_CODE = 1;


    private String mDestination;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), getString(R.string.Google_API_Key), Locale.US);
        }
        initializeComponents();
    }

    private void initializeComponents() {
        ActivityComponentBuilder componentBuilder = new ActivityComponentBuilder();

        SearchView searchBar = componentBuilder.initializeSearchBarWithFocus(findViewById(R.id.searchBar), this, this);
        componentBuilder.initilializeAutocompleteSearchBar(this, this, AUTOCOMPLETE_REQUEST_CODE);
    }

    @Override
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


}

