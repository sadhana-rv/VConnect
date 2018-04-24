package com.example.sadhanaravoori.firebasevconnect;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class DisplayEvents extends AppCompatActivity {


    TextView eventName,desc,orgName,dateEvent, timeEvent, distanceKm;
    Button register;
    DatabaseReference theReference;
    DatabaseReference disableReg;
    int noOfVolunteers, i;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_events);

        mAuth= FirebaseAuth.getInstance();
        String email=mAuth.getCurrentUser().getEmail();
        final String user=email.replace('.',' ');

        eventName=(TextView)findViewById(R.id.NameOfEvent);
        orgName=(TextView)findViewById(R.id.NameOfOrganization);
        desc=(TextView)findViewById(R.id.DescriptionOfEvent);
        dateEvent=(TextView)findViewById(R.id.Date);
        timeEvent=(TextView)findViewById(R.id.Time);
        distanceKm=(TextView)findViewById(R.id.Distance);

        register=(Button)findViewById(R.id.Register);
        final EventDetails e=(EventDetails) getIntent().getSerializableExtra("EventDetails");

        theReference= FirebaseDatabase.getInstance().getReferenceFromUrl(e.getReference());
        Log.e("DisplayEventsStuff",theReference.toString());

        eventName.setText(e.getNameOfEvent());
        orgName.setText(e.getNameOfOrganization());
        desc.setText(e.getDescriptionOfEvent());
        dateEvent.setText(e.getDate());
        timeEvent.setText(e.getTime());
        distanceKm.setText(e.distance+"");
        noOfVolunteers=e.getNoOfVolunteersRegistered();

        Log.e("DisplayEventsStuff",noOfVolunteers+"");

        disableReg=FirebaseDatabase.getInstance().getReferenceFromUrl(e.getReference()).child("VolunteersList");
        disableReg.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String volEmailFromDatabase=dataSnapshot.getValue(String.class);
                if(volEmailFromDatabase.equals(user))
                {
                    register.setText("Registered For Event!");
                    register.setEnabled(false);
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

        final DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        //Yes button clicked
                        e.setNoOfVolunteersRegistered(noOfVolunteers+1);
                        theReference.child("NoOfVolunteersRegistered").setValue(noOfVolunteers+1);
                        theReference.child("VolunteersList").push().setValue(user);
                        //register.setText("Registered For Event!");
                        //register.setEnabled(false);
                        Toast.makeText(getApplicationContext(),"Successful Registration",Toast.LENGTH_LONG).show();
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        break;
                }
            }
        };

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener).setNegativeButton("No", dialogClickListener).show();
            }
        });

        Log.e("Details",e.getNameOfEvent());
        Log.e("Details",e.getNoOfVolunteersRegistered()+"");

    }
}