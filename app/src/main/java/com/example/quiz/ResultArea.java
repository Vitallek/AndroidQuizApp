package com.example.quiz;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class ResultArea extends Fragment {

    private TextView congratulationsText;
    private Bundle bundle;
    private RecyclerView playerList;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<PlayerDBmodel> playerListFromDb;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_result_area, container, false);
        congratulationsText = view.findViewById(R.id.congratulationsText);
        playerList = view.findViewById(R.id.playerList);

        DBHandler dbHandler = new DBHandler(getActivity());

        bundle = getArguments();
        if(bundle != null) {
            System.out.println(bundle);
            String playerName = bundle.getString("PlayerName");
            int correctAnswers = bundle.getInt("correctAnswers");
            int startTime = bundle.getInt("startTime");
            int finishTime = bundle.getInt("finishTime");

            int score = correctAnswers * 100000 - (finishTime - startTime)/10;

            congratulationsText.setText("Congratulations, " + playerName + ", you have "+ correctAnswers + " correct answers!" +"\n Your score is: " + score);
            congratulationsText.setVisibility(View.VISIBLE);

            dbHandler.addNewField(playerName,score);
        }


        playerListFromDb = dbHandler.getPlayers();
        //fill list
        playerList.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        playerList.setLayoutManager(layoutManager);

        mAdapter = new PlayerListAdapter(playerListFromDb, getActivity());
        playerList.setAdapter(mAdapter);

        System.out.println(playerListFromDb);

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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        congratulationsText.setVisibility(View.GONE);
    }
}