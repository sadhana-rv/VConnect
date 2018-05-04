package com.example.sadhanaravoori.firebasevconnect;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class MainActivity extends AppCompatActivity {

    TextView volunteer,admin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final Bundle voladmin=new Bundle();
        volunteer=(TextView)findViewById(R.id.volunteer);
        admin=(TextView)findViewById(R.id.admin);

        volunteer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                voladmin.putString("VolunteerOrAdmin","Volunteer");
                Intent intent=new Intent(getApplicationContext(), VolunteerRegistration.class);
                intent.putExtras(voladmin);
                startActivity(intent);

            }
        });

        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                voladmin.putString("VolunteerOrAdmin","Admin");
                Intent intent=new Intent(getApplicationContext(), VolunteerRegistration.class);
                intent.putExtras(voladmin);
                startActivity(intent);

            }
        });

    }
}