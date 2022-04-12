package com.example.quiz;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class ResultArea extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_result_area, container, false);
        Button goHomeBtn = view.findViewById(R.id.goHomeBtn);
        goHomeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainerView, StartFragment.class, null)
                        .setReorderingAllowed(true)
                        .addToBackStack("ResultArea") // name can be null
                        .commit();
            }
        });
        return view;
    }
}