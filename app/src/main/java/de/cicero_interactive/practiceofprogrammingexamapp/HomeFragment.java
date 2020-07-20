package de.cicero_interactive.practiceofprogrammingexamapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;

public class HomeFragment extends Fragment {
    CardView medical_data_card,
            medical_training_card,
            communication_card;
    private NavigationView navigationView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View inf = inflater.inflate(R.layout.fragment_home, container, false);

        navigationView = getActivity().findViewById(R.id.nav_view);
        getActivity().setTitle(R.string.app_name);

        medical_data_card = inf.findViewById(R.id.medical_data_card);
        medical_training_card = inf.findViewById(R.id.medical_training_card);
        communication_card = inf.findViewById(R.id.communication_card);

        medical_training_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new MedicalTrainingFragment()).commit();
                navigationView.setCheckedItem(R.id.nav_medical_training);
            }
        });
        communication_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ChatFragment()).commit();
                navigationView.setCheckedItem(R.id.nav_chat);
            }
        });

        return inf;
    }
}