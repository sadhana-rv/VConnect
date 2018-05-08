package com.example.sadhanaravoori.firebasevconnect;

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
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class VolunteerViewOrganization extends AppCompatActivity {

    private Firebase fire;
    private Firebase events;
    private FirebaseAuth mAuth;
    private DatabaseReference theReference;
    float volLat, volLong;

    Button button;
    String searchLocation;
    EditText searchBox;


    DatabaseReference databaseReference;

    List<OrgDetailsToDisplay> list;
    public List<OrgDetailsToDisplay> sortedList;

    RecyclerView recyclerView;

    RecyclerViewAdapterOrganization adapter ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volunteer_view_organization);

        button=(Button)findViewById(R.id.searchButton);
        searchBox=(EditText)findViewById(R.id.search);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(VolunteerViewOrganization.this));

        mAuth= FirebaseAuth.getInstance();

        //USER HAS . IN THEIR EMAIL
        String email=mAuth.getCurrentUser().getEmail();
        String user=email.replace('.',' ');

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
                databaseReference= FirebaseDatabase.getInstance().getReference().child("Administrator");
                databaseReference.addValueEventListener(new com.google.firebase.database.ValueEventListener() {
                    @Override
                    public void onDataChange(com.google.firebase.database.DataSnapshot snapshot) {

                        list = new ArrayList<OrgDetailsToDisplay>();
                        Iterable<com.google.firebase.database.DataSnapshot> AllEvents = snapshot.getChildren();

                        for (com.google.firebase.database.DataSnapshot dataSnapshot1 : AllEvents) {

                            OrgDetailsToDisplay value = dataSnapshot1.getValue(OrgDetailsToDisplay.class);
                            theReference=dataSnapshot1.getRef();
                            Log.e("details",theReference.toString());
                            Log.d("Keyyyy", dataSnapshot1.getValue().toString());
                            value.setReference(theReference.toString());
                            value.setNameOfOrganization(dataSnapshot1.child("Name Of Organization").getValue().toString());
                            value.setNameOfAdmin(dataSnapshot1.child("Name Of Admin").getValue().toString());
                            value.setDescription(dataSnapshot1.child("Description").getValue().toString());
                            value.setEmail(dataSnapshot1.child("Email").getValue().toString());
                            value.setLatitude(Float.parseFloat(dataSnapshot1.child("Latitude").getValue().toString()));
                            value.setLongitude(Float.parseFloat(dataSnapshot1.child("Longitude").getValue().toString()));
                            value.setAddress(dataSnapshot1.child("Address").getValue().toString());
                            value.setPhone(dataSnapshot1.child("Phone").getValue().toString());
                            value.setImageUrl(dataSnapshot1.child("Image Url").getValue().toString());

                            OrgDetailsToDisplay ed = new OrgDetailsToDisplay();
                            String nameOfAdmin=value.getNameOfAdmin();
                            String nameOfOrganization=value.getNameOfOrganization();
                            String description=value.getDescription();
                            float latitude=value.getLatitude();
                            float longitude=value.getLongitude();
                            String emailOfAdmin=value.getEmail();
                            String address=value.getAddress();
                            String phone=value.getPhone();
                            String imgUrl=value.getImageUrl();

                            //ed.setReference(fire);
                            ed.setImageUrl(imgUrl);
                            ed.setDescription(description);
                            ed.setNameOfOrganization(nameOfOrganization);
                            ed.setNameOfAdmin(nameOfAdmin);
                            ed.setLatitude(latitude);
                            ed.setLongitude(longitude);
                            ed.setAddress(address);
                            ed.setEmail(emailOfAdmin);
                            ed.setPhone(phone);

                            ed.findDistance(volLat,volLong);
                            ed.setReference(theReference.toString());
                            Log.e("details",ed.toString());

                            list.add(ed);
                        }

                        sortedList = new ArrayList<OrgDetailsToDisplay>();
                        Collections.sort(list);
                        for(OrgDetailsToDisplay obj: list) {
                            Log.e("Sorted", obj.toString());
                            sortedList.add(obj);
                        }

                        adapter = new RecyclerViewAdapterOrganization(VolunteerViewOrganization.this, sortedList);
                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();

                        adapter.setOnItemClickListener(new RecyclerViewAdapterOrganization.OnItemClickedListener() {
                            @Override
                            public void onItemClick(int position) {
                                OrgDetailsToDisplay passThis=sortedList.get(position);

                                Intent intent=new Intent(getApplicationContext(),DisplayOrganizations.class);
                                intent.putExtra("OrgDetailsToDisplay",passThis);
                                startActivity(intent);
                            }
                        });
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        searchLocation=searchBox.getText().toString();

                        databaseReference= FirebaseDatabase.getInstance().getReference().child("Administrator");
                        databaseReference.addValueEventListener(new com.google.firebase.database.ValueEventListener() {
                            @Override
                            public void onDataChange(com.google.firebase.database.DataSnapshot snapshot) {

                                list = new ArrayList<OrgDetailsToDisplay>();
                                Iterable<com.google.firebase.database.DataSnapshot> AllEvents = snapshot.getChildren();

                                for (com.google.firebase.database.DataSnapshot dataSnapshot1 : AllEvents) {

                                    OrgDetailsToDisplay value = dataSnapshot1.getValue(OrgDetailsToDisplay.class);
                                    theReference=dataSnapshot1.getRef();
                                    Log.e("details",theReference.toString());
                                    Log.d("Keyyyy", dataSnapshot1.getValue().toString());
                                    value.setReference(theReference.toString());
                                    value.setNameOfOrganization(dataSnapshot1.child("Name Of Organization").getValue().toString());
                                    value.setNameOfAdmin(dataSnapshot1.child("Name Of Admin").getValue().toString());
                                    value.setDescription(dataSnapshot1.child("Description").getValue().toString());
                                    value.setEmail(dataSnapshot1.child("Email").getValue().toString());
                                    value.setLatitude(Float.parseFloat(dataSnapshot1.child("Latitude").getValue().toString()));
                                    value.setLongitude(Float.parseFloat(dataSnapshot1.child("Longitude").getValue().toString()));
                                    value.setAddress(dataSnapshot1.child("Address").getValue().toString());
                                    value.setPhone(dataSnapshot1.child("Phone").getValue().toString());
                                    value.setImageUrl(dataSnapshot1.child("Image Url").getValue().toString());

                                    OrgDetailsToDisplay ed = new OrgDetailsToDisplay();
                                    String nameOfAdmin=value.getNameOfAdmin();
                                    String nameOfOrganization=value.getNameOfOrganization();
                                    String description=value.getDescription();
                                    float latitude=value.getLatitude();
                                    float longitude=value.getLongitude();
                                    String emailOfAdmin=value.getEmail();
                                    String address=value.getAddress();
                                    String phone=value.getPhone();

                                    //ed.setReference(fire);
                                    String imgUrl=value.getImageUrl();

                                    ed.setImageUrl(imgUrl);
                                    ed.setDescription(description);
                                    ed.setNameOfOrganization(nameOfOrganization);
                                    ed.setNameOfAdmin(nameOfAdmin);
                                    ed.setLatitude(latitude);
                                    ed.setLongitude(longitude);
                                    ed.setAddress(address);
                                    ed.setEmail(emailOfAdmin);
                                    ed.setPhone(phone);

                                    ed.findDistance(volLat,volLong);
                                    ed.setReference(theReference.toString());
                                    Log.e("details",ed.toString());

                                    if(ed.getAddress().toLowerCase().contains(searchLocation.toLowerCase()) || ed.getNameOfOrganization().toLowerCase().contains(searchLocation.toLowerCase()))
                                        list.add(ed);
                                }

                                sortedList = new ArrayList<OrgDetailsToDisplay>();
                                Collections.sort(list);
                                for(OrgDetailsToDisplay obj: list) {
                                    Log.e("Sorted", obj.toString());
                                    sortedList.add(obj);
                                }

                                adapter = new RecyclerViewAdapterOrganization(VolunteerViewOrganization.this, sortedList);
                                recyclerView.setAdapter(adapter);
                                adapter.notifyDataSetChanged();

                                adapter.setOnItemClickListener(new RecyclerViewAdapterOrganization.OnItemClickedListener() {
                                    @Override
                                    public void onItemClick(int position) {
                                        OrgDetailsToDisplay passThis=sortedList.get(position);

                                        Intent intent=new Intent(getApplicationContext(),DisplayOrganizations.class);
                                        intent.putExtra("OrgDetailsToDisplay",passThis);
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
        }, 5000);


    }
}