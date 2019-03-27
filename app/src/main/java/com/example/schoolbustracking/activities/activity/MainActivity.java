package com.example.schoolbustracking.activities.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.schoolbustracking.R;
import com.example.schoolbustracking.activities.fragment.AddDriver;
import com.example.schoolbustracking.activities.fragment.BusDetails;
import com.example.schoolbustracking.activities.fragment.Feedback;
import com.example.schoolbustracking.activities.fragment.Notice;
import com.example.schoolbustracking.activities.fragment.RegisterStudent;
import com.google.firebase.FirebaseApp;

public class MainActivity extends AppCompatActivity {

    Button Add_drv, regstd, Bus_dtls, notice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("School Bus Tacking");

        FirebaseApp.initializeApp(MainActivity.this);

        getSupportFragmentManager().beginTransaction().replace(R.id.main_container, new AddDriver()).commit();



        Add_drv = findViewById(R.id.adddrvbtn);
        regstd = findViewById(R.id.std_reg_btn);
        Bus_dtls = findViewById(R.id.bs_det);
        notice = findViewById(R.id.noti);

        Add_drv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.main_container, new AddDriver()).commit();
            }
        });

        regstd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.main_container, new RegisterStudent()).commit();
            }
        });
        Bus_dtls.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.main_container, new BusDetails()).commit();
            }
        });
        notice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.main_container, new Notice()).commit();
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.principal_notification) {
            startActivity(new Intent(MainActivity.this, PrincipleFeedback.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }





}
