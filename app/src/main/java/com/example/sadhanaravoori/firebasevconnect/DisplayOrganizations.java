package com.example.sadhanaravoori.firebasevconnect;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DisplayOrganizations extends AppCompatActivity {

    TextView adminName,desc,orgName, address, emailText, phone;

    DatabaseReference theReference;
    DatabaseReference disableReg;
    int noOfVolunteers, i;
    private FirebaseAuth mAuth;
    String imgurl;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_organizations);

        mAuth= FirebaseAuth.getInstance();
        String email=mAuth.getCurrentUser().getEmail();
        final String user=email.replace('.',' ');

        imageView = (ImageView)findViewById(R.id.ImageOfOrganization);
        adminName=(TextView)findViewById(R.id.NameOfAdmin);
        orgName=(TextView)findViewById(R.id.NameOfOrganization);
        desc=(TextView)findViewById(R.id.Description);
        address=(TextView)findViewById(R.id.Address);
        emailText=(TextView)findViewById(R.id.AdminEmail);
        phone=(TextView)findViewById(R.id.phone);

        final OrgDetailsToDisplay e=(OrgDetailsToDisplay) getIntent().getSerializableExtra("OrgDetailsToDisplay");
        imgurl=e.getImageUrl();

        Glide.with(getApplicationContext()).load(imgurl).into(imageView);

        theReference= FirebaseDatabase.getInstance().getReferenceFromUrl(e.getReference());

        adminName.setText(e.getNameOfAdmin());
        orgName.setText(e.getNameOfOrganization());
        desc.setText(e.getDescription());
        address.setText(e.getAddress());
        emailText.setText(e.getEmail());
        phone.setText(e.getPhone());
    }
}
