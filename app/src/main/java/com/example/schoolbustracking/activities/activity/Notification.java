package com.example.schoolbustracking.activities.activity;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.schoolbustracking.R;
import com.example.schoolbustracking.activities.Model.NoticeModel;
import com.example.schoolbustracking.activities.ViewHolder.NotificationViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Notification extends AppCompatActivity {

    DatabaseReference myRef;
    RecyclerView notificationRecyView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Notification");

        //Firebase
        myRef = FirebaseDatabase.getInstance().getReference().child("Notice");

        //Widget
        notificationRecyView = findViewById(R.id.notification_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setStackFromEnd(true);
        notificationRecyView.setLayoutManager(layoutManager);

    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<NoticeModel> options = new FirebaseRecyclerOptions.Builder<NoticeModel>()
                .setQuery(myRef,NoticeModel.class)
                .build();

        FirebaseRecyclerAdapter<NoticeModel, NotificationViewHolder> adapter = new FirebaseRecyclerAdapter<NoticeModel, NotificationViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull NotificationViewHolder holder, int position, @NonNull NoticeModel model) {
                holder.notice.setText(model.getNotice());
                holder.time.setText(model.getTime());
                holder.date.setText(model.getDate());
            }

            @NonNull
            @Override
            public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.notice_item,viewGroup,false);
                return new NotificationViewHolder(view);
            }
        };
        notificationRecyView.setAdapter(adapter);
        adapter.startListening();
    }
}
