package com.example.schoolbustracking.activities.ViewHolder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.schoolbustracking.R;

public class DriverViewHolder extends RecyclerView.ViewHolder {

    public TextView busno, drivername, contactno,password;
    public Button edtBtn;
    public DriverViewHolder(@NonNull View itemView) {
        super(itemView);

        busno = itemView.findViewById(R.id.busno);
        drivername = itemView.findViewById(R.id.drivername);
        contactno = itemView.findViewById(R.id.contactno);
        edtBtn = itemView.findViewById(R.id.editBtn);
        password = itemView.findViewById(R.id.password);

    }
}
