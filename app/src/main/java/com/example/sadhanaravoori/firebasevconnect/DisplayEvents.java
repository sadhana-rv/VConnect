package com.example.sadhanaravoori.firebasevconnect;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class DisplayEvents extends AppCompatActivity {

    TextView eventName,desc,orgName,dateEvent, timeEvent, distanceKm;
    Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_events);

        eventName=(TextView)findViewById(R.id.NameOfEvent);
        orgName=(TextView)findViewById(R.id.NameOfOrganization);
        desc=(TextView)findViewById(R.id.DescriptionOfEvent);
        dateEvent=(TextView)findViewById(R.id.Date);
        timeEvent=(TextView)findViewById(R.id.Time);
        distanceKm=(TextView)findViewById(R.id.Distance);

        register=(Button)findViewById(R.id.Register);

        Bundle bundle=getIntent().getExtras();
        String NameOfEvent=bundle.getString("NameOfEvent");
        String DescriptionOfEvent=bundle.getString("DescriptionOfEvent");
        String NameOfOrganization=bundle.getString("NameOfOrganization");
        String date=bundle.getString("Date");
        String time=bundle.getString("Time");
        String distance=bundle.getString("Distance");

        eventName.setText(NameOfEvent);
        orgName.setText(NameOfOrganization);
        desc.setText(DescriptionOfEvent);
        dateEvent.setText(date);
        timeEvent.setText(time);
        distanceKm.setText(distance);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Successful Registration",Toast.LENGTH_LONG).show();
            }
        });

        Log.e("Details",NameOfEvent);
        Log.e("Details",NameOfOrganization);
        Log.e("Details",DescriptionOfEvent);
        Log.e("Details",date);
        Log.e("Details",time);
        Log.e("Details",distance);
    }
}
