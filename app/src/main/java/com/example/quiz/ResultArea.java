package com.example.quiz;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class ResultArea extends Fragment {

    private TextView congratulationsText;
    private Bundle bundle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_result_area, container, false);
        congratulationsText = view.findViewById(R.id.congratulationsText);

        bundle = getArguments();
        String playerName = bundle.getString("PlayerName");
        int correctAnswers = bundle.getInt("correctAnswers");
        int startTime = bundle.getInt("startTime");
        int finishTime = bundle.getInt("finishTime");

        int score = correctAnswers * 100000 - (finishTime - startTime)/10;

        congratulationsText.setText("Congratulations, " + playerName + ", you have "+ correctAnswers + " correct answers!" +"\n Your score is: " + score);

        DBHandler dbHandler = new DBHandler(getActivity());
        dbHandler.addNewField(playerName,score);


        Button goHomeBtn = view.findViewById(R.id.goHomeBtn);
        goHomeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainerView, StartFragment.class, null)
                        .addToBackStack("ResultArea") // name can be null
                        .commit();
            }
        });
        return view;
    }
}