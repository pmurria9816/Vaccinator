package com.assignment2.vaccinator;

import com.assignment2.vaccinator.models.CowinResponse;
import com.assignment2.vaccinator.models.TopBlock;

import java.util.Date;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiSet {

    @GET("getPublicReports")
    Call<CowinResponse> getCowinData(@Query("date") String date);
}