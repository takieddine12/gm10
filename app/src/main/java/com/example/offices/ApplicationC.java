package com.example.offices;

import android.app.Application;
import android.content.Intent;

import com.google.firebase.auth.FirebaseAuth;

public class ApplicationC  extends Application {

    private FirebaseAuth firebaseAuth;
    @Override
    public void onCreate() {
        super.onCreate();
        firebaseAuth  = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser() != null){
            startActivity(new Intent(this, MapsActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        }
    }
}
