package com.example.schoolbustracking.activities.fragment;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.schoolbustracking.R;
import com.example.schoolbustracking.activities.Model.DriverModel;
import com.example.schoolbustracking.activities.Utils.Common;
import com.example.schoolbustracking.activities.ViewHolder.DriverViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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
public class BusDetails extends Fragment {

    DatabaseReference myRef, update, delete;
    RecyclerView driverdetailsRecyView;

    Context context;
    Dialog edtDialog;

    EditText busno, drivername, phoneno;
    Button edt_submit;
    ImageView closebtn;

    String sbusno, sdrivername, sdrivercontact, password;

    TextView password_field;

    public BusDetails() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bus_details, container, false);
        context = view.getContext();

        //Firebase
        myRef = FirebaseDatabase.getInstance().getReference().child("Driver");

        edtDialog = new Dialog(context);
        //Widget
        driverdetailsRecyView = view.findViewById(R.id.recyclerview_busdetails);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
//        layoutManager.setStackFromEnd(true);
        driverdetailsRecyView.setLayoutManager(layoutManager);


        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<DriverModel> options = new FirebaseRecyclerOptions.Builder<DriverModel>()
                .setQuery(myRef, DriverModel.class)
                .build();

        FirebaseRecyclerAdapter<DriverModel, DriverViewHolder> adapter = new FirebaseRecyclerAdapter<DriverModel, DriverViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final DriverViewHolder holder, final int position, @NonNull final DriverModel model) {
                sbusno = model.getBusno();
                holder.busno.setText("Bus No: " + model.getBusno());
                holder.drivername.setText("Driver name: " + model.getName());
                holder.contactno.setText("Contact No: " + model.getPhone());
                holder.password.setText("Password:" + model.getPassword());
                holder.edtBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        edtDialog.setContentView(R.layout.edit_driver);
                        busno = edtDialog.findViewById(R.id.edtbusno);
                        drivername = edtDialog.findViewById(R.id.edtdrivername);
                        phoneno = edtDialog.findViewById(R.id.edtphnno);
                        edt_submit = edtDialog.findViewById(R.id.edtsubmit_btn);
                        closebtn = edtDialog.findViewById(R.id.closebtn);
                        password_field = edtDialog.findViewById(R.id.password);
                        closebtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                edtDialog.dismiss();
                            }
                        });

                        edtDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        edtDialog.show();

                        busno.setText(model.getBusno());
                        drivername.setText(model.getName());
                        phoneno.setText(model.getPhone());
                        password_field.setText(model.getPassword());
                        edt_submit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                sbusno = busno.getText().toString().trim();
                                sdrivername = drivername.getText().toString().trim();
                                sdrivercontact = phoneno.getText().toString().trim();
                                password = password_field.getText().toString();
                                Map<String, String> driverdetails = new HashMap<>();
                                driverdetails.put("name", sdrivername);
                                driverdetails.put("busno", sbusno);
                                driverdetails.put("phone", sdrivercontact);
                                driverdetails.put("password", password);
                                update = FirebaseDatabase.getInstance().getReference().child("Driver").child(sbusno);
                                update.setValue(driverdetails);
                                Toast.makeText(context, "Update Successfully", Toast.LENGTH_SHORT).show();
                                edtDialog.dismiss();

                            }
                        });

                    }
                });


                holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        new AlertDialog.Builder(context)
                                .setTitle("Delete")
                                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                })
                                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        delete = FirebaseDatabase.getInstance().getReference().child("Driver").child(sbusno);
                                        delete.removeValue();
                                        Toast.makeText(context, "delete", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .create()
                                .show();

                        return false;
                    }
                });

            }

            @NonNull
            @Override
            public DriverViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.driver_item, viewGroup, false);
                return new DriverViewHolder(view);
            }
        };

        driverdetailsRecyView.setAdapter(adapter);
        adapter.startListening();


    }

}
