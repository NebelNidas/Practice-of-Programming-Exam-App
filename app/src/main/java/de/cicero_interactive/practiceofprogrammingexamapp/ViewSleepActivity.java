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

public class ViewSleepActivity extends AppCompatActivity implements AddNumberDialog.AddDialogListener {
    RecyclerView recyclerView;
    ViewSleepAdapter adapter;
    RecyclerView.LayoutManager layoutManager;

    ArrayList<Sleep> sleeps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_medical_data);

        MyDatabase myDatabase = new MyDatabase(this);
        try {
            sleeps = myDatabase.getSleeps();
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
        adapter = new ViewSleepAdapter(this, sleeps);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new ViewSleepAdapter.OnItemClickListener() {
            @Override
            public void onDeleteClick(int position) throws JSONException {
                deleteSleep(position);
            }
        });
    }



    public void deleteSleep(final int position) throws JSONException {
        ArrayList<Sleep> sleeps_new = new ArrayList<Sleep>();
        // Re-add all beds except for the one which should get deleted
        for (int i = 0; i < sleeps.size(); i++) {
            if (i != position) {
                sleeps_new.add(sleeps.get(i));
            }
        }
        sleeps = sleeps_new;
        buildRecyclerView();
        //adapter.notifyItemRemoved(position);
        MyDatabase myDatabase = new MyDatabase(this);
        myDatabase.insertSleeps(sleeps);
    }

    public void addSleep(final int sleep, final Date date) throws JSONException {
        sleeps = new ArrayList<Sleep>(sleeps);
        sleeps.add(0, new Sleep(sleep, date));
        buildRecyclerView();
        //adapter.notifyItemInserted(weights.size()-1);
        MyDatabase myDatabase = new MyDatabase(this);
        myDatabase.insertSleeps(sleeps);
    }




    public void onAddButtonPressed(View view) {
        AddNumberDialog addNumberDialog = new AddNumberDialog();
        Bundle bundle = new Bundle();
        // TODO: Make it possible to enter hours too, so you don't have to convert everything to minutes
        bundle.putString("title", "Enter your time slept (in minutes)");
        bundle.putString("hint", "480");
        addNumberDialog.setArguments(bundle);
        addNumberDialog.show(getSupportFragmentManager(), "Add Sleep Dialog");
    }

    @Override
    public void passDataBack(int minutes) throws JSONException {
        addSleep(minutes, new Date());
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