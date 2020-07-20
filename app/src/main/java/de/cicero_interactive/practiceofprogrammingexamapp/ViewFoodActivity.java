package de.cicero_interactive.practiceofprogrammingexamapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Date;

public class ViewFoodActivity extends AppCompatActivity implements AddStringPlusNumberDialog.AddDialogListener {
    RecyclerView recyclerView;
    ViewFoodAdapter adapter;
    RecyclerView.LayoutManager layoutManager;

    ArrayList<Food> foods;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_medical_data);
        setTitle(R.string.food);

        MyDatabase myDatabase = new MyDatabase(this);
        try {
            foods = myDatabase.getFoods();
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
        adapter = new ViewFoodAdapter(this, foods);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new ViewFoodAdapter.OnItemClickListener() {
            @Override
            public void onDeleteClick(int position) throws JSONException {
                deleteFood(position);
            }
        });
    }



    public void deleteFood(final int position) throws JSONException {
        ArrayList<Food> foods_new = new ArrayList<Food>();
        // Re-add all beds except for the one which should get deleted
        for (int i = 0; i < foods.size(); i++) {
            if (i != position) {
                foods_new.add(foods.get(i));
            }
        }
        foods = foods_new;
        buildRecyclerView();
        //adapter.notifyItemRemoved(position);
        MyDatabase myDatabase = new MyDatabase(this);
        myDatabase.insertFoods(foods);
    }

    public void addFood(final String name, int calories, final Date date) throws JSONException {
        foods = new ArrayList<Food>(foods);
        foods.add(0, new Food(name, calories, date));
        buildRecyclerView();
        //adapter.notifyItemInserted(weights.size()-1);
        MyDatabase myDatabase = new MyDatabase(this);
        myDatabase.insertFoods(foods);
    }




    public void onAddButtonPressed(View view) {
        AddStringPlusNumberDialog addNumberDialog = new AddStringPlusNumberDialog();
        Bundle bundle = new Bundle();
        bundle.putString("title", "Enter a new food");
        bundle.putString("hint_text_chooser", "Hamburger");
        bundle.putString("hint_number_chooser", "295 [kcal]");
        addNumberDialog.setArguments(bundle);
        addNumberDialog.show(getSupportFragmentManager(), "Add Food Dialog");
    }

    @Override
    public void passDataBack(String name, int calories) throws JSONException {
        addFood(name, calories, new Date());
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