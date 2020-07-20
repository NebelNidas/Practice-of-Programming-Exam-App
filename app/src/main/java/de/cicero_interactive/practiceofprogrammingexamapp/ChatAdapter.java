package de.cicero_interactive.practiceofprogrammingexamapp;

import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MyViewHolder> /*implements Filterable*/ {
    ArrayList<ChatMessage> messages;
    ArrayList<ChatMessage> messages_full;
    Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView message_sender,
                message_content,
                message_date;
        LinearLayout linearLayoutOuter,
                     linearLayoutInner;
        CardView cardView;

        public MyViewHolder(View itemView) {
            super(itemView);
            message_sender = itemView.findViewById(R.id.message_sender);
            message_content = itemView.findViewById(R.id.message_content);
            message_date = itemView.findViewById(R.id.message_date);
            linearLayoutOuter = itemView.findViewById(R.id.linearLayoutOuter);
            linearLayoutInner = itemView.findViewById(R.id.linearLayoutInner);
            cardView = itemView.findViewById(R.id.cardView);
        }
    }


    public ChatAdapter(Context context, ArrayList<ChatMessage> messages) {
        this.context = context;
        this.messages = messages;
        messages_full = new ArrayList<>(messages);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.chat_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        if (messages == null) {
            return;
        }

        String sender = messages.get(position).getSender();
        String content = messages.get(position).getMessage();
        String date = new SimpleDateFormat("dd MMMM yyyy, HH:mm").format(messages.get(position).getDate());

        holder.message_sender.setText(sender);
        holder.message_content.setText(content);
        holder.message_date.setText(date);

        if (sender.equals("self")) {
            holder.linearLayoutOuter.setGravity(Gravity.RIGHT);
            holder.cardView.setCardBackgroundColor(ResourcesCompat.getColor(context.getResources(), R.color.color_chat_message_bubble_self, null));
            holder.message_sender.setText("You");
        }
    }

    @Override
    public int getItemCount() {
        if (messages != null) {
            return messages.size();
        }
        return 0;
    }


    // TODO: Implement filter
    /*
    @Override
    public Filter getFilter() {
        return filter;
    }

    private Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            ArrayList<ChatMessage> filtered_list = new ArrayList<>();

            if (charSequence == null || charSequence.length() == 0) {
                filtered_list.addAll(messages_full);
            } else {
                String filter_pattern = charSequence.toString().toLowerCase().trim();
                Log.d("test", filter_pattern);

                for (ChatMessage message : messages_full) {
                    if (message.getSender().toLowerCase().contains(filter_pattern) ||
                            message.getMessage().toLowerCase().contains(filter_pattern) ||
                            new SimpleDateFormat("dd MMMM yyyy").format(message.getDate()).toLowerCase().contains(filter_pattern)
                    ) {
                        filtered_list.add(message);
                    }
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = filtered_list;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            messages.clear();
            messages.addAll((List) filterResults.values);
            notifyDataSetChanged();
        }
    };*/
}
