package com.example.schoolbustracking.activities.activity;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.schoolbustracking.R;
import com.example.schoolbustracking.activities.Model.FeedbackModel;
import com.example.schoolbustracking.activities.Model.NoticeModel;
import com.example.schoolbustracking.activities.ViewHolder.FeedbackViewHolder;
import com.example.schoolbustracking.activities.ViewHolder.NotificationViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PrincipleFeedback extends AppCompatActivity {
    DatabaseReference myRef;
    RecyclerView feedbackRecyView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principle_feedback);

        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Feedback");

        //Firebase
        myRef = FirebaseDatabase.getInstance().getReference().child("Feedback");

        //Widget
        feedbackRecyView = findViewById(R.id.feedback_recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setReverseLayout(true);
//        layoutManager.setStackFromEnd(true);
        feedbackRecyView.setLayoutManager(layoutManager);
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<FeedbackModel> options = new FirebaseRecyclerOptions.Builder<FeedbackModel>()
                .setQuery(myRef,FeedbackModel.class)
                .build();

       FirebaseRecyclerAdapter<FeedbackModel,FeedbackViewHolder> adapter = new FirebaseRecyclerAdapter<FeedbackModel, FeedbackViewHolder>(options) {
           @Override
           protected void onBindViewHolder(@NonNull FeedbackViewHolder holder, int position, @NonNull FeedbackModel model) {
               holder.txtname.setText(model.getName());
               holder.txtfeedback.setText(model.getFeedback());
               holder.txttime.setText(model.getTime());
               holder.txtdate.setText(model.getDate());
           }

           @NonNull
           @Override
           public FeedbackViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
               View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.feedback_item,viewGroup,false);
               return new FeedbackViewHolder(view);
           }
       };

        feedbackRecyView.setAdapter(adapter);
        adapter.startListening();
    }
}
