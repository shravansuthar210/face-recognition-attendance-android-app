package com.example.attendance;

import android.app.Application;

public class global extends Application {
    String uid,usertype,fullname,email,address,birthofdate,number,course,rollnumber,year,division,localprofilepic,emailaddrss,password;

    public String getEmailaddrss() {
        return emailaddrss;
    }

    public void setEmailaddrss(String emailaddrss) {
        this.emailaddrss = emailaddrss;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLocalprofilepic() {
        return localprofilepic;
    }

    public void setLocalprofilepic(String localprofilepic) {
        this.localprofilepic = localprofilepic;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getRollnumber() {
        return rollnumber;
    }

    public void setRollnumber(String rollnumber) {
        this.rollnumber = rollnumber;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getUsertype() {
        return usertype;
    }

    public void setUsertype(String usertype) {
        this.usertype = usertype;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBirthofdate() {
        return birthofdate;
    }

    public void setBirthofdate(String birthofdate) {
        this.birthofdate = birthofdate;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
