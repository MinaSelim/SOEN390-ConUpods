package com.conupods.OutdoorMaps.View.Settings;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.conupods.MapsActivity;
import com.conupods.R;

/**
 * @author felix
 */
public class SettingsInfoActivity extends AppCompatActivity {

    /**
     * This method is to initiate the info settings view and its components
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_page_info);
        Uri url = Uri.parse("https://github.com/MinaSelim/SOEN390/wiki");
        //Top Menu buttons
        Button done = findViewById(R.id.done3);
        Button personalPage = findViewById(R.id.toggle3_1);
        Button defaultPage = findViewById(R.id.toggle3_2);
        Button info = findViewById(R.id.help);
        Button contactUs = findViewById(R.id.contactUs);
        Button terms = findViewById(R.id.termsAndConditions);
        done.setOnClickListener(view -> startActivityIfNeeded(new Intent(SettingsInfoActivity.this, MapsActivity.class).setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT), 0));
        personalPage.setOnClickListener(view -> startActivityIfNeeded(new Intent(SettingsInfoActivity.this, SettingsPersonalActivity.class).setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT), 0));
        defaultPage.setOnClickListener(view -> startActivityIfNeeded(new Intent(SettingsInfoActivity.this, SettingsActivity.class).setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT), 0));
        info.setOnClickListener(view -> startActivityIfNeeded(new Intent(Intent.ACTION_VIEW, url).setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT), 0));
        contactUs.setOnClickListener(view -> startActivityIfNeeded(new Intent(Intent.ACTION_VIEW, url).setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT), 0));
        terms.setOnClickListener(view -> startActivityIfNeeded(new Intent(Intent.ACTION_VIEW, url).setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT), 0));
    }
}
