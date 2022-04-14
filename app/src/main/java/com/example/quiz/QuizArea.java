package com.example.quiz;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.text.Html;
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
import java.util.Locale;

public class QuizArea extends Fragment {

    private JSONArray responseToHandle;
    private Button continueQuizBtn;
    private Button answ1Btn, answ2Btn, answ3Btn, answ4Btn;
    private TextView categoryTextView, timer, difficultyTextView, questionArea;
    private ConstraintLayout answersArea;
    private int answerNumber = 0;
    private int correctAnswers = 0;
    private Bundle bundle;


    public void quizHandler(JSONArray responseToHandle,
                            TextView categoryTextView,
                            TextView difficultyTextView,
                            TextView questionArea,
                            ConstraintLayout answersArea,
                            int answerNumber)
    {
        try {
            for(int i = 0; i < answersArea.getChildCount(); i++) {
                answersArea.getChildAt(i).setVisibility(View.GONE);
            }
            continueQuizBtn.setEnabled(false);
            JSONObject result = responseToHandle.getJSONObject(answerNumber);
            String category = result.getString("category");
            categoryTextView.setText(Html.fromHtml(category.toUpperCase(Locale.ROOT)));

            String difficulty = result.getString("difficulty");
            difficultyTextView.setText("Level: " + Html.fromHtml(difficulty));

            String question = result.getString("question");
            questionArea.setText(Html.fromHtml(question));

            JSONArray incorrectAnswers = result.getJSONArray("incorrect_answers");
            String correctAnswer = result.getString("correct_answer");

            List<String> allAnswers = new ArrayList<String>();
            for(int i =0; i< incorrectAnswers.length(); i++){
                allAnswers.add(incorrectAnswers.getString(i));
            }
            allAnswers.add(correctAnswer);
            Collections.shuffle(allAnswers);

            for(int i = 0; i < allAnswers.size(); i++){
                Button tempBtn = (Button) answersArea.getChildAt(i);
                // handle next question
                tempBtn.setEnabled(true);
                tempBtn.setBackgroundTintList(ContextCompat.getColorStateList(getContext(), R.color.white));
                //
                tempBtn.setText(allAnswers.get(i));
                tempBtn.setVisibility(View.VISIBLE);
                tempBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(tempBtn.getText() == correctAnswer){
                            tempBtn.setBackgroundTintList(ContextCompat.getColorStateList(getContext(), R.color.correctAnswer));
                            correctAnswers++;
                            continueQuizBtn.setEnabled(true);
                        } else {
                            tempBtn.setBackgroundTintList(ContextCompat.getColorStateList(getContext(), R.color.incorrectAnswer));
                            continueQuizBtn.setEnabled(true);
                        }
                        for(int i = 0; i < allAnswers.size(); i++){
                            answersArea.getChildAt(i).setEnabled(false);

                        }
                    }
                });
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quiz_area, container, false);
        //start bg timer
        bundle = getArguments();
        bundle.putInt("startTime", (int) System.currentTimeMillis());

        continueQuizBtn = view.findViewById(R.id.continueQuizBtn);
        categoryTextView = view.findViewById(R.id.categoryTextView);
        difficultyTextView = view.findViewById(R.id.difficultyTextView);
        questionArea = view.findViewById(R.id.questionArea);
        answersArea = view.findViewById(R.id.answersArea);
        timer = view.findViewById(R.id.timer);

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        String url = "https://opentdb.com/api.php?amount=10";

        // Request a json response from the provided URL.
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    responseToHandle =  response.getJSONArray("results");
                    quizHandler(responseToHandle, categoryTextView,difficultyTextView,questionArea,answersArea,answerNumber);
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

        //runs without a timer by reposting this handler at the end of the runnable
//        long startTime = System.currentTimeMillis();
//        Handler timerHandler = new Handler();
//        Runnable timerRunnable = new Runnable() {
//
//            @Override
//            public void run() {
//                long millis = System.currentTimeMillis() - startTime;
////                int seconds = (int) (millis / 1000);
////                int minutes = seconds / 60;
////                seconds = seconds % 60;
//                timer.setText("Time: " + millis);
//                timerHandler.postDelayed(this, 50);
//            }
//        };
//
//        timerHandler.postDelayed(timerRunnable, 0);
//        timerHandler.removeCallbacks(timerRunnable); // stop the timer

        continueQuizBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(answerNumber == 9){
                    bundle.putInt("finishTime", (int) System.currentTimeMillis());
                    ResultArea resultArea = new ResultArea();
                    System.out.println(bundle);
                    //resultArea.setArguments(bundle);
                    getFragmentManager().beginTransaction()
                            .replace(R.id.fragmentContainerView, resultArea, null)
                            .setReorderingAllowed(true)
                            .addToBackStack("QuizArea") // name can be null
                            .commit();
                } else {
                    answerNumber++;
                    quizHandler(responseToHandle, categoryTextView,difficultyTextView,questionArea,answersArea,answerNumber);
                    continueQuizBtn.setText((answerNumber + 1) + "/10 \nContinue");
                }
            }
        });
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        Bundle bundle = new Bundle();
//        bundle = getArguments();
//        bundle.putInt("FinishTime", (int) System.currentTimeMillis());
    }
}