package com.example.vuveventi.network;

import com.example.vuveventi.models.Aktivnosti;
import com.example.vuveventi.models.Eventi;
import com.example.vuveventi.models.KorisniciAktivnostiDomain;
import com.example.vuveventi.models.KorisniciDomain;
import com.example.vuveventi.viewmodels.LoginViewModel.LoginRequest;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {
    @GET("Eventi")
    Call<List<Eventi>> getEvents();

    @GET("Eventi/{id}")
    Call<Eventi> getEventById(@Path("id") int id);

    @GET("Aktivnosti")
    Call<List<Aktivnosti>> getAllActivities();

    @POST("Korisnici/login")
    Call<KorisniciDomain> login(@Body LoginRequest loginRequest);

    @POST("KorisniciAktivnosti")
    Call<Void> signUpUser(@Body KorisniciAktivnostiDomain signUpDetails);
}
