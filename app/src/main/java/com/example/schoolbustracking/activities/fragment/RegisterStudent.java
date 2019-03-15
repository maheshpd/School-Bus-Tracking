package com.example.schoolbustracking.activities.fragment;


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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterStudent extends Fragment {

    //Firebase
    FirebaseDatabase database;
    DatabaseReference myRef;

    //Widget
    EditText studentName, studentClass, studentBusNo, studentParentName, studentParentEmail;

    Context context;
    Button submitBtn;
    String sstudentname, sstudentClassm, sstudentBusno, sstudentParentName, sstudentParentEmail;


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
        studentParentEmail = view.findViewById(R.id.phnno);
        submitBtn = view.findViewById(R.id.stSubmitBtn);

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sstudentname = studentName.getText().toString().trim();
                sstudentBusno = studentBusNo.getText().toString().trim();
                sstudentClassm = studentClass.getText().toString().trim();
                sstudentParentName = studentParentName.getText().toString().trim();
                sstudentParentEmail = studentParentEmail.getText().toString().trim();

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
                } else {

                    Map<String, String> student = new HashMap<>();
                    student.put("stname", sstudentname);
                    student.put("stclass", sstudentClassm);
                    student.put("stbusno", sstudentBusno);
                    student.put("stparentName", sstudentParentName);
                    student.put("stparentEmail", sstudentParentEmail);


                    myRef.push().setValue(student);
                    studentBusNo.setText("");
                    studentName.setText("");
                    studentParentName.setText("");
                    studentParentEmail.setText("");
                    studentClass.setText("");


                }

            }
        });


        return view;
    }

}
