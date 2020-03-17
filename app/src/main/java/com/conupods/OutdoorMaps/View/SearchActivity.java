package com.conupods.OutdoorMaps.View;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.conupods.OutdoorMaps.ActivityComponentBuilder;
import com.conupods.R;

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initializeComponents();
    }

    private void initializeComponents() {
        ActivityComponentBuilder componentBuilder = new ActivityComponentBuilder();
        componentBuilder.initializeSearchBar(findViewById(R.id.searchBar), this);
        componentBuilder.initializeSearchDirectionCards(this);

    }
}

