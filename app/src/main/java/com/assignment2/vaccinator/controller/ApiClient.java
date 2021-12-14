package com.assignment2.vaccinator.controller;

import com.assignment2.vaccinator.ApiSet;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private static final String COWIN_URL = "https://api.cowin.gov.in/api/v1/reports/v2/";

    private static volatile ApiClient apiClient;

    private static Retrofit retrofit;

    ApiClient() {
        retrofit = new Retrofit.Builder()
                .baseUrl(COWIN_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

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

    public ApiSet getApi() {
       return retrofit.create(ApiSet.class);
    }
}