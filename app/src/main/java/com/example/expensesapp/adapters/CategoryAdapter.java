package com.example.expensesapp.adapters;

import android.app.Activity;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.expensesapp.boundary.UserCategoryBoundary;
import com.example.expensesapp.callback.OnClickCategory;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {
    private Activity activity;
    private ArrayList<UserCategoryBoundary> categories = new ArrayList<>();
    private OnClickCategory onClickCategory;

    public CategoryAdapter(Activity activity, ArrayList<UserCategoryBoundary> categories){
        this.categories = categories;
        this.activity = activity;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
