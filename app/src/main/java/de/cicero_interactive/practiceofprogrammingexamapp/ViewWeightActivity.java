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

public class ViewWeightActivity extends AppCompatActivity implements AddNumberDialog.AddDialogListener {
    RecyclerView recyclerView;
    ViewWeightAdapter adapter;
    RecyclerView.LayoutManager layoutManager;

    ArrayList<Weight> weights;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_medical_data);

        MyDatabase myDatabase = new MyDatabase(this);
        try {
            weights = myDatabase.getWeights();
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
        adapter = new ViewWeightAdapter(this, weights);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new ViewWeightAdapter.OnItemClickListener() {
            @Override
            public void onDeleteClick(int position) throws JSONException {
                deleteWeight(position);
            }
        });
    }



    public void deleteWeight(final int position) throws JSONException {
        ArrayList<Weight> weights_new = new ArrayList<Weight>();
        // Re-add all beds except for the one which should get deleted
        for (int i = 0; i < weights.size(); i++) {
            if (i != position) {
                weights_new.add(weights.get(i));
            }
        }
        weights = weights_new;
        buildRecyclerView();
        //adapter.notifyItemRemoved(position);
        MyDatabase myDatabase = new MyDatabase(this);
        myDatabase.insertWeights(weights);
    }

    public void addWeight(final int weight, final Date date) throws JSONException {
        weights = new ArrayList<Weight>(weights);
        weights.add(0, new Weight(weight, date));
        buildRecyclerView();
        //adapter.notifyItemInserted(weights.size()-1);
        MyDatabase myDatabase = new MyDatabase(this);
        myDatabase.insertWeights(weights);
    }




    public void onAddButtonPressed(View view) {
        AddNumberDialog addNumberDialog = new AddNumberDialog();
        Bundle bundle = new Bundle();
        bundle.putString("title", "Enter your current weight (kg)");
        bundle.putString("hint", "75");
        addNumberDialog.setArguments(bundle);
        addNumberDialog.show(getSupportFragmentManager(), "Add Weight Dialog");
    }

    @Override
    public void passDataBack(int weight) throws JSONException {
        addWeight(weight, new Date());
    }




    // Makes sure that you get back to the correct fragment in MainActivity
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
        }

        return true;
    }
}