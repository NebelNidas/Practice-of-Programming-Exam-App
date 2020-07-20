package de.cicero_interactive.practiceofprogrammingexamapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class MedicalTrainingAdapter extends RecyclerView.Adapter<MedicalTrainingAdapter.MyViewHolder> implements Filterable {
    ArrayList<Article> articles;
    ArrayList<Article> articles_full;
    Context context;
    OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView article_icon;
        TextView article_title,
                article_date,
                article_author,
                article_desc;
        CardView article_row;

        public MyViewHolder(View itemView) {
            super(itemView);
            article_icon = itemView.findViewById(R.id.article_icon);
            article_title = itemView.findViewById(R.id.article_title);
            article_date = itemView.findViewById(R.id.article_date);
            article_author = itemView.findViewById(R.id.article_author);
            article_desc = itemView.findViewById(R.id.article_desc);
            article_row = itemView.findViewById(R.id.article_row);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        onItemClickListener.onItemClick(position);
                    }
                }
            });
        }
    }


    public MedicalTrainingAdapter(Context context, ArrayList<Article> articles) {
        this.context = context;
        this.articles = articles;
        articles_full = new ArrayList<>(articles);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.article_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        if (articles == null) {
            return;
        }
        // TODO: Make icon dynamically configurable in JSON
        holder.article_icon.setImageResource(R.drawable.ic_outline_highlight_24);

        String title = articles.get(position).getTitle();
        String date = new SimpleDateFormat("dd MMMM yyyy").format(articles.get(position).getDate());
        String author = articles.get(position).getAuthor();
        String description = articles.get(position).getDescription();


        holder.article_title.setText(title);
        holder.article_date.setText(date);
        holder.article_author.setText(author);
        holder.article_desc.setText(description);
        holder.article_title.setText(title);
    }

    @Override
    public int getItemCount() {
        if (articles != null) {
            return articles.size();
        }
        return 0;
    }




    @Override
    public Filter getFilter() {
        return filter;
    }

    private Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            ArrayList<Article> filtered_list = new ArrayList<>();

            if (charSequence == null || charSequence.length() == 0) {
                filtered_list.addAll(articles_full);
            } else {
                String filter_pattern = charSequence.toString().toLowerCase().trim();
                Log.d("test", filter_pattern);

                for (Article article : articles_full) {
                    if (article.getTitle().toLowerCase().contains(filter_pattern) ||
                        new SimpleDateFormat("dd MMMM yyyy").format(article.getDate()).toLowerCase().contains(filter_pattern) ||
                        article.getAuthor().toLowerCase().contains(filter_pattern) ||
                        article.getDescription().toLowerCase().contains(filter_pattern)
                    ) {
                        filtered_list.add(article);
                    }
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = filtered_list;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            articles.clear();
            articles.addAll((List) filterResults.values);
            notifyDataSetChanged();
        }
    };
}
