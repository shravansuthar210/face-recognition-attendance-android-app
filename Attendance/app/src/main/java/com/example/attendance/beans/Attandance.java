package com.example.attendance.beans;

public class Attandance {
    private String Starttime,Endtime;

    public Attandance(String starttime, String endtime) {
        Starttime = starttime;
        Endtime = endtime;
    }

    public String getStarttime() {
        return Starttime;
    }

    public void setStarttime(String starttime) {
        Starttime = starttime;
    }

    public String getEndtime() {
        return Endtime;
    }

    public void setEndtime(String endtime) {
        Endtime = endtime;
    }
}
