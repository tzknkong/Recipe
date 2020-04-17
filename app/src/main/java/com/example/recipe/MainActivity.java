package com.example.recipe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.recipe.Adapter.AdapterRecipe;
import com.example.recipe.Model.Recipe;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private final static String RECIPE_FRAGMENT_TAG = "Recipe";
    private final static String FAVORITE_FRAGMENT_TAG = "favorite";
    private final static String ABOUT_FRAGMENT_TAG = "About";

    @BindView(R.id.layout_toolbar)Toolbar toolbar;
    @BindView(R.id.navigation_view)NavigationView navigationView;
    @BindView(R.id.main_drawer_layout) DrawerLayout drawerLayout;
    TextView uemail;
    TextView uname;
    RecyclerView recyclerView;


    ArrayList<Recipe> reipelist;
    AdapterRecipe recipeadapter;
    String useemail,usename;
    FirebaseDatabase firebase;
    private FirebaseAuth fAuth;
    private DatabaseReference fDatabase;

    ActionBarDrawerToggle actionBarDrawerToggle;
    android.widget.SearchView sv;
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        uemail = findViewById(R.id.txt_uemail) ;
        uname = findViewById(R.id.txt_uname) ;
        recyclerView = findViewById(R.id.rv_recipe);

        navigationView.setNavigationItemSelectedListener(this);


        fAuth = FirebaseAuth.getInstance();
        fDatabase = FirebaseDatabase.getInstance().getReference();

        FirebaseUser fuser = fAuth.getCurrentUser();


        getSupportFragmentManager().beginTransaction().replace(R.id.container_drawer_content,
                new FragmentRecipe(), RECIPE_FRAGMENT_TAG).commit();

        setupToolbar();
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        reipelist = new ArrayList<>();


        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        fDatabase = FirebaseDatabase.getInstance().getReference("Recipe");

        GridLayoutManager gridLayoutManager = new GridLayoutManager(MainActivity.this,1);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(recipeadapter);

        //////////////////////////////////////////////////////////////////////////////////////////////////////

         fDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                reipelist.clear();

                for (DataSnapshot itemSnapshot : dataSnapshot.getChildren()) {
                    Recipe recipe = itemSnapshot.getValue(Recipe.class);
                    reipelist.add(recipe);
                }
                recipeadapter = new AdapterRecipe(MainActivity.this,reipelist);
                recyclerView.setAdapter(recipeadapter);
                recipeadapter.notifyDataSetChanged();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private void setupToolbar() {

        toolbar.setTitle(R.string.app_name);
        setupNavigationDrawer(toolbar);
    }
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    public void setUserNE(FirebaseUser fuser){

        if (fuser != null) {
            useemail = fuser.getEmail();
            usename = fuser.getDisplayName();

            uemail.setText(useemail);
            uname.setText(usename);
        }
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

            case R.id.drawer_Login:
                if (!menuItem.isChecked()) {

                    Intent intent = new Intent(MainActivity.this, Login.class);
                    startActivity(intent);

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

            case R.id.drawer_add:
                if (!menuItem.isChecked()) {

                    Intent intent = new Intent(MainActivity.this, upload_recipe.class);
                    startActivity(intent);

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
                dialog.setIcon(R.mipmap.bakeicon);
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



}
