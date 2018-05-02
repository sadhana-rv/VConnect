package com.example.sadhanaravoori.firebasevconnect;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DisplayOrganizations extends AppCompatActivity {

    TextView adminName,desc,orgName, address, emailText, phone;

    DatabaseReference theReference;
    DatabaseReference disableReg;
    int noOfVolunteers, i;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_organizations);

        mAuth= FirebaseAuth.getInstance();
        String email=mAuth.getCurrentUser().getEmail();
        final String user=email.replace('.',' ');

        adminName=(TextView)findViewById(R.id.NameOfAdmin);
        orgName=(TextView)findViewById(R.id.NameOfOrganization);
        desc=(TextView)findViewById(R.id.Description);
        address=(TextView)findViewById(R.id.Address);
        emailText=(TextView)findViewById(R.id.AdminEmail);
        phone=(TextView)findViewById(R.id.phone);

        final OrgDetailsToDisplay e=(OrgDetailsToDisplay) getIntent().getSerializableExtra("OrgDetailsToDisplay");

        theReference= FirebaseDatabase.getInstance().getReferenceFromUrl(e.getReference());

        adminName.setText(e.getNameOfAdmin());
        orgName.setText(e.getNameOfOrganization());
        desc.setText(e.getDescription());
        address.setText(e.getAddress());
        emailText.setText(e.getEmail());
        phone.setText(e.getPhone());
    }
}
