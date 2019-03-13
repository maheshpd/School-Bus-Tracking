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
import android.widget.Toast;

import com.example.schoolbustracking.R;
import com.google.android.gms.common.internal.service.Common;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class Notice extends Fragment {

    //Firebase
    FirebaseDatabase database;
    DatabaseReference myRef;

    //Widget
    EditText notice_field;
    Button submit_btn;
    Context context;
    ProgressDialog mProgress;

    //String
    String snotice,stime,sdate;


    public Notice() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notice, container, false);
        context = view.getContext();
        mProgress = new ProgressDialog(context);


        notice_field = view.findViewById(R.id.principle_notice_field);
        submit_btn = view.findViewById(R.id.principle_notice_post_btn);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Notice");

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
                postNotice();
            }
        });


        return view;
    }

    private void postNotice() {
        snotice = notice_field.getText().toString().trim();

        if (TextUtils.isEmpty(snotice)) {
            Toast.makeText(context, "Enter your notice", Toast.LENGTH_SHORT).show();
        } else {
            mProgress.setMessage("Please wait...");
            mProgress.setCanceledOnTouchOutside(false);
            mProgress.show();

            Map<String,String> post = new HashMap<>();
            post.put("notice",snotice);
            post.put("time",stime);
            post.put("date",sdate);

            myRef.push().setValue(post);
            mProgress.dismiss();
            notice_field.setText("");
        }
    }


}
