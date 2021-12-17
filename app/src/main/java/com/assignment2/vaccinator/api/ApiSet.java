package com.assignment2.vaccinator.api;

import com.assignment2.vaccinator.models.CowinResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * This interface is used to list the exposed API in the application.
 */
public interface ApiSet {

    @GET("getPublicReports")
    Call<CowinResponse> getCowinData(@Query("date") String date);
}