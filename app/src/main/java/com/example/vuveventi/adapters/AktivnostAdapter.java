package com.example.vuveventi.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vuveventi.R;
import com.example.vuveventi.models.Aktivnosti;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AktivnostAdapter extends RecyclerView.Adapter<AktivnostAdapter.AktivnostViewHolder> {

    private static final String TAG = "AktivnostAdapter";
    private List<Aktivnosti> aktivnostiList;
    private Set<Aktivnosti> selectedActivities = new HashSet<>();

    public AktivnostAdapter(List<Aktivnosti> aktivnostiList) {
        this.aktivnostiList = aktivnostiList;
        Log.d(TAG, "AktivnostAdapter ucitao " + aktivnostiList.size() + " aktivnosti.");
    }

    @NonNull
    @Override
    public AktivnostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder pozvan");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_aktivnost, parent, false);
        return new AktivnostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AktivnostViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder pozvan za: " + position);
        Aktivnosti aktivnost = aktivnostiList.get(position);
        holder.aktivnostName.setText(aktivnost.getNaziv());
        //TODO Fixat formatiranje vremena i prostorije
        holder.aktivnostTime.setText(String.format("%s - %s", aktivnost.getVrijemePocetka(), aktivnost.getVrijemeZavrsetka()));
        holder.aktivnostRoom.setText(String.format("Prostorija: %d", aktivnost.getEventId()));

        holder.checkBox.setOnCheckedChangeListener(null);
        holder.checkBox.setChecked(selectedActivities.contains(aktivnost));
        holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                selectedActivities.add(aktivnost);
            } else {
                selectedActivities.remove(aktivnost);
            }
            Log.d(TAG, "Selektirane aktivnosti: " + selectedActivities.size());
        });
        Log.d(TAG, "Bound aktivnost: " + aktivnost.getNaziv());
    }

    @Override
    public int getItemCount() {
        int count = aktivnostiList.size();
        Log.d(TAG, "getItemCount pozvan, item count: " + count);
        return count;
    }

    public Set<Aktivnosti> getSelectedActivities() {
        return selectedActivities;
    }

    public static class AktivnostViewHolder extends RecyclerView.ViewHolder {

        TextView aktivnostName, aktivnostTime, aktivnostRoom;
        CheckBox checkBox;

        public AktivnostViewHolder(@NonNull View itemView) {
            super(itemView);
            aktivnostName = itemView.findViewById(R.id.aktivnost_name);
            aktivnostTime = itemView.findViewById(R.id.aktivnost_time);
            aktivnostRoom = itemView.findViewById(R.id.aktivnost_room);
            checkBox = itemView.findViewById(R.id.aktivnost_checkbox);
            Log.d(TAG, "AktivnostViewHolder pozvan");
        }
    }
}
