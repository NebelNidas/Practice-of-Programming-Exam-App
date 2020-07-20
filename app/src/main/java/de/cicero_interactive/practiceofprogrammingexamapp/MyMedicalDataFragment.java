package de.cicero_interactive.practiceofprogrammingexamapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.SimpleTimeZone;

public class MyMedicalDataFragment extends Fragment {
    private CardView food_card,
                     sleep_card,
                     weight_card,
                     allergies_card;
    private TextView food_preview,
                     sleep_preview,
                     weight_preview,
                     allergies_preview;
    private MyDatabase myDatabase;
    private ArrayList<Article> articles;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View inf = inflater.inflate(R.layout.fragment_my_medical_data, container, false);

        getActivity().setTitle(R.string.my_medical_data);
        myDatabase = new MyDatabase(getActivity());

        food_card = inf.findViewById(R.id.food_card);
        food_preview = inf.findViewById(R.id.food_preview);
        sleep_card = inf.findViewById(R.id.sleep_card);
        sleep_preview = inf.findViewById(R.id.sleep_preview);
        weight_card = inf.findViewById(R.id.weight_card);
        weight_preview = inf.findViewById(R.id.weight_preview);
        allergies_card = inf.findViewById(R.id.allergies_card);
        allergies_preview = inf.findViewById(R.id.allergies_preview);


        food_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ViewFoodActivity.class);
                startActivityForResult(intent, 1);
                setPreviews();
            }
        });

        sleep_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ViewSleepActivity.class);
                startActivityForResult(intent, 1);
                setPreviews();
            }
        });

        weight_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ViewWeightActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        allergies_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ViewAllergiesActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        setPreviews();
        return inf;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            setPreviews();
        }
    }


    private void setPreviews() {
        int calories = 0;
        try {
            if (myDatabase.getFoods().size() != 0)  {
                for (int i = 0; i < myDatabase.getFoods().size(); i++) {
                    if (DateUtils.isToday(myDatabase.getFoods().get(i).getDate().getTime())) {
                        calories += myDatabase.getFoods().get(i).getCalories();
                    }
                }
                int hours = calories / 60;
                int minutes = calories % 60;
                food_preview.setText(calories + " kcal today");
            } else {
                food_preview.setText("N/A");
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


        int minutes_slept = 0;
        try {
            if (myDatabase.getSleeps().size() != 0)  {
                for (int i = 0; i < myDatabase.getSleeps().size(); i++) {
                    if (DateUtils.isToday(myDatabase.getSleeps().get(i).getDate().getTime())) {
                        minutes_slept += myDatabase.getSleeps().get(i).getMinutes();
                    }
                }
                int hours = minutes_slept / 60;
                int minutes = minutes_slept % 60;
                sleep_preview.setText(hours + " h " + minutes + " min today");
            } else {
                sleep_preview.setText("N/A");
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


        try {
            if (myDatabase.getWeights().size() != 0)  {
                weight_preview.setText(myDatabase.getWeights().get(0).getWeight() + " kg");
            } else {
                weight_preview.setText("N/A");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        String allergies_preview_text = "";
        try {
            if (myDatabase.getAllergies().size() != 0)  {
                for (int i = 0; i < myDatabase.getAllergies().size(); i++) {
                    allergies_preview_text += myDatabase.getAllergies().get(i).getName();
                    if (i < myDatabase.getAllergies().size() - 1) {
                        allergies_preview_text += ", ";
                    }
                }
                allergies_preview.setText(allergies_preview_text);
            } else {
                allergies_preview.setText("N/A");
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
