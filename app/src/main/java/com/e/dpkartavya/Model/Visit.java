package com.e.dpkartavya.Model;

public class Visit {
    private String name,photo,police_officer,date,time;
    public Visit(){}

    public String getName() {
        return name;
    }

    public String getPhoto() {
        return photo;
    }

    public String getPolice_officer() {
        return police_officer;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public Visit(String name, String photo, String police_officer, String date, String time) {
        this.name = name;
        this.photo = photo;
        this.police_officer = police_officer;
        this.date = date;
        this.time = time;
    }
}
