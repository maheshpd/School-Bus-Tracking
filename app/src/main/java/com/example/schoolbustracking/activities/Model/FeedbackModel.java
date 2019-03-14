package com.example.schoolbustracking.activities.Model;

public class FeedbackModel {
    String name,feedback,time,date;

    public FeedbackModel() {
    }

    public FeedbackModel(String name, String feedback, String time, String date) {
        this.name = name;
        this.feedback = feedback;
        this.time = time;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
