package com.example.schoolbustracking.activities.Model;

public class DriverModel {
    String name, password,busno,phone;

    public DriverModel() {
    }

    public DriverModel(String name, String password, String busno, String phone) {
        this.name = name;
        this.password = password;
        this.busno = busno;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBusno() {
        return busno;
    }

    public void setBusno(String busno) {
        this.busno = busno;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
