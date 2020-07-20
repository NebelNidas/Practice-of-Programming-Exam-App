package de.cicero_interactive.practiceofprogrammingexamapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;

public class MyMedicalDataFragment extends Fragment {
    private CardView food_card,
                     sleep_card,
                     weight_card,
                     allergies_card;

    private MyDatabase myDatabase;
    private ArrayList<Article> articles;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View inf = inflater.inflate(R.layout.fragment_my_medical_data, container, false);

        getActivity().setTitle(R.string.my_medical_data);

        setHasOptionsMenu(true);

        food_card = inf.findViewById(R.id.food_card);
        sleep_card = inf.findViewById(R.id.sleep_card);
        weight_card = inf.findViewById(R.id.weight_card);
        allergies_card = inf.findViewById(R.id.allergies_card);

        food_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ViewFoodActivity.class);
                startActivity(intent);
            }
        });

        sleep_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ViewSleepActivity.class);
                startActivity(intent);
            }
        });

        weight_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ViewWeightActivity.class);
                startActivity(intent);
            }
        });

        allergies_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ViewAllergiesActivity.class);
                startActivity(intent);
            }
        });

        return inf;
    }
}
