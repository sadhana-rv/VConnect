package com.example.sadhanaravoori.firebasevconnect;

import android.util.Log;

/**
 * Created by Sadhana Ravoori on 25-04-18.
 */

public class WebCrawledDetails {

    public String name;
    public String address;

    public String getName() {
        return name;
    }

    public void setName(String name) {

        this.name = name;
        Log.e("setSTuff",this.name);
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {

        this.address =address;
        Log.e("setSTuff",this.address);
    }

    public String toString()
    {
        return name+" "+address;
    }
}
