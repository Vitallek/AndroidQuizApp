package com.example.quiz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainerView, StartFragment.class, null)
                .setReorderingAllowed(true)
                .addToBackStack("nameInput") // name can be null
                .commit();
    }
}