package com.example.recipe.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Locale;
import java.util.ResourceBundle;

import com.example.recipe.Adapter.AdapterAbout;
import com.example.recipe.MainActivity;
import com.example.recipe.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

public class FragmentAbout extends Fragment {
    ListView list;
    String[] titleId;
    String[] subtitleId;
    private MainActivity mainActivity;
    private Toolbar toolbar;

    Integer[] imageId = {
            R.drawable.ic_other_appname,
            R.drawable.ic_about,
            R.drawable.ic_other_email,
            R.drawable.ic_other_privacy

    };

    public FragmentAbout() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mainActivity = (MainActivity) context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.about, container, false);
        setHasOptionsMenu(true);

        toolbar = (Toolbar) rootView.findViewById(R.id.layout_toolbar);


        String currentLanguage = Locale.getDefault().getDisplayLanguage();

        if (currentLanguage.toLowerCase().contains("中文")) {
            titleId = getResources().getStringArray(R.array.chtitle);
            subtitleId = getResources().getStringArray(R.array.chsubtitle);
        }else {
            titleId = getResources().getStringArray(R.array.title);
            subtitleId = getResources().getStringArray(R.array.subtitle);
        }




        AdapterAbout adapter = new AdapterAbout(getActivity(), titleId, subtitleId, imageId);
        list = (ListView) rootView.findViewById(R.id.list);
        list.setAdapter(adapter);

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
}
