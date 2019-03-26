package com.example.schoolbustracking.activities.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.schoolbustracking.R;
import com.example.schoolbustracking.activities.Utils.Common;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;


public class EditDriver extends AppCompatDialogFragment {

    public Context context;
    private EditText busno, drivername, drivercontact;
    private Button submitbtn;

    //Firebase
    FirebaseDatabase database;
    DatabaseReference myRef;

    String sbusno, sdrivername, sdrivercontact;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.edit_driver, null);
        context = view.getContext();

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Driver");


        busno = view.findViewById(R.id.edtbusno);
        drivername = view.findViewById(R.id.edtdrivername);
        drivercontact = view.findViewById(R.id.edtphnno);
        submitbtn = view.findViewById(R.id.edtsubmit_btn);

        submitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sbusno = busno.getText().toString().trim();
                sdrivername = drivername.getText().toString().trim();
                sdrivercontact = drivercontact.getText().toString().trim();

                HashMap<String, String> post = new HashMap<>();
                post.put("busno", sbusno);
                post.put("name", sdrivername);
                post.put("phone", sdrivercontact);

                myRef.child(Common.user_name).setValue(post).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(context, "Update Successfully", Toast.LENGTH_SHORT).show();
                        dismiss();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "Update not Successfully", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

        return super.onCreateDialog(savedInstanceState);

    }
}
