package com.example.recipe.Fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.recipe.MainActivity;
import com.example.recipe.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Locale;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


public class FragmentSetting extends Fragment  {

    private MainActivity mainActivity;
    private Toolbar toolbar;
    Button change,logout;
    private FirebaseAuth mAuth;




    public void onAttach(Context context) {
        super.onAttach(context);
        mainActivity = (MainActivity) context;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.setting, container, false);
        setHasOptionsMenu(true);
        mAuth = FirebaseAuth.getInstance();

        loadLocale();

        change = rootView.findViewById(R.id.btn_changeLanguage);
        logout = rootView.findViewById(R.id.btn_logout);

        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showchangeLanguageDialog();

            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mAuth.getCurrentUser() != null) {
                    mAuth.signOut();
                    Toast.makeText(getContext(), "User Logout" , Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(getContext(), "No user Login" , Toast.LENGTH_LONG).show();
                }

            }
        });




        setupToolbar();
        showFloatingActionButton(false);
        return rootView;
    }


    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main, menu);

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


    private void showchangeLanguageDialog(){
        final String[] Languagelist = {"English", "中文"};
        AlertDialog.Builder mbuilder =  new AlertDialog.Builder(getActivity());
        mbuilder.setTitle("Choose Language... / 選擇語言...");
        mbuilder.setSingleChoiceItems(Languagelist, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (i==0){
                    setLocale("en");
                    FragmentTransaction fs = getFragmentManager().beginTransaction();
                    fs.detach(FragmentSetting.this).attach(FragmentSetting.this).commit();
                }else if(i==1){
                    setLocale("zh");

                }


                dialogInterface.dismiss();
            }
        });

        AlertDialog mDialog = mbuilder.create();
        mDialog.show();

    }


    private void setLocale(String lang){
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config =  new Configuration();
        config.locale = locale;
        getActivity().getResources().updateConfiguration(config, getActivity().getResources().getDisplayMetrics());

        SharedPreferences.Editor editor = getActivity().getPreferences(mainActivity.MODE_PRIVATE).edit();
        editor.putString("Lang",lang);
        editor.apply();
    }


    private void loadLocale(){
        SharedPreferences pref =  getActivity().getPreferences(Activity.MODE_PRIVATE);
        String language =  pref.getString("lang","");
        setLocale(language);
    }


}
