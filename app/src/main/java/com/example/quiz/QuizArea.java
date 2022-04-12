package com.example.quiz;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class QuizArea extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_quiz_area, container, false);
        Button continueQuizBtn = view.findViewById(R.id.continueQuizBtn);
        continueQuizBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainerView, ResultArea.class, null)
                        .setReorderingAllowed(true)
                        .addToBackStack("QuizArea") // name can be null
                        .commit();
            }
        });
        return view;
    }
}