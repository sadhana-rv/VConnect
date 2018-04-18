package com.example.sadhanaravoori.firebasevconnect;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class VolunteerInfo extends AppCompatActivity {

    EditText name,phone,age;
    CheckBox teaching,volunteering,donating,artistic;

    private DatabaseReference fire=FirebaseDatabase.getInstance().getReference().child("Volunteer");

    Button submit;

    String n,p,a;     //Put into Database

    RadioGroup gender;
    RadioButton g;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volunteer_info);

        final Bundle bundle=getIntent().getExtras();

        name=(EditText)findViewById(R.id.name);
        phone=(EditText)findViewById(R.id.phone);
        age=(EditText)findViewById(R.id.age);

        gender = (RadioGroup)findViewById(R.id.gender);
        gender.clearCheck();

        gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                g = (RadioButton) group.findViewById(checkedId);
            }

        });

        donating=(CheckBox)findViewById(R.id.donating);
        volunteering=(CheckBox)findViewById(R.id.volunteering);
        teaching=(CheckBox)findViewById(R.id.teaching);
        artistic=(CheckBox)findViewById(R.id.artistic);


        submit=(Button)findViewById(R.id.submit);
        Log.e("Button","Got");
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                n=name.getText().toString().trim();
                p=phone.getText().toString().trim();
                a=age.getText().toString().trim();

                String email=bundle.getString("email");
                String ans=email.replace('.',' ');

                HashMap<String, String>datamap= new HashMap<String ,String>();
                datamap.put("Name",n);
                datamap.put("Age",a);
                datamap.put("Phone",p);
                datamap.put("Gender",g.getText().toString());

                fire.child(ans).setValue(datamap);

                Bundle sendEmail=new Bundle();
                sendEmail.putString("Email",ans);

                Intent intent = new Intent(getApplicationContext(), VolunteerHomeLocation.class);
                intent.putExtras(sendEmail);
                startActivity(intent);

            }
        });

    }
}