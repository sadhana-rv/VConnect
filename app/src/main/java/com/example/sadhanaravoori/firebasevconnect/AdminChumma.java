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
import android.util.Log;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;

public class AdminChumma extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout drawer;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_chumma);

        Log.e("details","enters adminnnn");

        Toolbar toolbar= (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer= (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navigationView= (NavigationView) findViewById(R.id.admin_nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new EventsFragment()).commit();

        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(AdminChumma.this, drawer,toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new PutUpEventsFragment()).commit();

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
            case R.id.nav_put_up:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new PutUpEventsFragment()).commit();
                Intent intent=new Intent(this, AdminPutUpEvent.class);
                startActivity(intent);
                break;

            case R.id.nav_view_volunteers:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AdminViewVolunteersFragment()).commit();
                Intent intent1=new Intent(this, AdminViewEvents.class);
                startActivity(intent1);
                break;

            case R.id.nav_logout:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new LogoutFragment()).commit();
                Intent intent3=new Intent(this, AdminLogout.class);
                startActivity(intent3);
                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
