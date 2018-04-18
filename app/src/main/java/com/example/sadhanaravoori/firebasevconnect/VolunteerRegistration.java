package com.example.sadhanaravoori.firebasevconnect;

import android.content.Intent;
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

public class VolunteerRegistration extends AppCompatActivity {

    private Button b1;
    private EditText email,pass;
    private FirebaseAuth mAuth;
    private TextView t1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volunteer_registration);

        b1=(Button)findViewById(R.id.submit);
        email=(EditText)findViewById(R.id.email);
        pass=(EditText)findViewById(R.id.password);
        t1=(TextView)findViewById(R.id.login);

        final Bundle oldBundle=getIntent().getExtras();

        mAuth= FirebaseAuth.getInstance();

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(email.getText().toString().trim().isEmpty() || pass.getText().toString().trim().isEmpty())
                {
                    Toast.makeText(getApplicationContext(),"Please fill in both email and pasword",Toast.LENGTH_LONG).show();
                }
                else
                {
                    mAuth.createUserWithEmailAndPassword(email.getText().toString().trim(),pass.getText().toString().trim()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()) {
                                finish();
                                Toast.makeText(getApplicationContext(), "Successful Registration!", Toast.LENGTH_LONG).show();
                                String voladmin=oldBundle.getString("VolunteerOrAdmin");;
                                Bundle bundle=new Bundle();
                                bundle.putString("email",email.getText().toString().trim());

                                if(voladmin.equals("Volunteer"))
                                {
                                    Intent intent = new Intent(getApplicationContext(), VolunteerInfo.class);
                                    intent.putExtras(bundle);
                                    startActivity(intent);
                                }
                                else if(voladmin.equals("Admin"))
                                {
                                    Intent intent = new Intent(getApplicationContext(), OrganizationDetails.class);
                                    intent.putExtras(bundle);
                                    startActivity(intent);
                                    Log.e("Finish","Yes");
                                }

                            }
                        }
                    });
                }

            }
        });


        t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(), LoginPage.class);
                startActivity(intent);
            }
        });
    }
}
