package com.example.sadhanaravoori.firebasevconnect;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.constraint.solver.ArrayLinkedVariables;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import java.util.Timer;
import java.util.TimerTask;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DisplayVolunteers extends AppCompatActivity {

    DatabaseReference theReference;

    StoredVolunteerInfo obj=new StoredVolunteerInfo();

    private ListView volList;
    private ArrayList<StoredVolunteerInfo> volunteers=new ArrayList<StoredVolunteerInfo>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_volunteers);

        volList=(ListView)findViewById(R.id.listView1);

        final EventDetails e=(EventDetails) getIntent().getSerializableExtra("EventDetails");

        theReference= FirebaseDatabase.getInstance().getReferenceFromUrl(e.getReference()).child("VolunteersList");
        Log.e("DisplayVolStuff",theReference.toString());

        final ArrayAdapter<StoredVolunteerInfo> arrayAdapter=new ArrayAdapter<StoredVolunteerInfo>(this, android.R.layout.simple_list_item_2,android.R.id.text1,volunteers){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);

                TextView text1 = (TextView) view.findViewById(android.R.id.text1);
                TextView text2 = (TextView) view.findViewById(android.R.id.text2);

                text1.setText(volunteers.get(position).getName());

                text2.setText( volunteers.get(position).getEmail() );

                return view;
            }
        };

        volList.setAdapter(arrayAdapter);

        theReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                final String email=dataSnapshot.getValue(String.class);
                DatabaseReference fire=FirebaseDatabase.getInstance().getReference().child("Volunteer").child(email);
                fire.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        if(dataSnapshot.getKey().equals("Name")){

                            String name=dataSnapshot.getValue(String.class);
                            StoredVolunteerInfo obj=new StoredVolunteerInfo();
                            obj.setName(name);
                            obj.setEmail(email);
                            volunteers.add(obj);
                            arrayAdapter.notifyDataSetChanged();
                        }

                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

}