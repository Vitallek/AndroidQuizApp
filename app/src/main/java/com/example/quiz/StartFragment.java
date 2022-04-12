package com.example.quiz;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class StartFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_start, container, false);
        Button startQuizBtn = view.findViewById(R.id.startQuizBtn);
        startQuizBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainerView, NameInputFragment.class, null)
                        .setReorderingAllowed(true)
                        .addToBackStack("startArea") // name can be null
                        .commit();
            }
        });
        Button resultsBtn1 = view.findViewById(R.id.resultsBtn1);
        resultsBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainerView, ResultArea.class, null)
                        .setReorderingAllowed(true)
                        .addToBackStack("startArea") // name can be null
                        .commit();
            }
        });
        return view;
    }
}