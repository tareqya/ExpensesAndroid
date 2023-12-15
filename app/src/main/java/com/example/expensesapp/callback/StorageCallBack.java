package com.example.expensesapp.callback;

public interface StorageCallBack {
    void onImageUrlDownloaded(String imageUrl);

    void onDownloadFailed(Exception e);

}
