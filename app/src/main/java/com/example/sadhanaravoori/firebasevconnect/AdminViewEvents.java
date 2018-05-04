package com.example.sadhanaravoori.firebasevconnect;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class AdminViewEvents extends AppCompatActivity {

    private Firebase fire;
    private Firebase events;
    private FirebaseAuth mAuth;
    float adminLat, adminLong;
    private DatabaseReference theReference;


    DatabaseReference databaseReference;

    List<EventDetails> list;
    public List<EventDetails> sortedList;

    RecyclerView recyclerView;

    AdminRecyclerViewAdapter adapter ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_view_events);

        //COPIED CODE

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(AdminViewEvents.this));

        mAuth= FirebaseAuth.getInstance();

        //USER HAS . IN THEIR EMAIL
        String email=mAuth.getCurrentUser().getEmail();
        final String user=email.replace('.',' ');

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                // this code will be executed after 2 seconds
                databaseReference=FirebaseDatabase.getInstance().getReference().child("Events");
                databaseReference.addValueEventListener(new com.google.firebase.database.ValueEventListener() {
                    @Override
                    public void onDataChange(com.google.firebase.database.DataSnapshot snapshot) {

                        list = new ArrayList<EventDetails>();
                        Iterable<com.google.firebase.database.DataSnapshot> AllEvents = snapshot.getChildren();

                        for (com.google.firebase.database.DataSnapshot dataSnapshot1 : AllEvents) {

                            EventDetails value = dataSnapshot1.getValue(EventDetails.class);
                            theReference=dataSnapshot1.getRef();
                            Log.d("Keyyyy", dataSnapshot1.getValue().toString());
                            value.setReference(theReference.toString());
                            value.setNameOfOrganization(dataSnapshot1.child("NameOfOrganization").getValue().toString());
                            value.setNameOfEvent(dataSnapshot1.child("NameOfEvent").getValue().toString());
                            value.setDescriptionOfEvent(dataSnapshot1.child("DescriptionOfEvent").getValue().toString());
                            value.setDate(dataSnapshot1.child("Date").getValue().toString());
                            value.setTime(dataSnapshot1.child("Time").getValue().toString());
                            value.setLatitude(Float.parseFloat(dataSnapshot1.child("Latitude").getValue().toString()));
                            value.setLongitude(Float.parseFloat(dataSnapshot1.child("Longitude").getValue().toString()));
                            value.setNoOfVolunteersMax(Integer.parseInt(dataSnapshot1.child("NoOfVolunteersMax").getValue().toString()));
                            value.setNoOfVolunteersMin(Integer.parseInt(dataSnapshot1.child("NoOfVolunteersMin").getValue().toString()));
                            value.setNoOfVolunteersRegistered(Integer.parseInt(dataSnapshot1.child("NoOfVolunteersRegistered").getValue().toString()));
                            value.setAdminEmail(dataSnapshot1.child("AdminEmail").getValue().toString());
                            //value.setVolunteerList((HashMap<String, String>) dataSnapshot1.child("VolunteersList").getValue());
                            //Log.e("VolunteerList", dataSnapshot1.child("VolunteersList").getValue().getClass().getName());

                            //ADD location.latitude and location.longitude for get and set methods
                            //In EventDetails create a new variable called distanceBetween
                            //In EventDetails create a new method called findDistanceBetween and pass the parameters volLat and volLong
                            //In findDistanceBetween set the value of distanceBetween

                            //Done
                            EventDetails ed = new EventDetails();
                            String adminEmail=value.getAdminEmail();
                            String nameOfEvent=value.getNameOfEvent();
                            String nameOfOrganization=value.getNameOfOrganization();
                            String date=value.getDate();
                            String time=value.getTime();
                            String descriptionOfEvent=value.getDescriptionOfEvent();
                            float latitude=value.getLatitude();
                            float longitude=value.getLongitude();
                            int noOfVolunteersMin=value.getNoOfVolunteersMin();
                            int noOfVolunteersMax=value.getNoOfVolunteersMax();
                            int noOfVolunteersRegistered=value.getNoOfVolunteersRegistered();

                            ed.setAdminEmail(adminEmail);
                            ed.setDescriptionOfEvent(descriptionOfEvent);
                            ed.setNameOfOrganization(nameOfOrganization);
                            ed.setNameOfEvent(nameOfEvent);
                            ed.setDate(date);
                            ed.setTime(time);
                            ed.setLatitude(latitude);
                            ed.setLongitude(longitude);
                            ed.setNoOfVolunteersMax(noOfVolunteersMax);
                            ed.setNoOfVolunteersMin(noOfVolunteersMin);
                            ed.setNoOfVolunteersRegistered(noOfVolunteersRegistered);
                            ed.setReference(theReference.toString());

                            if(ed.getAdminEmail().equals(user))
                                list.add(ed);
                        }

                        adapter = new AdminRecyclerViewAdapter(AdminViewEvents.this, list);
                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();

                        adapter.setOnItemClickListener(new AdminRecyclerViewAdapter.OnItemClickedListener() {
                            @Override
                            public void onItemClick(int position) {

                                EventDetails passThis=list.get(position);

                                Intent intent=new Intent(getApplicationContext(),DisplayVolunteers.class);
                                intent.putExtra("EventDetails",passThis);
                                startActivity(intent);
                            }
                        });
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        }, 2000);

        //CHANGED TIME FROM 5000 To 2000

    }
}