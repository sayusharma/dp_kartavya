package com.e.dpkartavya.Model;

public class Visit {
    private String name,mob,addr,photo,police_officer,date,time,notes;
    public Visit(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMob() {
        return mob;
    }

    public void setMob(String mob) {
        this.mob = mob;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getPolice_officer() {
        return police_officer;
    }

    public void setPolice_officer(String police_officer) {
        this.police_officer = police_officer;
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

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Visit(String name, String mob, String addr, String photo, String police_officer, String date, String time, String notes) {
        this.name = name;
        this.mob = mob;
        this.addr = addr;
        this.photo = photo;
        this.police_officer = police_officer;
        this.date = date;
        this.time = time;
        this.notes = notes;
    }
}
