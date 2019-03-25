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
import android.widget.Toast;

import com.example.schoolbustracking.R;
import com.example.schoolbustracking.activities.Model.ParentModel;
import com.example.schoolbustracking.activities.Utils.Common;
import com.example.schoolbustracking.activities.Utils.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ParentPage extends AppCompatActivity {
    EditText username, userpass;
    Button loginBtn;

    User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_page);

        //Firebase
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_driver = database.getReference("Student");
        loginBtn = findViewById(R.id.parentloginbtn);
        username = findViewById(R.id.username);
        userpass = findViewById(R.id.userpass);

        user = new User(ParentPage.this);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Common.user_name = username.getText().toString().trim();
                Common.current_pass = userpass.getText().toString().trim();
                
                if (TextUtils.isEmpty(Common.user_name)) {
                    Toast.makeText(ParentPage.this, "Enter name", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(Common.current_pass)) {
                    Toast.makeText(ParentPage.this, "Enter pass", Toast.LENGTH_SHORT).show();
                } else {
                    final ProgressDialog mDialog = new ProgressDialog(ParentPage.this);
                    mDialog.setMessage("Please wait...");
                    mDialog.setCanceledOnTouchOutside(false);
                    mDialog.show();
                    table_driver.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            //Get User information
                            ParentModel parentModel = dataSnapshot.child(username.getText().toString()).getValue(ParentModel.class);
                            if (parentModel.getPassword().equals(userpass.getText().toString())) {
                                mDialog.dismiss();
                                mainPage();
                            } else {
                                mDialog.dismiss();
                                Toast.makeText(ParentPage.this, "User no exist", Toast.LENGTH_SHORT).show();
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

    public void mainPage() {
        startActivity(new Intent(ParentPage.this, ParentMainActivity.class));
    }
}
