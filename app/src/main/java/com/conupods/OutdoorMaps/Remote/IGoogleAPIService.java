package com.conupods.OutdoorMaps.Remote;

import com.conupods.OutdoorMaps.Models.PointsOfInterest.PlacesOfInterest;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface IGoogleAPIService {

    @GET
    Call<PlacesOfInterest> getNearbyPlaces(@Url String url);
}
