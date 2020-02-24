package com.conupods;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {


    private static final String TAG = "MainActivity";
    private static final int ERROOR_DIALOG_REQUEST = 9001;
    Button MapActivityButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(isGoogleAPIServiceAvailable())
            init();


    }


    private void init(){
        MapActivityButton = (Button) findViewById(R.id.MapActivityButton);
        MapActivityButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view){
                startActivity(new Intent(MainActivity.this, MapsActivity.class));

            }
        });
    }


    private boolean isGoogleAPIServiceAvailable(){
        Log.d(TAG, "Checking is Google API services are available...");
        Boolean GoogleAPIServiceAvailable = false;

        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(MainActivity.this);

        if(available == ConnectionResult.SUCCESS){
            Log.d(TAG, "isGoogleAPIServiceAvailable: Google API Services avvailable");
            GoogleAPIServiceAvailable = true;
        }
        else if(GoogleApiAvailability.getInstance().isUserResolvableError(available)){
            Log.d(TAG, "An error occured but can be resolved.");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(MainActivity.this, available, ERROOR_DIALOG_REQUEST);
            dialog.show();
        }
        else{
            Toast.makeText(this, "Cant make map requests without permissions", Toast.LENGTH_SHORT).show();
        }

        return GoogleAPIServiceAvailable;
    }



}
