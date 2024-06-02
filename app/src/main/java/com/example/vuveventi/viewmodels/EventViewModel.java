package com.example.vuveventi.viewmodels;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.vuveventi.models.Eventi;
import com.example.vuveventi.network.ApiClient;
import com.example.vuveventi.network.ApiService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventViewModel extends ViewModel {

    private MutableLiveData<List<Eventi>> events;
    private static final String TAG = "EventViewModel";

    public EventViewModel() {
        events = new MutableLiveData<>();
        loadEvents();
    }

    public LiveData<List<Eventi>> getEvents() {
        return events;
    }

    private void loadEvents() {
        ApiService apiService = ApiClient.getRetrofitInstance().create(ApiService.class);
        Call<List<Eventi>> call = apiService.getEvents();
        call.enqueue(new Callback<List<Eventi>>() {
            @Override
            public void onResponse(Call<List<Eventi>> call, Response<List<Eventi>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d(TAG, "Events retrieved successfully: " + response.body());
                    events.setValue(response.body());
                } else {
                    Log.d(TAG, "Failed to retrieve events. Response code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Eventi>> call, Throwable t) {
                Log.e(TAG, "Failed to retrieve events", t);
            }
        });
    }
}
