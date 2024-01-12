package com.example.expensesapp.callback;

import com.example.expensesapp.boundary.UserCategoryBoundary;

public interface OnClickCategory {
    void onClick(UserCategoryBoundary categoryBoundary, int position);
}
