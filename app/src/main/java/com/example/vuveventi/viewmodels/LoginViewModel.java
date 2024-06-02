package com.example.vuveventi.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.vuveventi.models.KorisniciDomain;
import com.example.vuveventi.network.ApiClient;
import com.example.vuveventi.network.ApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewModel extends ViewModel {

    public LiveData<KorisniciDomain> login(String email, String lozinka) {
        MutableLiveData<KorisniciDomain> loginResult = new MutableLiveData<>();

        ApiService apiService = ApiClient.getRetrofitInstance().create(ApiService.class);
        Call<KorisniciDomain> call = apiService.login(new LoginRequest(email, lozinka));
        call.enqueue(new Callback<KorisniciDomain>() {
            @Override
            public void onResponse(Call<KorisniciDomain> call, Response<KorisniciDomain> response) {
                if (response.isSuccessful() && response.body() != null) {
                    loginResult.setValue(response.body());
                } else {
                    loginResult.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<KorisniciDomain> call, Throwable t) {
                loginResult.setValue(null);
            }
        });

        return loginResult;
    }

    public static class LoginRequest {
        private String email;
        private String lozinka;

        public LoginRequest(String email, String lozinka) {
            this.email = email;
            this.lozinka = lozinka;
        }

        public String getEmail() {
            return email;
        }

        public String getLozinka() {
            return lozinka;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public void setLozinka(String lozinka) {
            this.lozinka = lozinka;
        }
    }
}
