package com.example.schoolbustracking.activities.ViewHolder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.schoolbustracking.R;

public class FeedbackViewHolder extends RecyclerView.ViewHolder {

   public TextView txtname, txtfeedback, txttime, txtdate;

    public FeedbackViewHolder(@NonNull View itemView) {
        super(itemView);

        txtname = itemView.findViewById(R.id._name);
        txtfeedback = itemView.findViewById(R.id.feedback);
        txttime = itemView.findViewById(R.id.feedbacktime);
        txtdate = itemView.findViewById(R.id.feedbackdate);
    }
}
