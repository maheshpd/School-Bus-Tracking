package com.example.schoolbustracking.activities.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.example.schoolbustracking.R;

public class ParentMainActivity extends AppCompatActivity {

    Button trackBtn,changeBtn,givefeedbackbtn,logoutbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_main);

        trackBtn = findViewById(R.id.track_location);
        changeBtn = findViewById(R.id.change_password);
        givefeedbackbtn = findViewById(R.id.give_feedback);
        logoutbtn = findViewById(R.id.logout);


    }
}
