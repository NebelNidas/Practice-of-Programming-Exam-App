package de.cicero_interactive.practiceofprogrammingexamapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ViewWeightAdapter extends RecyclerView.Adapter<ViewWeightAdapter.MyViewHolder> {
    ArrayList<Weight> weights;
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

        public MyViewHolder(View itemView) {
            super(itemView);
            data_value = itemView.findViewById(R.id.data_value);
            data_date = itemView.findViewById(R.id.data_date);
            data_delete = itemView.findViewById(R.id.data_delete);
            medical_data_row = itemView.findViewById(R.id.medical_data_row);

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


    public ViewWeightAdapter(Context context, ArrayList<Weight> weights) {
        this.context = context;
        this.weights = weights;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.medical_data_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        if (weights == null) {
            return;
        }

        holder.data_value.setText("" + weights.get(position).getWeight());
        holder.data_date.setText(new SimpleDateFormat("dd MMMM yyyy").format(weights.get(position).getDate()));
    }

    @Override
    public int getItemCount() {
        if (weights != null) {
            return weights.size();
        }
        return 0;
    }
}

