package com.example.expensesapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.expensesapp.R;
import com.example.expensesapp.entity.CategoryItem;

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private Context context;
    private ArrayList<CategoryItem> categoryItems;

    public ProductAdapter(Context context, ArrayList<CategoryItem> categoryItems){
        this.context = context;
        this.categoryItems = categoryItems;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item, parent, false);
        return new ProductAdapter.ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ProductAdapter.ProductViewHolder productViewHolder = (ProductAdapter.ProductViewHolder) holder;
        CategoryItem categoryItem = getItem(position);

        productViewHolder.product_TV_name.setText(categoryItem.getTitle());
        productViewHolder.product_TV_price.setText(categoryItem.getPrice() + " ₪");
    }

    public CategoryItem getItem(int position){
        return this.categoryItems.get(position);
    }
    @Override
    public int getItemCount() {
        return categoryItems.size();
    }

    public class ProductViewHolder extends  RecyclerView.ViewHolder {

        public TextView product_TV_name;
        public TextView product_TV_price;
        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            product_TV_price = itemView.findViewById(R.id.product_TV_price);
            product_TV_name = itemView.findViewById(R.id.product_TV_name);

        }
    }
}
