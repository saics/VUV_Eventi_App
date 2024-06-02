package com.example.vuveventi.activities;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vuveventi.R;
import com.example.vuveventi.adapters.EventAdapter;
import com.example.vuveventi.models.Eventi;
import com.example.vuveventi.viewmodels.EventViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private EventViewModel eventViewModel;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        eventViewModel = new ViewModelProvider(this).get(EventViewModel.class);

        eventViewModel.getEvents().observe(this, new Observer<List<Eventi>>() {
            @Override
            public void onChanged(List<Eventi> events) {
                if (events != null) {
                    Log.d(TAG, "Number of events received: " + events.size());
                    recyclerView.setAdapter(new EventAdapter(events, MainActivity.this));
                } else {
                    Log.d(TAG, "No events received");
                }
            }
        });
    }
}
