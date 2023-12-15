package com.example.expensesapp.controller;

import android.net.Uri;

import androidx.annotation.NonNull;

import com.example.expensesapp.callback.StorageCallBack;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;

public class StorageController {

    private FirebaseStorage storage;
    public StorageController(){
        storage  = FirebaseStorage.getInstance();
    }

    public String downloadImageUrl(String imagePath) {
        Task<Uri> downloadImageTask = storage.getReference().child(imagePath).getDownloadUrl();
        while (!downloadImageTask.isComplete() && !downloadImageTask.isCanceled());

        return downloadImageTask.getResult().toString();
    }
}
