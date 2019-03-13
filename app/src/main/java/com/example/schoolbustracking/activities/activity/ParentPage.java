package com.example.schoolbustracking.activities.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.schoolbustracking.R;

public class ParentPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_page);

        Button parent_btn = findViewById(R.id.parentloginbtn);
        parent_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ParentPage.this, ParentMainActivity.class));
            }
        });
    }
}
