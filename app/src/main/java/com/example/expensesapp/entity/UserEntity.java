package com.example.expensesapp.entity;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;

public class UserEntity extends FirebaseKey implements Serializable {
    public static final String USERS_TABLE = "Users";

    private String firstName;
    private String lastName;
    private String email;
    private String imagePath;
    private String imageUrl;

    public UserEntity() {}

    public String getFirstName() {
        return firstName;
    }

    public UserEntity setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public UserEntity setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getEmail(){
        return this.email;
    }

    public UserEntity setEmail(String email){
        this.email = email;
        return this;
    }

    public  com.google.android.gms.tasks.Task<Void> saveUser(FirebaseDatabase db){

        return db.getReference(USERS_TABLE).child(this.key).setValue(this);
    }

    public String getImagePath() {
        return imagePath;
    }

    public UserEntity setImagePath(String imagePath) {
        this.imagePath = imagePath;
        return this;
    }

    @Exclude
    public String getImageUrl() {
        return imageUrl;
    }

    public UserEntity setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    @Exclude
    public String getFullName(){
        return this.firstName + " " + this.lastName;
    }
}
