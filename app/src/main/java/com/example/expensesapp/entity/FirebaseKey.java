package com.example.expensesapp.entity;

import com.google.firebase.database.Exclude;

import java.io.Serializable;

public class FirebaseKey implements Serializable {
    protected String key;


    public FirebaseKey(){}

    public FirebaseKey setKey(String key) {
        this.key = key;
        return this;
    }
    @Exclude
    public String getKey() {
        return key;
    }
}
