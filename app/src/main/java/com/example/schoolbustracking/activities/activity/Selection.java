package com.example.schoolbustracking.activities.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.schoolbustracking.R;

public class Selection extends AppCompatActivity {
Button principal_btn,parents_btn,driver_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection);

        principal_btn=findViewById(R.id.principalbutton);
        parents_btn=findViewById(R.id.parentbutton);
        driver_btn=findViewById(R.id.driverbutton);

        principal_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Selection.this, PrincipalPage.class));
            }
        });


        parents_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Selection.this, ParentPage.class));
            }
        });

        driver_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Selection.this, DriverPage.class));
            }
        });


    }

}
