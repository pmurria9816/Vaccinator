package com.assignment2.vaccinator.ui.dashboard;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.assignment2.vaccinator.controller.ApiClient;
import com.assignment2.vaccinator.models.CowinResponse;
import com.assignment2.vaccinator.models.TopBlock;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardViewModel extends ViewModel {

    private MutableLiveData<TopBlock> liveCowinResponse;

    public DashboardViewModel() {
        liveCowinResponse = new MutableLiveData<>();
        fetchCowinResponse();
    }

    public LiveData<TopBlock> getCowinResponse() {
        return liveCowinResponse;
    }

    private void fetchCowinResponse() {

        String currentDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        Call<CowinResponse> response = ApiClient.getApiClient().getApi().getCowinData(currentDate);

        response.enqueue(new Callback<CowinResponse>() {
            @Override
            public void onResponse(Call<CowinResponse> call, Response<CowinResponse> response) {
                liveCowinResponse.setValue(response.body().getTopBlock());
            }

            @Override
            public void onFailure(Call<CowinResponse> call, Throwable t) {

            }
        });
    }
}