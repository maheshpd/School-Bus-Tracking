package com.example.schoolbustracking.activities.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.schoolbustracking.R;
import com.example.schoolbustracking.activities.activity.UserMaps;

/**
 * A simple {@link Fragment} subclass.
 */
public class TrackLocation extends Fragment {


    ImageButton trackbtn;
    Context context;

    public TrackLocation() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_track_location, container, false);
        context = view.getContext();

        trackbtn = view.findViewById(R.id.TrackBtn);
        trackbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, UserMaps.class));
            }
        });

        return view;
    }

}
