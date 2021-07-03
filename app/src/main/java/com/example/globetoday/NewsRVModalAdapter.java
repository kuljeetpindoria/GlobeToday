package com.example.globetoday;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

class NewsRVModalAdapter extends RecyclerView.Adapter<NewsRVModalAdapter.ViewHolder> {
    private ArrayList<Articles> articlesArrayList;
    private Context context;

    public NewsRVModalAdapter(ArrayList<Articles> articlesArrayList, Context context) {
        this.articlesArrayList = articlesArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public NewsRVModalAdapter.ViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news, parent, false);
        return new NewsRVModalAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsRVModalAdapter.ViewHolder holder, int position) {
        Articles articles = articlesArrayList.get(position);
        holder.subTitleTV.setText(articles.getDescription());
        holder.titleTV.setText(articles.getTitle());
        Picasso.get().load(articles.getUrlToImage()).into(holder.newsIV);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, NewsDetail.class);
                intent.putExtra("title", articles.getTitle());
                intent.putExtra("content", articles.getContent());
                intent.putExtra("desc", articles.getDescription());
                intent.putExtra("image", articles.getUrlToImage());
                intent.putExtra("url", articles.getUrl());
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {

        return articlesArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView titleTV, subTitleTV;
        private ImageView newsIV;
        public ViewHolder(@NonNull  View itemView) {
            super(itemView);
            titleTV = itemView.findViewById(R.id.TVNewsHeading);
            subTitleTV =  itemView.findViewById(R.id.TVNewsSubtitle);
            newsIV = itemView.findViewById(R.id.IVNews);
        }
    }
}
