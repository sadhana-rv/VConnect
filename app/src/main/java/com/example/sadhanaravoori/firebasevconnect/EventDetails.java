package com.example.sadhanaravoori.firebasevconnect;

import android.location.Location;
import android.util.Log;

/**
 * Created by Sadhana Ravoori on 14-04-18.
 */
public class EventDetails {


    public String nameOfOrganization, nameOfEvent, descriptionOfEvent, date, time;
    float latitude, longitude, distance, volLat, volLong;

    public EventDetails() {
        // This is default constructor.
    }

    public EventDetails(String nameOfOrganization, String nameOfEvent, String descriptionOfEvent, String date, String time, Float latitude, Float longitude)
    {
        this.nameOfOrganization = nameOfOrganization;
        this.time = time;
        this.date = date;
        this.nameOfEvent = nameOfEvent;
        this.descriptionOfEvent = descriptionOfEvent;
        this.latitude=latitude;
        this.longitude=longitude;
    }

    public String getNameOfOrganization() {

        return nameOfOrganization;
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
        Location.distanceBetween(latitude, longitude, this.volLat, this.volLong, results);
        distance=results[0];
        Log.e("DistanceBetween",latitude+" "+this.volLat+"="+distance);
        Log.e("DistanceBetween",longitude+" "+volLong+"="+distance);
    }

}