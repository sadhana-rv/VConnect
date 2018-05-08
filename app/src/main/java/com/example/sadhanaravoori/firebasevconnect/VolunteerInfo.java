package com.example.sadhanaravoori.firebasevconnect;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class VolunteerInfo extends AppCompatActivity {

    EditText name,phone,age;
    int flag=1;

    private DatabaseReference fire=FirebaseDatabase.getInstance().getReference().child("Volunteer");

    Button submit;

    String n,p,a;     //Put into Database

    RadioGroup gender;
    RadioButton g;

    public static VolunteerInfo vi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volunteer_info);

        vi = this;

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


        submit=(Button)findViewById(R.id.submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flag=1;
                n=name.getText().toString().trim();
                p=phone.getText().toString().trim();
                a=age.getText().toString().trim();

                if(n==null || p==null || a==null || n.equals("") || a.equals("") || p.equals("")) {
                    Toast.makeText(getApplicationContext(), "Please fill in all the details!", Toast.LENGTH_LONG).show();
                    flag = 0;
                }
                else if(p.length()<10){
                    flag=0;
                    Toast.makeText(getApplicationContext(), "Enter valid phone number!", Toast.LENGTH_LONG).show();
                }


                try {
                    if (g.getText().toString().equals("") || g.getText().toString() == null)
                        Toast.makeText(getApplicationContext(), "Please fill in all the details!", Toast.LENGTH_LONG).show();
                }
                catch (Exception e){
                    flag=0;
                    Toast.makeText(getApplicationContext(), "Please fill in all the details!", Toast.LENGTH_LONG).show();
                }

                if(flag==1) {
                    String email=bundle.getString("email");
                    String ans= email.replaceAll("\\.", " ");
                    if(ans==null)
                        ans=email;

                    Bundle sendEmail=new Bundle();
                    sendEmail.putString("Email",ans);
                    sendEmail.putString("Name",n);
                    sendEmail.putString("Age",a);
                    sendEmail.putString("Phone",p);
                    sendEmail.putString("Gender", g.getText().toString());

                    Intent intent = new Intent(getApplicationContext(), VolunteerHomeLocation.class);
                    intent.putExtras(sendEmail);
                    startActivity(intent);
                }

            }
        });

    }
}