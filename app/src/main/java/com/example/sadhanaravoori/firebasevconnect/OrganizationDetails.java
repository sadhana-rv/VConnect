package com.example.sadhanaravoori.firebasevconnect;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class OrganizationDetails extends AppCompatActivity {

    EditText admin_name, organization_name, role_admin,phone,email,description;

    private DatabaseReference fire= FirebaseDatabase.getInstance().getReference().child("Administrator");

    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organization_details);

        admin_name=(EditText)findViewById(R.id.nameofadmin);
        organization_name=(EditText)findViewById(R.id.nameoforganization);
        role_admin=(EditText)findViewById(R.id.roleofadmin);
        phone=(EditText)findViewById(R.id.phone);
        email=(EditText)findViewById(R.id.email);
        description=(EditText)findViewById(R.id.description);

        submit=(Button)findViewById(R.id.submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap<String, String> datamap= new HashMap<String ,String>();

                datamap.put("Name Of Organization",organization_name.getText().toString().trim());
                datamap.put("Phone", phone.getText().toString().trim());
                datamap.put("Email", email.getText().toString().trim().replace('.',' '));
                datamap.put("Description",description.getText().toString().trim());
                datamap.put("Name Of Admin",admin_name.getText().toString().trim());
                datamap.put("Role Of Admin",role_admin.getText().toString().trim());

                Bundle bundle=getIntent().getExtras();
                String head=bundle.getString("email").replace('.',' ');

                //fire.child(head).setValue(datamap);

                Bundle sendEmail=new Bundle();
                sendEmail.putString("Email",head);
                sendEmail.putString("Name Of Organization",organization_name.getText().toString().trim());
                sendEmail.putString("Phone", phone.getText().toString().trim());
                sendEmail.putString("Email Of Org", email.getText().toString().trim().replace('.',' '));
                sendEmail.putString("Description",description.getText().toString().trim());
                sendEmail.putString("Name Of Admin",admin_name.getText().toString().trim());
                sendEmail.putString("Role Of Admin",role_admin.getText().toString().trim());

                Intent intent=new Intent(getApplicationContext(),MapsAdmin.class);
                intent.putExtras(sendEmail);
                startActivity(intent);

            }
        });


    }
}