package com.example.schoolbustracking.activities.fragment;


import android.Manifest;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.schoolbustracking.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterStudent extends Fragment {

    //Firebase
    FirebaseDatabase database;
    DatabaseReference myRef;

    //Widget
    EditText studentName, studentClass, studentBusNo, studentParentName, studentParentEmail, parentPhoneno;

    Context context;
    Button submitBtn;
    String sstudentname, sstudentClassm, sstudentBusno, sstudentParentName, sstudentParentEmail, sparentphoneno, spassword;
    int n;

    public RegisterStudent() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register_student, container, false);
        context = view.getContext();

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Student");


        studentName = view.findViewById(R.id.studentname);
        studentClass = view.findViewById(R.id.stdcls);
        studentBusNo = view.findViewById(R.id.bsno);
        studentParentName = view.findViewById(R.id.parnam);
        studentParentEmail = view.findViewById(R.id.email);
        submitBtn = view.findViewById(R.id.stSubmitBtn);
        parentPhoneno = view.findViewById(R.id.phnno);

        Random random = new Random();
        n = random.nextInt(999999);
        spassword = String.valueOf(n);


        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sstudentname = studentName.getText().toString().trim();
                sstudentBusno = studentBusNo.getText().toString().trim();
                sstudentClassm = studentClass.getText().toString().trim();
                sstudentParentName = studentParentName.getText().toString().trim();
                sstudentParentEmail = studentParentEmail.getText().toString().trim();
                sparentphoneno = parentPhoneno.getText().toString().trim();

                if (TextUtils.isEmpty(sstudentname)) {
                    Toast.makeText(context, "Enter Student name", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(sstudentBusno)) {
                    Toast.makeText(context, "Enter Student Bus No", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(sstudentClassm)) {
                    Toast.makeText(context, "Enter Student Class", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(sstudentParentName)) {
                    Toast.makeText(context, "Enter Student's Parent Name", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(sstudentParentEmail)) {
                    Toast.makeText(context, "Enter Student's Parent Email", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(sparentphoneno)) {
                    Toast.makeText(context, "Enter phone no", Toast.LENGTH_SHORT).show();
                } else {

                    Map<String, String> student = new HashMap<>();
                    student.put("stname", sstudentname);
                    student.put("stclass", sstudentClassm);
                    student.put("stbusno", sstudentBusno);
                    student.put("stparentName", sstudentParentName);
                    student.put("stparentEmail", sstudentParentEmail);
                    student.put("phone", sparentphoneno);
                    student.put("password", spassword);

                    myRef.child(sparentphoneno).setValue(student);
                    studentBusNo.setText("");
                    studentName.setText("");
                    studentParentName.setText("");
                    studentParentEmail.setText("");
                    studentClass.setText("");
                    parentPhoneno.setText("");
                    sendSmsToDriver();
                }

            }
        });


        return view;
    }


    private void sendSmsToDriver() {

        String message = "This message is from School Bus Tracking. Your child Bus no is "+ sstudentBusno +" and " + spassword + " is your login password";
        String numbers = sparentphoneno;
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(numbers, null, message, null, null);

    }
}
