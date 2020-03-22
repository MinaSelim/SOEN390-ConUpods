package com.conupods.OutdoorMaps.Services;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

import com.conupods.OutdoorMaps.View.SearchView.AbstractCampusLocationAdapter;

import java.io.IOException;
import java.util.List;

public class SearchService {

    AbstractCampusLocationAdapter mAdapter;

    /**private final int mMAX_NUMBER_OF_RESULTS = 4;
    private Geocoder mGeocoder = null;


     private List<Address> addressList = null;

    public List<Address> getListOfAddresses(String locationQuery, Context context ){

        mGeocoder = new Geocoder(context);
        if(locationQuery != null && locationQuery != ""){

            try{
                addressList = mGeocoder.getFromLocationName(locationQuery, mMAX_NUMBER_OF_RESULTS);
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }

        return addressList;
    } */
}
