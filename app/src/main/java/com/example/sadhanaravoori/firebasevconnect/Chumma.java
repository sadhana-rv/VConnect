package com.example.sadhanaravoori.firebasevconnect;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;

public class Chumma extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout drawer;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chumma);

        Toolbar toolbar= (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer= (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navigationView= (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new EventsFragment()).commit();

        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(Chumma.this, drawer,toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Vol_Quote_Fragment()).commit();


        //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new EventsFragment()).commit();
        navigationView.setCheckedItem(R.id.nav_home);
    }

    @Override
    public void onBackPressed(){
        if (drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {


        switch (item.getItemId()) {
            case R.id.nav_events:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new EventsFragment()).commit();
                Intent intent=new Intent(this, VolunteerViewEvents.class);
                startActivity(intent);
                break;

            case R.id.nav_registered_events:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new RegisteredEventsFragment()).commit();
                Intent intent1=new Intent(this, VolunteerViewRegisteredEvents.class);
                startActivity(intent1);
                break;

            case R.id.nav_organizations:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new OrganizationsFragment()).commit();
                Intent intent2=new Intent(this, VolunteerViewOrganization.class);
                startActivity(intent2);
                break;

            case R.id.nav_logout:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new LogoutFragment()).commit();
                Intent intent3=new Intent(this, Logout.class);
                startActivity(intent3);
                break;

            case R.id.nav_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Vol_Quote_Fragment()).commit();
                break;

            case R.id.nav_other_organizations:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new OtherOrgFragment()).commit();
                Intent intent100=new Intent(this, OtherOrganizationDetails.class);
                startActivity(intent100);
                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}