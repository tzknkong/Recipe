package com.example.recipe;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;


import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private final static String RECIPE_FRAGMENT_TAG = "Recipe";
    private final static String FAVORITE_FRAGMENT_TAG = "favorite";
    private final static String ABOUT_FRAGMENT_TAG = "About";


    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private android.widget.SearchView sv;
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);
        drawerLayout = (DrawerLayout) findViewById(R.id.main_drawer_layout);







        getSupportFragmentManager().beginTransaction().replace(R.id.container_drawer_content,
                new FragmentRecipe(), RECIPE_FRAGMENT_TAG).commit();

        setupToolbar();

    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private void setupToolbar() {
        toolbar = (Toolbar) findViewById(R.id.layout_toolbar);
        toolbar.setTitle(R.string.app_name);
        setupNavigationDrawer(toolbar);
    }



////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {


        switch (menuItem.getItemId()) {
            case R.id.drawer_main:
                if (!menuItem.isChecked()) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.container_drawer_content,
                            new FragmentRecipe(), RECIPE_FRAGMENT_TAG).commit();

                    toolbar.setTitle(R.string.drawer_recipes);
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;


            case R.id.drawer_favorite:
                if (!menuItem.isChecked()) {

                    getSupportFragmentManager().beginTransaction().replace(R.id.container_drawer_content,
                            new FragmentFavor(), FAVORITE_FRAGMENT_TAG).commit();

                    toolbar.setTitle(R.string.drawer_favorite);
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;


            case R.id.drawer_about:
                if (!menuItem.isChecked()) {

                    getSupportFragmentManager().beginTransaction().replace(R.id.container_drawer_content,
                            new FragmentAbout(), ABOUT_FRAGMENT_TAG).commit();

                    toolbar.setTitle(R.string.drawer_about);
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;


        }
        return false;
    }
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void setupNavigationDrawer(Toolbar toolbar) {
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.main_drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {

            if (Config.ENABLE_EXIT_DIALOG) {

                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                dialog.setIcon(R.mipmap.ic_launcher);
                dialog.setTitle(R.string.app_name);
                dialog.setMessage("Want to exit?");
                final AlertDialog d = dialog.show();
                dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        MainActivity.this.finish();
                    }
                });


                dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        d.dismiss();
                    }
                });
                dialog.show();

            } else {
                super.onBackPressed();
            }
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

}
