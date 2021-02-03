package com.example.attendance.beans;

public class addstudentdatabase {
    String FullName;
    String RollNumber;
    String Address;
    String Email;
    String BrithofDate;
    String Division;
    String Course;
    String Number;
    String Uid;

    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) {
        Uid = uid;
    }

    public addstudentdatabase(String fullName, String rollNumber, String address, String email, String brithofDate, String division, String course, String number, String uid) {
        FullName = fullName;
        RollNumber = rollNumber;
        Address = address;
        Email = email;
        BrithofDate = brithofDate;
        Division = division;
        Course = course;
        Number = number;
        Uid=uid;
    }

    public addstudentdatabase() {
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public String getRollNumber() {
        return RollNumber;
    }

    public void setRollNumber(String rollNumber) {
        RollNumber = rollNumber;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getBrithofDate() {
        return BrithofDate;
    }

    public void setBrithofDate(String brithofDate) {
        BrithofDate = brithofDate;
    }

    public String getDivision() {
        return Division;
    }

    public void setDivision(String division) {
        Division = division;
    }

    public String getCourse() {
        return Course;
    }

    public void setCourse(String course) {
        Course = course;
    }


    public String getNumber() {
        return Number;
    }

    public void setNumber(String number) {
        Number = number;
    }
}
