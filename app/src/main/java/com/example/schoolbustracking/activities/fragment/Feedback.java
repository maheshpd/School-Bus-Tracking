package com.example.schoolbustracking.activities.fragment;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.schoolbustracking.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class Feedback extends Fragment {

    //Firebase
    FirebaseDatabase database;
    DatabaseReference myRef;

    //Widget
    EditText nametxt, feedbacktxt;
    ImageButton submit_btn;
    Context context;
    ProgressDialog mProgress;

    //String
    String sname, sfeedback, stime, sdate;

    public Feedback() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_feedback, container, false);
        context = view.getContext();
        mProgress = new ProgressDialog(context);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Feedback");


        nametxt = view.findViewById(R.id.feedback_name);
        feedbacktxt = view.findViewById(R.id.feedback_txt);
        submit_btn = view.findViewById(R.id.feedback_submit);

        //Time
        SimpleDateFormat sdf = new SimpleDateFormat("h:mm:ss a");
        stime = sdf.format(new Date());

        //change Date Format
        Date now = new Date();
        sdf = new SimpleDateFormat("MMM dd, yyyy");
        sdate = sdf.format(now);


        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postFeedback();
            }
        });


        return view;

    }

    private void postFeedback() {
        sname = nametxt.getText().toString();
        sfeedback = feedbacktxt.getText().toString();

        if (TextUtils.isEmpty(sname)) {
            Toast.makeText(context, "Enter your name", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(sfeedback)) {
            Toast.makeText(context, "Enter your feedback", Toast.LENGTH_SHORT).show();
        } else {
            mProgress.setMessage("Please wait...");
            mProgress.setCanceledOnTouchOutside(false);
            mProgress.show();

            Map<String, String> post = new HashMap<>();
            post.put("name", sname);
            post.put("feedback", sfeedback);
            post.put("time", stime);
            post.put("date", sdate);

            myRef.push().setValue(post);
            mProgress.dismiss();
            nametxt.setText("");
            feedbacktxt.setText("");

        }
    }

}
