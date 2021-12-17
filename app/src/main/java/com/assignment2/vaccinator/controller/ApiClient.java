package com.assignment2.vaccinator.controller;

import com.assignment2.vaccinator.api.ApiSet;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * This class is used as a client to COWIN REST service to fetch data.
 */
public class ApiClient {

    //COWIN BASE URL
    private static final String COWIN_URL = "https://api.cowin.gov.in/api/v1/reports/v2/";

    private static volatile ApiClient apiClient;

    //Retrofit instance
    private static Retrofit retrofit;

    /**
     *  Constructor to initialise the retrofit instance with base url
     *  Also, GSON converter factory instance is used to convert JSON response to object.
     */
    ApiClient() {
        retrofit = new Retrofit.Builder()
                .baseUrl(COWIN_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    /**
     * This will return ApiClient instance
     * We have used Singleton pattern.
     * Single instance of ApiClient is created and used across application.
     */
    public static synchronized ApiClient getApiClient() {
        if (apiClient == null) {
            synchronized (ApiClient.class) {
                if (apiClient == null) {
                    apiClient = new ApiClient();
                }
            }
        }
        return apiClient;
    }

    /**
     * This Method will return APISet, used to call the various api endpoints.
     */
    public ApiSet getApi() {
       return retrofit.create(ApiSet.class);
    }
}