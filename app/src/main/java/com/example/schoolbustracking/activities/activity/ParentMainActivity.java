package com.example.schoolbustracking.activities.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.schoolbustracking.R;
import com.example.schoolbustracking.activities.fragment.AddDriver;
import com.example.schoolbustracking.activities.fragment.ChangePassword;
import com.example.schoolbustracking.activities.fragment.Feedback;
import com.example.schoolbustracking.activities.fragment.TrackLocation;

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

        getSupportFragmentManager().beginTransaction().replace(R.id.parent_main_container, new TrackLocation()).commit();

        trackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.parent_main_container, new TrackLocation()).commit();
            }
        });

        changeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.parent_main_container, new ChangePassword()).commit();
            }
        });

        givefeedbackbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.parent_main_container, new Feedback()).commit();
            }
        });

        logoutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

        @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.parentnoticemain, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(ParentMainActivity.this,Notification.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
