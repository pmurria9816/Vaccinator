package com.assignment2.vaccinator.ui.dashboard;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.assignment2.vaccinator.controller.ApiClient;
import com.assignment2.vaccinator.models.CowinResponse;
import com.assignment2.vaccinator.models.TopBlock;

import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * This class is used as a view model for dashboard fragment.
 */
public class DashboardViewModel extends ViewModel {

    private MutableLiveData<TopBlock> liveCowinResponse;

    //Init and fetch data from cowin apis.
    public DashboardViewModel() {
        liveCowinResponse = new MutableLiveData<>();
        fetchCowinResponse();
    }

    //Getter method
    public LiveData<TopBlock> getCowinResponse() {
        return liveCowinResponse;
    }

    /**
     * This method is used to call the api client and get data from cowin api.
     */
    private void fetchCowinResponse() {

        //Get fromatted current date.
        String currentDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

        //Calling Cowin API.
        Call<CowinResponse> response = ApiClient.getApiClient().getApi().getCowinData(currentDate);

        //Callback method. Once the API sends the response, this method is called.
        // This is Async call. Main thread is not blocked.
        response.enqueue(new Callback<CowinResponse>() {
            @Override
            public void onResponse(Call<CowinResponse> call, Response<CowinResponse> response) {
                //set the response
                liveCowinResponse.setValue(response.body().getTopBlock());
            }

            //If call fails log the exception.
            @Override
            public void onFailure(Call<CowinResponse> call, Throwable t) {
                Log.e("Error while fetching data", t.getMessage());
            }
        });
    }
}