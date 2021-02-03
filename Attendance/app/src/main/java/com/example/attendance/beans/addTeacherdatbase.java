package com.example.attendance.beans;

public class addTeacherdatbase {
    private String Fullname,email,degree,courese,birthofdate,numbers,uid,addresss;

    public addTeacherdatbase() {
    }

    public addTeacherdatbase(String fullname, String email, String degree, String courese, String birthofdate, String numbers, String uid, String addresss) {
        this.Fullname = fullname;
        this.email = email;
        this.degree = degree;
        this.courese = courese;
        this.birthofdate = birthofdate;
        this.numbers = numbers;
        this.uid = uid;
        this.addresss = addresss;
    }

    public void setFullname(String fullname) {
        Fullname = fullname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public void setCourese(String courese) {
        this.courese = courese;
    }

    public void setBirthofdate(String birthofdate) {
        this.birthofdate = birthofdate;
    }

    public void setNumbers(String numbers) {
        this.numbers = numbers;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setAddresss(String addresss) {
        this.addresss = addresss;
    }

    public String getFullname() {
        return Fullname;
    }

    public String getEmail() {
        return email;
    }

    public String getDegree() {
        return degree;
    }

    public String getCourese() {
        return courese;
    }

    public String getBirthofdate() {
        return birthofdate;
    }

    public String getNumbers() {
        return numbers;
    }

    public String getUid() {
        return uid;
    }

    public String getAddresss() {
        return addresss;
    }
}
