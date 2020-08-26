package com.e.dpkartavya.Model;

public class User {
    private String name,rank,photo,mob,pass,police;
    public User(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getMob() {
        return mob;
    }

    public void setMob(String mob) {
        this.mob = mob;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getPolice() {
        return police;
    }

    public void setPolice(String police) {
        this.police = police;
    }

    public User(String name, String rank, String photo, String mob, String pass, String police) {
        this.name = name;
        this.rank = rank;
        this.photo = photo;
        this.mob = mob;
        this.pass = pass;
        this.police = police;
    }
}