package com.example.quiz;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class NameInputFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_name_input, container, false);
        Button playBtn1 = view.findViewById(R.id.playBtn1);
        playBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainerView, QuizArea.class, null)
                        .setReorderingAllowed(true)
                        .addToBackStack("nameInputArea") // name can be null
                        .commit();
            }
        });
        return view;
    }
}