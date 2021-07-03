package com.example.globetoday;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CategoryRVAdapter extends RecyclerView.Adapter<CategoryRVAdapter.ViewHolder>{

    private ArrayList<CategoryRVModaL>  categoryRVModal;
    private Context context;
    private CategorClickInterface categorClickInterface;

    public CategoryRVAdapter(ArrayList<CategoryRVModaL> categoryRVModal, Context context, CategorClickInterface categorClickInterface) {
        this.categoryRVModal = categoryRVModal;
        this.context = context;
        this.categorClickInterface = categorClickInterface;
    }

    public CategoryRVAdapter(ArrayList<CategoryRVModaL> categoryRVModal, Context context) {
        this.categoryRVModal = categoryRVModal;
        this.context = context;

    }

    @NonNull

    @Override
    public CategoryRVAdapter.ViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.categories,parent,false);
        return new CategoryRVAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  CategoryRVAdapter.ViewHolder holder, int position) {
        CategoryRVModaL categoryRVModaL = categoryRVModal.get(position);
        holder.categoryTV.setText(categoryRVModaL.getCategory());
        Picasso.get().load(categoryRVModaL.getCategoryImageUrl()).into(holder.categoryIV);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categorClickInterface.onCategoryClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {

        return categoryRVModal.size();
    }

    public interface CategorClickInterface{
        void onCategoryClick(int postion);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView categoryTV;
        private ImageView categoryIV;
        public ViewHolder(@NonNull  View itemView) {
            super(itemView);
            categoryTV = itemView.findViewById(R.id.TVCategories);
            categoryIV = itemView.findViewById(R.id.IVCatergories);
        }
    }
}
