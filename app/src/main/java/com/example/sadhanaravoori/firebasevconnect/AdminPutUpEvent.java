package com.example.sadhanaravoori.firebasevconnect;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class AdminPutUpEvent extends AppCompatActivity {
    private EditText nameofevent;
    private EditText dateofevent;
    private EditText discriptionofevent;

    private EditText timeofevent, minVol, maxVol;
    private FirebaseAuth mAuth;
    private Button submit;

    private Firebase adminFire, orgFire;
    // Write a message to the database
    private DatabaseReference fire = FirebaseDatabase.getInstance().getReference().child("Events");

    String email, user, address;
    String nameOfOrganization;

    float adminLat,adminLong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_put_up_event);

        mAuth= FirebaseAuth.getInstance();
        email=mAuth.getCurrentUser().getEmail();
        user=email.replace('.',' ');

        //Get the volunteers location from the database
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                Log.e("hi","hello");
            }
        },5000);

        Log.e("detailsandstuff",user);
        adminFire=new Firebase("https://fir-vconnect.firebaseio.com/Administrator/"+user);

        adminFire.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String, String> map=dataSnapshot.getValue(Map.class);
                String lat=map.get("Latitude");
                String lon=map.get("Longitude");

                adminLat=Float.parseFloat(lat);
                adminLong=Float.parseFloat(lon);
                Log.e("volunteersLat",adminLat+" "+adminLong);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        orgFire=new Firebase("https://fir-vconnect.firebaseio.com/Administrator/"+user);
        orgFire.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String, String> map=dataSnapshot.getValue(Map.class);
                nameOfOrganization=map.get("Name Of Organization");
                address=map.get("Address");
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        nameofevent = (EditText) findViewById(R.id.nameofevent);
        dateofevent = (EditText) findViewById(R.id.dateofevent);
        discriptionofevent = (EditText) findViewById(R.id.discriptionofevent);
        minVol = (EditText) findViewById(R.id.minVol);
        maxVol= (EditText) findViewById(R.id.maxVol);
        submit = (Button) findViewById(R.id.submit);
        timeofevent = (EditText) findViewById(R.id.timeofevent);
        submit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                addarraylist();
            }
        });

    }

    private void addarraylist() {
        String nameofevent1 = nameofevent.getText().toString().trim();
        String dateofevent1 = dateofevent.getText().toString().trim();
        String timeofevent1 = timeofevent.getText().toString().trim();
        String discriptionofevent1 = discriptionofevent.getText().toString().trim();
        String minimum =  minVol.getText().toString().trim();
        String maximum= maxVol.getText().toString().trim();

        if(TextUtils.isEmpty(nameofevent1)) {
            Toast.makeText(this, "Please enter the name of the event",Toast.LENGTH_LONG).show();
        }else if (TextUtils.isEmpty(dateofevent1)){

            Toast.makeText(this, "Please enter the date of the event",Toast.LENGTH_LONG).show();
        }
        else if (TextUtils.isEmpty(timeofevent1)){
            Toast.makeText(this, "Please enter the time of the event",Toast.LENGTH_LONG).show();
        }
        else if (TextUtils.isEmpty(discriptionofevent1)){


            Toast.makeText(this, "Please enter the discription of the event",Toast.LENGTH_LONG).show();
        }
        else if (TextUtils.isEmpty(minimum)){

            Toast.makeText(this, "Please enter no of volunteers required for the event",Toast.LENGTH_LONG).show();
        }
        else {
            HashMap<String, String> datamap = new HashMap<String, String>();
            datamap.put("NameOfEvent", nameofevent1);
            datamap.put("Date", dateofevent1);
            datamap.put("Time", timeofevent1);
            datamap.put("DescriptionOfEvent", discriptionofevent1);
            datamap.put("NoOfVolunteersMin", minimum);
            datamap.put("NoOfVolunteersMax", maximum);
            datamap.put("NoOfVolunteersRegistered", "0");
            datamap.put("Latitude", adminLat+"");
            datamap.put("Longitude", adminLong+"");
            datamap.put("AdminEmail", user);
            datamap.put("NameOfOrganization", nameOfOrganization);
            datamap.put("Address",address);
            fire.push().setValue(datamap);
        }
    }
}