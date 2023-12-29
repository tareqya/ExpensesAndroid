package com.example.expensesapp.callback;

import com.example.expensesapp.boundary.UserBoundary;
import com.google.android.gms.tasks.Task;

public interface UserCallBack {
    void onUserSaveComplete(Task<Void> task);
    void onUserDataFetchComplete(UserBoundary userBoundary);
}
