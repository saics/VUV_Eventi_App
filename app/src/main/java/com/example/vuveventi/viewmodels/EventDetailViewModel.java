package com.example.vuveventi.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.vuveventi.models.Aktivnosti;
import com.example.vuveventi.models.Eventi;
import com.example.vuveventi.network.ApiClient;
import com.example.vuveventi.network.ApiService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventDetailViewModel extends ViewModel {

    private MutableLiveData<Eventi> event;
    private MutableLiveData<List<Aktivnosti>> activities;

    public EventDetailViewModel() {
        event = new MutableLiveData<>();
        activities = new MutableLiveData<>();
    }

    public LiveData<Eventi> getEvent(int eventId) {
        loadEvent(eventId);
        return event;
    }

    public LiveData<List<Aktivnosti>> getActivities(int eventId) {
        loadActivities(eventId);
        return activities;
    }

    private void loadEvent(int eventId) {
        ApiService apiService = ApiClient.getRetrofitInstance().create(ApiService.class);
        Call<Eventi> call = apiService.getEventById(eventId);
        call.enqueue(new Callback<Eventi>() {
            @Override
            public void onResponse(Call<Eventi> call, Response<Eventi> response) {
                if (response.isSuccessful() && response.body() != null) {
                    event.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<Eventi> call, Throwable t) {
                event.setValue(null);
            }
        });
    }

    private void loadActivities(int eventId) {
        ApiService apiService = ApiClient.getRetrofitInstance().create(ApiService.class);
        Call<List<Aktivnosti>> call = apiService.getAllActivities();
        call.enqueue(new Callback<List<Aktivnosti>>() {
            @Override
            public void onResponse(Call<List<Aktivnosti>> call, Response<List<Aktivnosti>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Aktivnosti> allActivities = response.body();
                    List<Aktivnosti> filteredActivities = new ArrayList<>();
                    for (Aktivnosti aktivnost : allActivities) {
                        if (aktivnost.getEventId() == eventId) {
                            filteredActivities.add(aktivnost);
                        }
                    }
                    activities.setValue(filteredActivities);
                }
            }

            @Override
            public void onFailure(Call<List<Aktivnosti>> call, Throwable t) {
                activities.setValue(null);
            }
        });
    }
}
