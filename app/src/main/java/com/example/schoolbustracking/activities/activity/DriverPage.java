package com.example.schoolbustracking.activities.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.schoolbustracking.R;
import com.example.schoolbustracking.activities.Model.DriverModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DriverPage extends AppCompatActivity {
    EditText drivername, driverPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_page);

        //Widget
        Button driver_btn = findViewById(R.id.driverloginbtn);
        drivername = findViewById(R.id.drivername_edt);
        driverPassword = findViewById(R.id.driver_password_edt);
        //Firebase
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_driver = database.getReference("Driver");


        driver_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(drivername.getText().toString())) {
                    Toast.makeText(DriverPage.this, "Enter name", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(drivername.getText().toString())) {
                    Toast.makeText(DriverPage.this, "Enter password", Toast.LENGTH_SHORT).show();
                } else {

                    final ProgressDialog mDialog = new ProgressDialog(DriverPage.this);
                    mDialog.setMessage("Please wait...");
                    mDialog.setCanceledOnTouchOutside(false);
                    mDialog.show();
                    table_driver.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            //Check if user not exist in database
                            if (dataSnapshot.child(drivername.getText().toString()).exists()) {


                                //Get User information
                                DriverModel driverModel = dataSnapshot.child(drivername.getText().toString()).getValue(DriverModel.class);
                                if (driverModel.getPassword().equals(driverPassword.getText().toString())) {
                                    mDialog.dismiss();
                                    startActivity(new Intent(DriverPage.this, Welcome.class));
                                } else {
                                    mDialog.dismiss();
                                    Toast.makeText(DriverPage.this, "Sign in failed!!!", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                mDialog.dismiss();
                                Toast.makeText(DriverPage.this, "User not exist in Database", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }

            }
        });

    }
}
