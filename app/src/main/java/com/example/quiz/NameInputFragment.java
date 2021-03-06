package com.example.quiz;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NameInputFragment extends Fragment {

    private Bundle bundle = new Bundle();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_name_input, container, false);
        Button playBtn1 = view.findViewById(R.id.playBtn1);
        EditText playerNameInputField = view.findViewById(R.id.playerNameInputField);

        playBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!playerNameInputField.getText().toString().isEmpty() && playerNameInputField.getText().length() <= 20){
                    String playerNameStr = playerNameInputField.getText().toString();

                    bundle.putString("PlayerName", playerNameStr);
                    QuizArea quizArea = new QuizArea();
                    quizArea.setArguments(bundle);

                    getFragmentManager().beginTransaction()
                            .setCustomAnimations(R.anim.enter_right_to_left,R.anim.exit_right_to_left,R.anim.enter_left_to_right, R.anim.exit_left_to_right)
                            .replace(R.id.fragmentContainerView, quizArea)
                            .addToBackStack("nameInputArea") // name can be null
                            .commit();
                } else {
                    Toast.makeText(getActivity(),"Name should have length greater than 0 and less than 20 characters",Toast.LENGTH_SHORT).show();
                }
            }

        });

        return view;
    }
}