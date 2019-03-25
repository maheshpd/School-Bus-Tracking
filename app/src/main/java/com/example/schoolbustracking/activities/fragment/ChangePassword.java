package com.example.schoolbustracking.activities.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.schoolbustracking.R;
import com.example.schoolbustracking.activities.Utils.Common;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChangePassword extends Fragment {
    EditText current_pass, new_pass, con_pass;
    ImageView changePass;

    public ChangePassword() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_change_password, container, false);
        current_pass = view.findViewById(R.id.current_pass);
        new_pass = view.findViewById(R.id.new_pass_edt);
        con_pass = view.findViewById(R.id.Confir_pass_edt);
        changePass = view.findViewById(R.id.submit_password);

        changePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (current_pass.getText().toString().equals(Common.current_pass)){

                }
            }
        });
        return view;
    }

}
