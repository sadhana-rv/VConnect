package com.example.sadhanaravoori.firebasevconnect;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;

public class ProfilePage extends AppCompatActivity {

   private FirebaseAuth mAuth;
   private EditText e1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);
        e1=(EditText)findViewById(R.id.edit1box);
        mAuth=FirebaseAuth.getInstance();

        if(mAuth.getCurrentUser() != null)
        {
            e1.setText(mAuth.getCurrentUser().getEmail());
        }
    }
}
