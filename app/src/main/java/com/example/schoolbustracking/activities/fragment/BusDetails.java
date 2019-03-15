package com.example.schoolbustracking.activities.fragment;


import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.schoolbustracking.R;
import com.example.schoolbustracking.activities.Model.DriverModel;
import com.example.schoolbustracking.activities.ViewHolder.DriverViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 */
public class BusDetails extends Fragment {

    DatabaseReference myRef;
    RecyclerView driverdetailsRecyView;

    Context context;
    Dialog edtDialog;

    EditText busno, drivername, phoneno;
    Button edt_submit;
    ImageView closebtn;

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
            protected void onBindViewHolder(@NonNull DriverViewHolder holder, final int position, @NonNull final DriverModel model) {
                holder.busno.setText("Bus No: " + model.getBusno());
                holder.drivername.setText("Driver name: " + model.getName());
                holder.contactno.setText("Contact No: " + model.getPhone());
                holder.edtBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        edtDialog.setContentView(R.layout.edit_driver);
                        busno = edtDialog.findViewById(R.id.edtbusno);
                        drivername = edtDialog.findViewById(R.id.edtdrivername);
                        phoneno = edtDialog.findViewById(R.id.edtphnno);
                        edt_submit = edtDialog.findViewById(R.id.edtsubmit_btn);
                        closebtn = edtDialog.findViewById(R.id.closebtn);

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
