package com.example.sadhanaravoori.firebasevconnect;

import android.util.Log;

/**
 * Created by Sadhana Ravoori on 25-04-18.
 */

public class StoredVolunteerInfo {

    public String name;
    public String email;

    public String getName() {
        return name;
    }

    public void setName(String name) {

        this.name = name;
        Log.e("setSTuff",this.name);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {

        this.email = email;
        Log.e("setSTuff",this.email);
    }

    public String toString()
    {
        return name+" "+email;
    }
}
