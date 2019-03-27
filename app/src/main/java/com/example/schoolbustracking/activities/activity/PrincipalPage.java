package com.example.schoolbustracking.activities.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.schoolbustracking.R;
import com.example.schoolbustracking.activities.Utils.User;

public class PrincipalPage extends AppCompatActivity {
    private EditText username, password;
    private Button login;

    String susername, userpass;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal_page);

        username = findViewById(R.id.username);
        password = findViewById(R.id.userpass);
        login = findViewById(R.id.loginbtn);

        user = new User(this);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });
    }

    private void loginUser() {
        susername = username.getText().toString().trim();
        userpass = password.getText().toString().trim();
        if (TextUtils.isEmpty(susername)) {
            Toast.makeText(this, "Enter username", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(userpass)) {
            Toast.makeText(this, "Enter password", Toast.LENGTH_SHORT).show();
        } else {
            startActivity(new Intent(PrincipalPage.this, MainActivity.class));
        }
    }

}
