package com.e.dpkartavya.Model;

public class MoreDetails {
    private Loc loc;
    private String officer,date,time;
    public MoreDetails(){}

    public Loc getLoc() {
        return loc;
    }

    public void setLoc(Loc loc) {
        this.loc = loc;
    }

    public String getOfficer() {
        return officer;
    }

    public void setOfficer(String officer) {
        this.officer = officer;
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

    public MoreDetails(Loc loc, String officer, String date, String time) {
        this.loc = loc;
        this.officer = officer;
        this.date = date;
        this.time = time;
    }
}
