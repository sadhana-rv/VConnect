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
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class AdminViewEvents extends AppCompatActivity {

    private Firebase fire;
    private Firebase events;
    private FirebaseAuth mAuth;
    float adminLat, adminLong;


    DatabaseReference databaseReference;

    List<EventDetails> list;
    public List<EventDetails> sortedList;

    RecyclerView recyclerView;

    RecyclerViewAdapter adapter ;

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

        //Get the volunteers location from the database
        /*
        fire=new Firebase("https://fir-vconnect.firebaseio.com/Admin/"+user);
        fire.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.e("snapshotChildren",dataSnapshot.getChildren().toString());
                Map<String, Map<String, String>> map=dataSnapshot.getValue(Map.class);
                Map<String, String> volLocation=map.get("Location");
                String lat=volLocation.get("Latitude");
                String lon=volLocation.get("Longitude");

                adminLat=Float.parseFloat(lat);
                adminLong=Float.parseFloat(lon);
                Log.e("AdminsLat",adminLat+" "+adminLong);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        */

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
                            Log.d("Keyyyy", dataSnapshot1.getValue().toString());
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

                            //ed.findDistance(volLat,volLong);

                            if(ed.getAdminEmail().equals(user))
                                list.add(ed);
                        }

                        /*sortedList = new ArrayList<EventDetails>();
                        Collections.sort(list);
                        for(EventDetails obj: list) {
                            Log.e("Sorted", obj.toString());
                            sortedList.add(obj);
                        }*/
                        //Close this loop here
                        //Sort the list according to distanceBetween
                        //Assign it to another list and then display the sorted list
                        //adapter = new RecyclerViewAdapter(VolunteerViewEvents.this, sorted_list);

                        adapter = new RecyclerViewAdapter(AdminViewEvents.this, list);
                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();

                        adapter.setOnItemClickListener(new RecyclerViewAdapter.OnItemClickedListener() {
                            @Override
                            public void onItemClick(int position) {
                                String noe=list.get(position).getNameOfEvent();
                                EventDetails passThis=list.get(position);
                                /*
                                Log.e("noeee",noe);
                                Bundle bundle=new Bundle();
                                bundle.putString("NameOfEvent",sortedList.get(position).getNameOfEvent());
                                bundle.putString("NameOfOrganization",sortedList.get(position).getNameOfOrganization());
                                bundle.putString("DescriptionOfEvent",sortedList.get(position).getDescriptionOfEvent());
                                bundle.putString("Date",sortedList.get(position).getDate());
                                bundle.putString("Time",sortedList.get(position).getTime());
                                bundle.putString("Distance",sortedList.get(position).distance/1000+"");
                                bundle.putString("NoOfVolunteersRegistered",sortedList.get(position).getNoOfVolunteersRegistered()+"");
                                bundle.putString("Object",sortedList.get(position).toString());
                                */

                                Intent intent=new Intent(getApplicationContext(),DisplayEvents.class);
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
        }, 5000);



        //COPY DONE




        /*events = new Firebase("https://fir-vconnect.firebaseio.com/EventsFromFirebase");
        events.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });*/

    }
}
