package com.example.attendance.beans;

public class profiledatabase {
    private String Fullname,email,birthofdate,numbers,uid,addresss,password,usertype,rollnumber,course,year,degree,courece,division;

    public profiledatabase() {
    }

    public profiledatabase(String fullname, String email, String birthofdate, String numbers, String uid, String addresss, String password, String usertype, String rollnumber, String course, String year,String degree,String courece,String division) {
        Fullname = fullname;
        this.email = email;
        this.birthofdate = birthofdate;
        this.numbers = numbers;
        this.uid = uid;
        this.addresss = addresss;
        this.password = password;
        this.usertype=usertype;
        this.rollnumber=rollnumber;
        this.course=course;
        this.year=year;
        this.degree=degree;
        this.courece=courece;
        this.division=division;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public String getCourece() {
        return courece;
    }

    public void setCourece(String courece) {
        this.courece = courece;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getRollnumber() {
        return rollnumber;
    }

    public void setRollnumber(String rollnumber) {
        this.rollnumber = rollnumber;
    }

    public String getUsertype() {
        return usertype;
    }

    public void setUsertype(String usertype) {
        this.usertype = usertype;
    }

    public String getFullname() {
        return Fullname;
    }

    public void setFullname(String fullname) {
        Fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBirthofdate() {
        return birthofdate;
    }

    public void setBirthofdate(String birthofdate) {
        this.birthofdate = birthofdate;
    }

    public String getNumbers() {
        return numbers;
    }

    public void setNumbers(String numbers) {
        this.numbers = numbers;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getAddresss() {
        return addresss;
    }

    public void setAddresss(String addresss) {
        this.addresss = addresss;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
