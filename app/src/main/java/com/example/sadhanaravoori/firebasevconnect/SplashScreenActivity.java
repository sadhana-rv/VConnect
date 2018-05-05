package com.example.sadhanaravoori.firebasevconnect;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SplashScreenActivity extends AppCompatActivity {

    private int SLEEP_TIMER = 3;
    private FirebaseAuth mAuth=FirebaseAuth.getInstance();
    DatabaseReference fire;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash_screen);

        setContentView(R.layout.activity_splash_screen);
        getSupportActionBar().hide();
        LogoLauncher logoLauncher = new LogoLauncher();
        logoLauncher.start();

    }

    private class LogoLauncher extends Thread{
        public void run(){
            try{
                sleep(1000 * SLEEP_TIMER);
            }catch(InterruptedException e){
                e.printStackTrace();
            }

            if(mAuth.getCurrentUser() != null)
            {
                String email=mAuth.getCurrentUser().getEmail();
                final String user=email.replace('.',' ');
                Log.e("fireChild",user);

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
                                SplashScreenActivity.this.finish();
                                startActivity(new Intent(getApplicationContext(), AdminPutUpEvent.class));
                            }

                        }

                        DataSnapshot volunteers = dataSnapshot.child("Volunteer");
                        Iterable<DataSnapshot> AllVolunteers = volunteers.getChildren();
                        for (DataSnapshot var1 : AllVolunteers) {
                            String emailFromFirebase = var1.getKey().toString();

                            if (user.equals(emailFromFirebase)) {
                                SplashScreenActivity.this.finish();
                                startActivity(new Intent(getApplicationContext(), VolunteerViewEvents.class));
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }

            else {
                Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
                startActivity(intent);
                SplashScreenActivity.this.finish();
            }
        }
    }
}