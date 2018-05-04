package com.example.sadhanaravoori.firebasevconnect;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginPage extends AppCompatActivity {

    private TextView t;
    private Button b1;
    private EditText email,pass;
    private FirebaseAuth mAuth;
    private String e,p;

    DatabaseReference fire;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        mAuth=FirebaseAuth.getInstance();

        t=(TextView)findViewById(R.id.te1);
        b1=(Button)findViewById(R.id.submit);
        email=(EditText)findViewById(R.id.email);
        pass=(EditText)findViewById(R.id.password);


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                e=email.getText().toString().trim();
                p=pass.getText().toString().trim();

                mAuth.signInWithEmailAndPassword(e,p).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()) {
                            String email=mAuth.getCurrentUser().getEmail();
                            final String user=email.replace('.',' ');
                            Log.e("fireChild",user);

                            Toast.makeText(getApplicationContext(),"Successful Login!",Toast.LENGTH_LONG).show();
                            fire= FirebaseDatabase.getInstance().getReference();

                            fire.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    //if logged in as admin
                                    DataSnapshot admins = dataSnapshot.child("Administrator");
                                    Iterable<DataSnapshot> AllAdmins = admins.getChildren();
                                    for (DataSnapshot var1 : AllAdmins) {
                                        String emailFromFirebase = var1.getKey().toString();
                                        Log.e("fireChild","From Firebase "+emailFromFirebase);

                                        if (user.equals(emailFromFirebase)) {
                                            LoginPage.this.finish();
                                            startActivity(new Intent(getApplicationContext(), AdminViewEvents.class));
                                        }

                                    }

                                    DataSnapshot volunteers = dataSnapshot.child("Volunteer");
                                    Iterable<DataSnapshot> AllVolunteers = volunteers.getChildren();
                                    for (DataSnapshot var1 : AllVolunteers) {
                                        String emailFromFirebase = var1.getKey().toString();

                                        if (user.equals(emailFromFirebase)) {
                                            LoginPage.this.finish();
                                            startActivity(new Intent(getApplicationContext(), VolunteerViewEvents.class));
                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });

                        }
                        else
                            Toast.makeText(getApplicationContext(),"Incorrect Username or password!",Toast.LENGTH_LONG).show();
                    }
                });

            }
        });

        t.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });



    }
}
