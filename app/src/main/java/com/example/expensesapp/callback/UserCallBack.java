package com.example.expensesapp.callback;

import com.google.android.gms.tasks.Task;

public interface UserCallBack {
    void onUserSaveComplete(Task<Void> task);
}
