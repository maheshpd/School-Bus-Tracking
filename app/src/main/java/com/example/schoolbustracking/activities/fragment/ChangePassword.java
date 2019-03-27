package com.example.schoolbustracking.activities.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.schoolbustracking.R;
import com.example.schoolbustracking.activities.Utils.Common;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChangePassword extends Fragment {
    EditText current_pass, new_pass, con_pass;
    ImageView changePass;

    DatabaseReference change_password;

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

                if (TextUtils.isEmpty(current_pass.getText().toString())) {
                    Toast.makeText(getContext(), "Enter current password", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(new_pass.getText().toString())) {
                    Toast.makeText(getContext(), "Enter your new password", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(con_pass.getText().toString())) {
                    Toast.makeText(getContext(), "Enter your confirm password", Toast.LENGTH_SHORT).show();
                } else {

                    if (current_pass.getText().toString().equals(Common.current_pass)) {
                        if (con_pass.getText().toString().equals(con_pass.getText().toString())) {
                            change_password = FirebaseDatabase.getInstance().getReference().child("Student").child(Common.user_name);
                            change_password.child("password").setValue(con_pass.getText().toString());
                            Toast.makeText(getContext(), "Your password is update", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), "Password doesn't match", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getContext(), "Your current password doesn't exist", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        return view;
    }

}
