package com.example.sadhanaravoori.firebasevconnect;

import android.app.Application;

import com.firebase.client.Firebase;

/**
 * Created by Sadhana Ravoori on 14-04-18.
 */

public class FireApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);
    }
}
