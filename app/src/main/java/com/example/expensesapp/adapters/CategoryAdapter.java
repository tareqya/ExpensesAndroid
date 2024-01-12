package com.example.expensesapp.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.expensesapp.R;
import com.example.expensesapp.boundary.UserCategoryBoundary;
import com.example.expensesapp.callback.OnClickCategory;
import com.google.android.material.progressindicator.LinearProgressIndicator;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {
    private Context context;
    private ArrayList<UserCategoryBoundary> categories;
    private OnClickCategory onClickCategory;

    public CategoryAdapter(Context context, ArrayList<UserCategoryBoundary> categories){
        this.categories = categories;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        CategoryViewHolder categoryViewHolder = (CategoryViewHolder) holder;
        UserCategoryBoundary categoryBoundary = getItem(position);

        categoryViewHolder.category_TV_name.setText(categoryBoundary.getCategoryEntity().getName());
        Glide.with(context)
                .load(categoryBoundary.getCategoryEntity().getImageUrl())
                .into(categoryViewHolder.category_IMG_image);
        categoryViewHolder.category_TV_total.setText(categoryBoundary.getTotalPrice() + " ₪");

        double percent = categoryBoundary.getTotalPrice() / categoryBoundary.getMaxPrice();

        if (percent > 1){
            categoryViewHolder.category_PI_progress.setProgress(100);
        }else{
            // percent * 100
            int value = (int) Math.round(percent * 100);
            categoryViewHolder.category_PI_progress.setProgress(value);
        }
    }

    private UserCategoryBoundary getItem(int position) {
        return categories.get(position);
    }

    @Override
    public int getItemCount() {
        return this.categories.size();
    }

    public void setOnClickCategory(OnClickCategory onClickCategory){
        this.onClickCategory = onClickCategory;
    }

    public class CategoryViewHolder extends  RecyclerView.ViewHolder {

        public ImageView category_IMG_image;
        public TextView category_TV_name;
        public LinearProgressIndicator category_PI_progress;
        public TextView category_TV_total;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);

            category_IMG_image = itemView.findViewById(R.id.category_IMG_image);
            category_TV_name = itemView.findViewById(R.id.category_TV_name);
            category_PI_progress = itemView.findViewById(R.id.category_PI_progress);
            category_TV_total = itemView.findViewById(R.id.category_TV_total);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    UserCategoryBoundary categoryBoundary = getItem(getAdapterPosition());
                    int position = getAdapterPosition();
                    onClickCategory.onClick(categoryBoundary, position);
                }
            });

        }
    }
}
