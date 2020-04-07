package com.example.recipe;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import android.graphics.Color;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {
    private String[] mPlanetTitles;
    private ListView mDrawerList;

    private DrawerLayout drawerLayout;
    private NavigationView navigation_view;
    private Toolbar toolbar;
    private android.widget.SearchView sv;


    Button b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_Layout);
        navigation_view = (NavigationView) findViewById(R.id.navigation_view);
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        sv = findViewById(R.id.SV_textsearch);
        sv.setQueryHint("Searching your Recipe");
        sv.setIconifiedByDefault(false);
        sv.setBackgroundColor(Color.parseColor("#FFFFFF"));



//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Name, email address, and profile photo Url
            String name = user.getDisplayName();
            String email = user.getEmail();

            // Check if user's email is verified
            boolean emailVerified = user.isEmailVerified();

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getIdToken() instead.
            String uid = user.getUid();
        }



/////////////////////////Menu///////////////////////////////////////////////////////////////////////////////
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
//////////////////////////////Menu//////////////////////////////////////////////////////////////////////////////////////////////////////


        navigation_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                drawerLayout.closeDrawer(GravityCompat.START);

                int id = item.getItemId();

                if (id == R.id.action_home) {
                    Toast.makeText(MainActivity.this, "Main", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, MainActivity.class);
                    startActivity(intent);
                    return true;
                }
                else if (id == R.id.action_help) {

                    Toast.makeText(MainActivity.this, "Help", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, Createac.class);
                    startActivity(intent);
                    return true;
                }else if (id == R.id.action_login){
                    Toast.makeText(MainActivity.this, "login", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, login.class);
                    startActivity(intent);
                    return true;
                }else if (id == R.id.action_favor) {
                    Toast.makeText(MainActivity.this, "Favor", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, Createac.class);
                    startActivity(intent);
                    return true;
                }else if (id == R.id.action_settings) {
                    Toast.makeText(MainActivity.this, "Setting", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, login.class);
                    startActivity(intent);
                    return true;
                }

                return false;
            }
        });


////////////////////////////////////////////////////////////////////////////////////////////////////////



    }
}
