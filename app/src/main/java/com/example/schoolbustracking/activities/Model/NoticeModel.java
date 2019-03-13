package com.example.schoolbustracking.activities.Model;

public class NoticeModel {
    String notice,date,time;

    public NoticeModel(String notice, String date, String time) {
        this.notice = notice;
        this.date = date;
        this.time = time;
    }

    public NoticeModel() {
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
