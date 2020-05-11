package com.example.recipe;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.core.view.MenuItemCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.recipe.Adapter.AdapterLogin;
import com.example.recipe.Adapter.AdapterRecipe;
import com.example.recipe.Fragment.FragmentAbout;
import com.example.recipe.Fragment.FragmentRecipe;
import com.example.recipe.Fragment.FragmentSetting;
import com.example.recipe.Model.Recipe;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private final static String RECIPE_FRAGMENT_TAG = "Recipe";
    private final static String FAVORITE_FRAGMENT_TAG = "favorite";
    private final static String ABOUT_FRAGMENT_TAG = "About";
    private final static String Setting_FRAGMENT_TAG = "Setting";


    @BindView(R.id.layout_toolbar)Toolbar toolbar;
    @BindView(R.id.navigation_view)NavigationView navigationView;
    @BindView(R.id.main_drawer_layout) DrawerLayout drawerLayout;
    TextView uemail;
    TextView uname;
    RecyclerView recyclerView;


    ArrayList<Recipe> reipelist;
    AdapterRecipe recipeadapter;
    AdapterLogin adapterLogin;
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
        FloatingActionButton fab = findViewById(R.id.fab);
        recyclerView = findViewById(R.id.rv_recipe);
        //recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //recyclerView.setAdapter(adapterLogin);

        navigationView.setNavigationItemSelectedListener(this);


        fAuth = FirebaseAuth.getInstance();
        fDatabase = FirebaseDatabase.getInstance().getReference();

        FirebaseUser fuser = fAuth.getCurrentUser();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                startActivity(intent);
            }
        });



        getSupportFragmentManager().beginTransaction().replace(R.id.container_drawer_content,
                new FragmentRecipe(), RECIPE_FRAGMENT_TAG).commit();

        setupToolbar();


    }
 ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

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
        getMenuInflater().inflate(R.menu.search, menu);

        MenuItem searchViewItem = menu.findItem(R.id.search);

        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchViewItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;

            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
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




            case R.id.drawer_Login:
                if (!menuItem.isChecked()) {


                    Intent intent = new Intent(this, Login.class);
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

            case R.id.drawer_setting:
                if (!menuItem.isChecked()) {

                    getSupportFragmentManager().beginTransaction().replace(R.id.container_drawer_content,
                            new FragmentSetting(), Setting_FRAGMENT_TAG).commit();

                    toolbar.setTitle("Setting");

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
