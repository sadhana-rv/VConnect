package com.example.sadhanaravoori.firebasevconnect;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class AdminLogout extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_logout);

        final DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        mAuth = FirebaseAuth.getInstance();
                        mAuth.signOut();
                        Toast.makeText(getApplicationContext(),"You have successfully Signed Out!",Toast.LENGTH_LONG).show();
                        Intent intent5=new Intent(getApplicationContext(),MainActivity.class);
                        AdminLogout.this.finish();
                        startActivity(intent5);
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        Intent intent0=new Intent(getApplicationContext(),AdminChumma.class);
                        startActivity(intent0);
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to Logout?").setPositiveButton("Yes", dialogClickListener).setNegativeButton("No", dialogClickListener).show();
    }
}
