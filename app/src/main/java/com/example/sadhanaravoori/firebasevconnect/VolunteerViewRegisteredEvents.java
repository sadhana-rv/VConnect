package com.example.sadhanaravoori.firebasevconnect;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import static java.lang.Thread.sleep;

public class VolunteerViewRegisteredEvents extends AppCompatActivity {

    private Firebase fire;
    private Firebase events;
    private FirebaseAuth mAuth;
    private DatabaseReference theReference;
    private DatabaseReference volListRef;
    float volLat, volLong;

    Button button;
    String searchLocation;
    EditText searchBox;

    String user;

    int flag=1;

    DatabaseReference databaseReference;

    List<EventDetails> list;
    public List<EventDetails> sortedList;

    RecyclerView recyclerView;

    RecyclerViewAdapter adapter ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volunteer_view_registered_events);

        Log.e("Details","In Volunteer View Register Events");

        button=(Button)findViewById(R.id.searchButton);
        searchBox=(EditText)findViewById(R.id.search);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(VolunteerViewRegisteredEvents.this));

        mAuth= FirebaseAuth.getInstance();

        //USER HAS . IN THEIR EMAIL
        String email=mAuth.getCurrentUser().getEmail();
        user=email.replace('.',' ');

        //Get the volunteers location from the database
        fire=new Firebase("https://fir-vconnect.firebaseio.com/Volunteer/"+user);
        fire.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String, String> volLocation=dataSnapshot.getValue(Map.class);
                String lat=volLocation.get("Latitude");
                String lon=volLocation.get("Longitude");

                volLat=Float.parseFloat(lat);
                volLong=Float.parseFloat(lon);
                Log.e("volunteersLat",volLat+" "+volLong);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

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
                            Log.e("details",theReference.toString());
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

                            value.setAddress(dataSnapshot1.child("Address").getValue().toString());
                            value.setAdminEmail(dataSnapshot1.child("AdminEmail").getValue().toString());

                            Log.d("ERROR EMAIL",dataSnapshot1.child("AdminEmail").getValue().toString());
                            Log.d("ERROR EMAIL",dataSnapshot1.child("Latitude").getValue().toString());

                            final EventDetails ed = new EventDetails();
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
                            String adminEmail=value.getAdminEmail();
                            String address=value.getAddress();

                            ed.setAdminEmail(adminEmail);
                            ed.setAddress(address);
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
                            ed.findDistance(volLat,volLong);
                            ed.setReference(theReference.toString());

                            Log.e("details",ed.toString());

                            String stringDate = ed.getDate();
                            DateFormat format = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
                            try {
                                Date date1=format.parse(stringDate);
                                Date date2=new Date();
                                if(date2.compareTo(date1)==1)
                                {
                                    theReference.removeValue();
                                }
                                Log.e("dateDetails",date2.toString());
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                            volListRef=theReference.child("VolunteersList");
                            Log.e("Details","VolListRef"+volListRef);
                            flag=1;
                            Log.e("Details","Flag Set to "+flag);
                            volListRef.addChildEventListener(new ChildEventListener() {
                                @Override
                                public void onChildAdded(com.google.firebase.database.DataSnapshot dataSnapshot, String s) {
                                    Log.e("Details", "Snapshot "+dataSnapshot.getValue().toString());
                                    Log.e("Details", "USER "+user+"\n");
                                    if(dataSnapshot.getValue().toString().equals(user))
                                    {

                                        list.add(ed);
                                        Log.e("Details","List of objects "+list.toString());
                                        Log.e("Details","LIST.add\n");


                                        sortedList = new ArrayList<EventDetails>();
                                        Collections.sort(list);
                                        for(EventDetails obj: list) {
                                            Log.e("Sorted", obj.toString());
                                            sortedList.add(obj);
                                        }

                                        adapter = new RecyclerViewAdapter(VolunteerViewRegisteredEvents.this, sortedList);
                                        recyclerView.setAdapter(adapter);
                                        adapter.notifyDataSetChanged();

                                        adapter.setOnItemClickListener(new RecyclerViewAdapter.OnItemClickedListener() {
                                            @Override
                                            public void onItemClick(int position) {
                                                EventDetails passThis=sortedList.get(position);

                                                Intent intent=new Intent(getApplicationContext(),DisplayEvents.class);
                                                intent.putExtra("EventDetails",passThis);
                                                startActivity(intent);
                                            }
                                        });
                                    }
                                }

                                @Override
                                public void onChildChanged(com.google.firebase.database.DataSnapshot dataSnapshot, String s) {

                                }

                                @Override
                                public void onChildRemoved(com.google.firebase.database.DataSnapshot dataSnapshot) {

                                }

                                @Override
                                public void onChildMoved(com.google.firebase.database.DataSnapshot dataSnapshot, String s) {

                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        searchLocation=searchBox.getText().toString();

                        databaseReference=FirebaseDatabase.getInstance().getReference().child("Events");
                        databaseReference.addValueEventListener(new com.google.firebase.database.ValueEventListener() {
                            @Override
                            public void onDataChange(com.google.firebase.database.DataSnapshot snapshot) {

                                list = new ArrayList<EventDetails>();
                                Iterable<com.google.firebase.database.DataSnapshot> AllEvents = snapshot.getChildren();

                                for (com.google.firebase.database.DataSnapshot dataSnapshot1 : AllEvents) {

                                    EventDetails value = dataSnapshot1.getValue(EventDetails.class);
                                    theReference=dataSnapshot1.getRef();
                                    Log.e("details",theReference.toString());
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
                                    value.setAddress(dataSnapshot1.child("Address").getValue().toString());
                                    value.setAdminEmail(dataSnapshot1.child("AdminEmail").getValue().toString());

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
                                    String address=value.getAddress();

                                    //ed.setReference(fire);
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
                                    ed.setAddress(address);
                                    ed.setAdminEmail(adminEmail);
                                    ed.findDistance(volLat,volLong);
                                    ed.setReference(theReference.toString());
                                    Log.e("details",ed.toString());

                                    if(ed.getNoOfVolunteersRegistered() < ed.getNoOfVolunteersMax() && (ed.getAddress().toLowerCase().contains(searchLocation.toLowerCase()) || ed.getNameOfOrganization().toLowerCase().contains(searchLocation.toLowerCase()))) {
                                        Log.e("details","ed.add");
                                        list.add(ed);
                                    }
                                }

                                sortedList = new ArrayList<EventDetails>();
                                Collections.sort(list);
                                for(EventDetails obj: list) {
                                    Log.e("Sorted", obj.toString());
                                    sortedList.add(obj);
                                }

                                adapter = new RecyclerViewAdapter(VolunteerViewRegisteredEvents.this, sortedList);
                                recyclerView.setAdapter(adapter);
                                adapter.notifyDataSetChanged();

                                adapter.setOnItemClickListener(new RecyclerViewAdapter.OnItemClickedListener() {
                                    @Override
                                    public void onItemClick(int position) {
                                        EventDetails passThis=sortedList.get(position);

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
                });




            }
        }, 2000);


    }
}
