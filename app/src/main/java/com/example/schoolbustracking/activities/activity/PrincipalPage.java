package com.example.schoolbustracking.activities.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.schoolbustracking.R;

public class PrincipalPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal_page);
    }

    public void mainPage(View view) {
        startActivity(new Intent(PrincipalPage.this, MainActivity.class));
    }

}
