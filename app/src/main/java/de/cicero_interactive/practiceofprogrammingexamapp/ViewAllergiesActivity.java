package de.cicero_interactive.practiceofprogrammingexamapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Date;

public class ViewAllergiesActivity extends AppCompatActivity implements AddStringDialog.AddDialogListener {
    RecyclerView recyclerView;
    ViewAllergiesAdapter adapter;
    RecyclerView.LayoutManager layoutManager;

    ArrayList<Allergy> allergies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_medical_data);

        MyDatabase myDatabase = new MyDatabase(this);
        try {
            allergies = myDatabase.getAllergies();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        recyclerView = findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(this);

        buildRecyclerView();
    }

    public void buildRecyclerView() {
        // Setting hasFixedSize to true will increase performance
        recyclerView.setHasFixedSize(true);
        adapter = new ViewAllergiesAdapter(this, allergies);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new ViewAllergiesAdapter.OnItemClickListener() {
            @Override
            public void onDeleteClick(int position) throws JSONException {
                deleteAllergy(position);
            }
        });
    }



    public void deleteAllergy(final int position) throws JSONException {
        ArrayList<Allergy> allergies_new = new ArrayList<Allergy>();
        // Re-add all beds except for the one which should get deleted
        for (int i = 0; i < allergies.size(); i++) {
            if (i != position) {
                allergies_new.add(allergies.get(i));
            }
        }
        allergies = allergies_new;
        buildRecyclerView();
        //adapter.notifyItemRemoved(position);
        MyDatabase myDatabase = new MyDatabase(this);
        myDatabase.insertAllergies(allergies);
    }

    public void addAllergy(final String name, final Date date) throws JSONException {
        allergies = new ArrayList<Allergy>(allergies);
        allergies.add(0, new Allergy(name, date));
        buildRecyclerView();
        //adapter.notifyItemInserted(weights.size()-1);
        MyDatabase myDatabase = new MyDatabase(this);
        myDatabase.insertAllergies(allergies);
    }




    public void onAddButtonPressed(View view) {
        AddStringDialog addStringDialog = new AddStringDialog();
        Bundle bundle = new Bundle();
        // TODO: Make date choosable
        bundle.putString("title", "Enter an allergy");
        bundle.putString("hint", "Dust mites");
        addStringDialog.setArguments(bundle);
        addStringDialog.show(getSupportFragmentManager(), "Add Allergy Dialog");
    }

    @Override
    public void passDataBack(String name) throws JSONException {
        addAllergy(name, new Date());
    }





    @Override
    public void onBackPressed() {
        try {
            handleBackButtonPressed();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                try {
                    handleBackButtonPressed();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return true;
        }

        return(super.onOptionsItemSelected(item));
    }

    public void handleBackButtonPressed() throws JSONException {
        setResult(RESULT_OK);
        finish();
    }
}