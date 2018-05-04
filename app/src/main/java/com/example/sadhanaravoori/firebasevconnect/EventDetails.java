package com.example.sadhanaravoori.firebasevconnect;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by Sadhana Ravoori on 14-04-18.
 */
public class EventDetails implements Comparable<EventDetails>, Serializable{

    private String url;
    public String nameOfOrganization, nameOfEvent, descriptionOfEvent, date, time, address;
    public float latitude, longitude, distance, volLat, volLong;
    public int noOfVolunteersMin, noOfVolunteersMax;
    public int noOfVolunteersRegistered;
    public String adminEmail;
   // public HashMap<String, String>volunteerList;
    public EventDetails() {
        // This is default constructor.
    }

    public EventDetails(String nameOfOrganization, String nameOfEvent, String descriptionOfEvent, String date, String time, Float latitude, Float longitude, int noOfVolunteersMax, int noOfVolunteersMin, int noOfVolunteersRegistered)
    {
        this.nameOfOrganization = nameOfOrganization;
        this.time = time;
        this.date = date;
        this.nameOfEvent = nameOfEvent;
        this.descriptionOfEvent = descriptionOfEvent;
        this.latitude=latitude;
        this.longitude=longitude;
        this.noOfVolunteersMax=noOfVolunteersMax;
        this.noOfVolunteersMin=noOfVolunteersMin;
        this.noOfVolunteersRegistered=noOfVolunteersRegistered;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setReference(String url)
    {
        this.url=url;
    }

    public String getReference() {
        return url;
    }

    public String getAdminEmail() {
        return adminEmail;
    }

    public void setAdminEmail(String adminEmail) {
        this.adminEmail = adminEmail;
    }

    public String getNameOfOrganization() {

        return nameOfOrganization;
    }

    public int getNoOfVolunteersRegistered() {
        Log.e("NOVR",noOfVolunteersRegistered+"");
        return noOfVolunteersRegistered;
    }

    public void setNoOfVolunteersRegistered(int noOfVolunteersRegistered) {
        this.noOfVolunteersRegistered = noOfVolunteersRegistered;
        //fire.child("NoOfVolunteersRegistered").setValue(this.noOfVolunteersRegistered);
    }

    public int getNoOfVolunteersMax() {
        return noOfVolunteersMax;
    }

    public int getNoOfVolunteersMin() {
        return noOfVolunteersMin;
    }

    public void setNoOfVolunteersMax(int noOfVolunteersMax) {
        this.noOfVolunteersMax = noOfVolunteersMax;
    }

    public void setNoOfVolunteersMin(int noOfVolunteersMin) {
        this.noOfVolunteersMin = noOfVolunteersMin;
    }

    public String getDescriptionOfEvent()
    {
        return descriptionOfEvent;
    }

    public String getTime() {
        return time;
    }

    public String getDate() {
        return date;
    }

    public String getNameOfEvent() {
        return nameOfEvent;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setDescriptionOfEvent(String descriptionOfEvent) {
        this.descriptionOfEvent = descriptionOfEvent;
    }

    public void setNameOfEvent(String nameOfEvent) {
        this.nameOfEvent = nameOfEvent;
    }

    public void setNameOfOrganization(String nameOfOrganization) {
        this.nameOfOrganization = nameOfOrganization;
    }

    public Float getLatitude() {
        return latitude;
    }

    public Float getLongitude() {
        return longitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
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
    public int compareTo(@NonNull EventDetails eventDetails) {

        if(distance > eventDetails.distance)
            return 1;
        else if(distance < eventDetails.distance)
            return -1;
        else
            return 0;
    }

    public String toString()
    {
        return nameOfEvent+" "+noOfVolunteersRegistered+" ";
    }

}