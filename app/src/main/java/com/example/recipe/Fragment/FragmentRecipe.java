package com.example.recipe.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;


import com.example.recipe.Adapter.AdapterRecipe;
import com.example.recipe.MainActivity;
import com.example.recipe.Model.Recipe;
import com.example.recipe.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class FragmentRecipe extends Fragment {

    private MainActivity mainActivity;
    private Toolbar toolbar;
    ArrayList<Recipe> reipelist;
    AdapterRecipe recipeadapter;
    private DatabaseReference fDatabase;
    RecyclerView recyclerView;



    public void onAttach(Context context) {
        super.onAttach(context);
        mainActivity = (MainActivity) context;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.recipe_list, container, false);
        setHasOptionsMenu(true);

 //////////////////////////////////////////////////////////////////////////////////////////////////////
        recyclerView = rootView.findViewById(R.id.rv_recipe);


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        reipelist = new ArrayList<>();

        //recyclerView.setLayoutManager(new LinearLayoutManager(mainActivity));

        fDatabase = FirebaseDatabase.getInstance().getReference("Recipe");

        GridLayoutManager gridLayoutManager = new GridLayoutManager(mainActivity,1);
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
                recipeadapter = new AdapterRecipe(mainActivity,reipelist);
                recyclerView.setAdapter(recipeadapter);
                recipeadapter.notifyDataSetChanged();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




        toolbar = (Toolbar) rootView.findViewById(R.id.layout_toolbar);
        setupToolbar();

        showFloatingActionButton(true);

        return rootView;
    }


    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.search, menu);

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



    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mainActivity.setupNavigationDrawer(toolbar);
    }

    private void setupToolbar() {

        mainActivity.setSupportActionBar(toolbar);
    }

    private void showFloatingActionButton(boolean visible) {
        final FloatingActionButton fab = getActivity().findViewById(R.id.fab);

        if (visible) {
            fab.show();
        } else {
            fab.hide();
        }
    }



}
