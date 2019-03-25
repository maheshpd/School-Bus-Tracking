package com.example.schoolbustracking.activities.Model;

public class ParentModel {
    String stname,stclass,stbusno,stparentName,stparentEmail,phone,password;

    public ParentModel() {
    }

    public ParentModel(String stname, String stclass, String stbusno, String stparentName, String stparentEmail, String phone, String password) {
        this.stname = stname;
        this.stclass = stclass;
        this.stbusno = stbusno;
        this.stparentName = stparentName;
        this.stparentEmail = stparentEmail;
        this.phone = phone;
        this.password = password;
    }

    public String getStname() {
        return stname;
    }

    public void setStname(String stname) {
        this.stname = stname;
    }

    public String getStclass() {
        return stclass;
    }

    public void setStclass(String stclass) {
        this.stclass = stclass;
    }

    public String getStbusno() {
        return stbusno;
    }

    public void setStbusno(String stbusno) {
        this.stbusno = stbusno;
    }

    public String getStparentName() {
        return stparentName;
    }

    public void setStparentName(String stparentName) {
        this.stparentName = stparentName;
    }

    public String getStparentEmail() {
        return stparentEmail;
    }

    public void setStparentEmail(String stparentEmail) {
        this.stparentEmail = stparentEmail;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
