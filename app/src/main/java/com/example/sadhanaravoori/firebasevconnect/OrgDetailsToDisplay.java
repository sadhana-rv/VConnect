package com.example.sadhanaravoori.firebasevconnect;

import android.location.Location;
import android.support.annotation.NonNull;
import android.util.Log;

import java.io.Serializable;

/**
 * Created by Sadhana Ravoori on 03-05-18.
 */

public class OrgDetailsToDisplay implements Comparable<OrgDetailsToDisplay>, Serializable {
    String nameOfAdmin, nameOfOrganization, description, email, address,  phone;
    float latitude, longitude;
    float volLat, volLong, distance;
    private String url;

    public void setReference(String url)
    {
        this.url=url;
    }

    public String getReference() {
        return url;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public String getDescription() {
        return description;
    }

    public float getLatitude() {
        return latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public String getNameOfAdmin() {
        return nameOfAdmin;
    }

    public String getNameOfOrganization() {
        return nameOfOrganization;
    }

    public String getPhone() {
        return phone;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public void setNameOfAdmin(String nameOfAdmin) {
        this.nameOfAdmin = nameOfAdmin;
    }

    public void setNameOfOrganization(String nameOfOrganization) {
        this.nameOfOrganization = nameOfOrganization;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void findDistance(float volLat,float volLong)
    {
        this.volLat=volLat;
        this.volLong=volLong;
        float[] results = new float[1];
        Location startPoint=new Location("locationA");
        startPoint.setLatitude(this.volLat);
        startPoint.setLongitude(this.volLong);

        Location endPoint=new Location("locationA");
        endPoint.setLatitude(latitude);
        endPoint.setLongitude(longitude);

        distance=startPoint.distanceTo(endPoint);
        //Location.distanceBetween(latitude, longitude, this.volLat, this.volLong, results);
        //distance=results[0];
        Log.e("DistanceBetween",latitude+" "+this.volLat+"="+distance);
        Log.e("DistanceBetween",longitude+" "+volLong+"="+distance);
    }

    @Override
    public int compareTo(@NonNull OrgDetailsToDisplay eventDetails) {
        if(distance > eventDetails.distance)
            return 1;
        else if(distance < eventDetails.distance)
            return -1;
        else
            return 0;
    }
}
