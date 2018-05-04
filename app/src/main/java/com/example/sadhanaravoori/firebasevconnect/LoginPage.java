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

public class LoginPage extends AppCompatActivity {

    private TextView t;
    private Button b1;
    private EditText email,pass;
    private FirebaseAuth mAuth;
    private String e,p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        mAuth=FirebaseAuth.getInstance();

        t=(TextView)findViewById(R.id.te1);
        b1=(Button)findViewById(R.id.submit);
        email=(EditText)findViewById(R.id.email);
        pass=(EditText)findViewById(R.id.password);

        if(mAuth.getCurrentUser() != null)
        {
            Intent intent = new Intent(this,ProfilePage.class);
            startActivity(intent);
        }

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                e=email.getText().toString().trim();
                p=pass.getText().toString().trim();

                mAuth.signInWithEmailAndPassword(e,p).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()) {
                            finish();
                            Toast.makeText(getApplicationContext(),"Successful Login!",Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(getApplicationContext(),AdminPutUpEvent.class);
                            startActivity(intent);

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
