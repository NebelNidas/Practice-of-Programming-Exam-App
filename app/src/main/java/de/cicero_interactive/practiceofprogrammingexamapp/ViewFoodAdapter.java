package de.cicero_interactive.practiceofprogrammingexamapp;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ViewFoodAdapter extends RecyclerView.Adapter<ViewFoodAdapter.MyViewHolder> {
    ArrayList<Food> foods;
    Context context;
    OnItemClickListener onItemClickListener;


    public interface OnItemClickListener {
        void onDeleteClick(int position) throws JSONException;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView data_value,
                 data_date;
        ImageView data_delete;
        CardView medical_data_row;
        LinearLayout linear_layout_text;

        public MyViewHolder(View itemView) {
            super(itemView);
            data_value = itemView.findViewById(R.id.data_value);
            data_date = itemView.findViewById(R.id.data_date);
            data_delete = itemView.findViewById(R.id.data_delete);
            medical_data_row = itemView.findViewById(R.id.medical_data_row);
            linear_layout_text = itemView.findViewById(R.id.linear_layout_text);

            data_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        try {
                            onItemClickListener.onDeleteClick(position);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }
    }


    public ViewFoodAdapter(Context context, ArrayList<Food> foods) {
        this.context = context;
        this.foods = foods;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.medical_data_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        if (foods == null) {
            return;
        }

        holder.data_value.setText(foods.get(position).getName() + ": " + foods.get(position).getCalories() + " kcal");
        holder.data_date.setText(new SimpleDateFormat("dd MMMM yyyy HH:mm").format(foods.get(position).getDate()));
        // Set orientation to vertical because above textviews are quite long
        holder.linear_layout_text.setOrientation(LinearLayout.VERTICAL);
        holder.linear_layout_text.setGravity(Gravity.LEFT);
    }

    @Override
    public int getItemCount() {
        if (foods != null) {
            return foods.size();
        }
        return 0;
    }
}

