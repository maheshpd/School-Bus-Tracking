package com.example.schoolbustracking.activities.ViewHolder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.schoolbustracking.R;

public class NotificationViewHolder extends RecyclerView.ViewHolder {

    public TextView notice, time, date;

    public NotificationViewHolder(@NonNull View itemView) {
        super(itemView);

        notice = itemView.findViewById(R.id.notice_text);
        time = itemView.findViewById(R.id.notice_time);
        date = itemView.findViewById(R.id.date_text);
    }
}
