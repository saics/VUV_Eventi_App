package com.example.vuveventi.activities;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.vuveventi.R;
import com.example.vuveventi.adapters.AktivnostAdapter;
import com.example.vuveventi.models.Aktivnosti;
import com.example.vuveventi.models.Eventi;
import com.example.vuveventi.models.KorisniciAktivnostiDomain;
import com.example.vuveventi.network.ApiClient;
import com.example.vuveventi.network.ApiService;
import com.example.vuveventi.viewmodels.EventDetailViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventDetailActivity extends AppCompatActivity {

    private static final String TAG = "EventDetailActivity";
    private ImageView eventImage;
    private TextView eventName, eventDate, eventLocation, eventMaxAttendees, eventRooms;
    private RecyclerView aktivnostiRecyclerView;
    private LinearLayout signupForm;
    private EditText userName, userEmail;
    private Button registerButton;
    private EventDetailViewModel eventDetailViewModel;
    private AktivnostAdapter aktivnostAdapter;
    private int eventId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);

        eventImage = findViewById(R.id.event_image);
        eventName = findViewById(R.id.event_name);
        eventDate = findViewById(R.id.event_date);
        eventLocation = findViewById(R.id.event_location);
        eventMaxAttendees = findViewById(R.id.event_max_attendees);
        eventRooms = findViewById(R.id.event_rooms);
        aktivnostiRecyclerView = findViewById(R.id.aktivnosti_recycler_view);
        signupForm = findViewById(R.id.signup_form);
        userName = findViewById(R.id.user_name);
        userEmail = findViewById(R.id.user_email);
        registerButton = findViewById(R.id.signup_button);

        aktivnostiRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        eventDetailViewModel = new ViewModelProvider(this).get(EventDetailViewModel.class);

        eventId = getIntent().getIntExtra("EVENT_ID", -1);
        Log.d(TAG, "Primljen EventID: " + eventId);

        eventDetailViewModel.getEvent(eventId).observe(this, new Observer<Eventi>() {
            @Override
            public void onChanged(Eventi event) {
                if (event != null) {
                    eventName.setText(event.getNaziv());
                    eventDate.setText(String.format("Datum: %s", event.getDatumPocetka()));
                    eventLocation.setText(String.format("Lokacija: %s", event.getLokacija()));
                    eventMaxAttendees.setText(String.format("Max broj posjetitelja: %d", 175)); // Example data
                    eventRooms.setText(String.format("Prostorije: %d", 2)); // Example data
                    Glide.with(EventDetailActivity.this).load(event.getImageUrl()).into(eventImage);
                    Log.d(TAG, "Naziv ucitanog eventa: " + event.getNaziv());
                } else {
                    Log.d(TAG, "Nema eventa za ID: " + eventId);
                }
            }
        });

        // Aktivnosti eventa
        eventDetailViewModel.getActivities(eventId).observe(this, new Observer<List<Aktivnosti>>() {
            @Override
            public void onChanged(List<Aktivnosti> activities) {
                if (activities != null) {
                    aktivnostAdapter = new AktivnostAdapter(activities);
                    aktivnostiRecyclerView.setAdapter(aktivnostAdapter);
                    Log.d(TAG, "Ucitano " + activities.size() + " aktivnosti za EventID: " + eventId);
                } else {
                    Log.d(TAG, "Nema aktivnosti za EventID: " + eventId);
                }
            }
        });

        //TODO Provjeriti da li je korisnik vec prijavljen...
        boolean isLoggedIn = checkIfLoggedIn();
        if (!isLoggedIn) {
            signupForm.setVisibility(LinearLayout.VISIBLE);
        } else {
            signupForm.setVisibility(LinearLayout.GONE);
        }

        //TODO Signup logika
        registerButton.setOnClickListener(v -> {
            String name = userName.getText().toString();
            String email = userEmail.getText().toString();
            Set<Aktivnosti> selectedActivities = aktivnostAdapter.getSelectedActivities();

            if (name.isEmpty() || email.isEmpty()) {
                Toast.makeText(EventDetailActivity.this, "Unesi sve podatke", Toast.LENGTH_SHORT).show();
            } else if (selectedActivities.isEmpty()) {
                Toast.makeText(EventDetailActivity.this, "Odaberi barem jednu aktivnost", Toast.LENGTH_SHORT).show();
            } else {
                signUpUser(name, email, selectedActivities);
            }
        });
    }

    private boolean checkIfLoggedIn() {
        //TOOD Ili tu provjeriti da li je korisnik vec prijavljen
        return false;
    }

    //TODO fixati
    private void signUpUser(String name, String email, Set<Aktivnosti> selectedActivities) {
        KorisniciAktivnostiDomain signUpDetails = new KorisniciAktivnostiDomain();
        signUpDetails.setKorisnikName(name);
        signUpDetails.setKorisnikEmail(email);
        signUpDetails.setEventId(eventId);

        List<Integer> aktivnostiIds = new ArrayList<>();
        for (Aktivnosti aktivnost : selectedActivities) {
            aktivnostiIds.add(aktivnost.getId());
        }
        signUpDetails.setAktivnostiIds(aktivnostiIds);

        ApiService apiService = ApiClient.getRetrofitInstance().create(ApiService.class);
        Call<Void> call = apiService.signUpUser(signUpDetails);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(EventDetailActivity.this, "Uspješna prijava!", Toast.LENGTH_SHORT).show();
                    // email confirmation valjda na backendu
                } else {
                    Toast.makeText(EventDetailActivity.this, "Pokušaj opet.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(EventDetailActivity.this, "Pokušaj opet.", Toast.LENGTH_SHORT).show();
            }
        });

        for (Aktivnosti aktivnost : selectedActivities) {
            Log.d(TAG, "Prijavljene aktivnosti: " + aktivnost.getNaziv());
        }
    }
}
