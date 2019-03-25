package com.example.schoolbustracking.activities.fragment;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.schoolbustracking.R;
import com.example.schoolbustracking.activities.Model.DriverModel;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddDriver extends Fragment {

    //Firebase
    FirebaseDatabase database;
    DatabaseReference myRef;
    FirebaseAuth mAuth;

    //Widget
    EditText edt_busno, edt_drivername, edt_driveCnt;
    Button btnSubmit;
    Context context;
    ProgressDialog progressDialog;
    //String
    String sbusno, sdrivername, sdriverCnt, spassword;
    int n;

    public AddDriver() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_driver, container, false);
        context = view.getContext();

        //init widget
        edt_busno = view.findViewById(R.id.busno);
        edt_drivername = view.findViewById(R.id.drivername);
        edt_driveCnt = view.findViewById(R.id.phnno);
        btnSubmit = view.findViewById(R.id.submit_btn);
        progressDialog = new ProgressDialog(context);


        Random random = new Random();
        n = random.nextInt(999999);
        spassword = String.valueOf(n);


        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Driver");


        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.setMessage("Please wait...");
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();

                sbusno = edt_busno.getText().toString();
                sdrivername = edt_drivername.getText().toString();
                sdriverCnt = edt_driveCnt.getText().toString();

                if (TextUtils.isEmpty(sbusno)) {
                    Toast.makeText(context, "Enter bus no", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(sdrivername)) {
                    Toast.makeText(context, "Enter driver name", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(sdriverCnt)) {
                    Toast.makeText(context, "Enter driver Contact no", Toast.LENGTH_SHORT).show();
                } else {
                    myRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            if (dataSnapshot.child(sbusno).exists()) {
                                progressDialog.dismiss();
                                Toast.makeText(context, "Driver name already register", Toast.LENGTH_SHORT).show();
                            } else {
                                progressDialog.dismiss();
                                DriverModel driverModel = new DriverModel(sdrivername, spassword, sbusno, sdriverCnt);
                                myRef.child(sbusno).setValue(driverModel);
                                Toast.makeText(context, "Register successfully", Toast.LENGTH_SHORT).show();

                                //Empty edit text
                                edt_busno.setText("");
                                edt_drivername.setText("");
                                edt_driveCnt.setText("");
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }
        });
        return view;
    }

}
