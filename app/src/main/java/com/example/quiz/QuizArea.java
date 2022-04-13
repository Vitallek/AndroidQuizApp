package com.example.quiz;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class QuizArea extends Fragment {

    private JSONArray responseToHandle;
    private Button continueQuizBtn;
    private TextView categoryTextView;
    private TextView difficultyTextView;
    private TextView questionArea;
    private ScrollView answersArea;

    public void quizHandler(JSONArray responseToHandle,
                            TextView categoryTextView,
                            TextView difficultyTextView,
                            TextView questionArea,
                            ScrollView answersArea,
                            int answerNumber)
    {
        try {
            JSONObject result = responseToHandle.getJSONObject(answerNumber);
            String category = result.getString("category");
            categoryTextView.setText(category);

            String difficulty = result.getString("difficulty");
            difficultyTextView.setText(difficulty);

            String question = result.getString("question");
            questionArea.setText(question);

            JSONArray incorrectAnswers = result.getJSONArray("incorrect_answers");
            String correctAnswer = result.getString("correct_answer");

            List<String> allAnswers = new ArrayList<String>();
            for(int i =0; i< incorrectAnswers.length(); i++){
                allAnswers.add(incorrectAnswers.getString(i));
            }
            allAnswers.add(correctAnswer);
            Collections.shuffle(allAnswers);


            for(int i = 0; i < allAnswers.size(); i++){
                Button button = new Button(getActivity());

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quiz_area, container, false);
        //TODO name processing
        Bundle bundle = getArguments();

        continueQuizBtn = view.findViewById(R.id.continueQuizBtn);
        categoryTextView = view.findViewById(R.id.categoryTextView);
        difficultyTextView = view.findViewById(R.id.difficultyTextView);
        questionArea = view.findViewById(R.id.questionArea);
        answersArea = view.findViewById(R.id.answersArea);

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        String url = "https://opentdb.com/api.php?amount=10";

        // Request a json response from the provided URL.
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    responseToHandle =  response.getJSONArray("results");
                    quizHandler(responseToHandle, categoryTextView,difficultyTextView,questionArea,answersArea,0);
//                    Toast.makeText(getActivity(),responseToHandle.toString(),Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        // Add the request to the RequestQueue.
        queue.add(request);

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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}