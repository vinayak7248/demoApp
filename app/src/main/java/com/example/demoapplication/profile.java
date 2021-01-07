package com.example.demoapplication;

import com.google.firebase.database.Exclude;

public class profile {
    private String profile_pic, Fname,Lname,Gender,country,state,Htown,id;
    private int DOB;
    private Long Mno,Tno;
    public profile(){

    }
    public profile(String id,String profile_pic, String Fname, String Lname, int DOB, String Gender, String country, String state, String Htown, Long Mno, Long Tno) {
        this.id=id;
        this.profile_pic = profile_pic;
        this.Fname = Fname;
        this.Lname = Lname;
        this.DOB = DOB;
        this.Gender = Gender;
        this.country = country;
        this.state = state;
        this.Htown = Htown;
        this.Mno = Mno;
        this.Tno = Tno;
    }


    public String getProfile_pic() {
        return profile_pic;
    }

    public void setProfile_pic(String profile_pic) {
        this.profile_pic = profile_pic;
    }

    public String getFname() {
        return Fname;
    }

    public void setFname(String fname) {
        Fname = fname;
    }

    public String getLname() {
        return Lname;
    }

    public void setLname(String lname) {
        Lname = lname;
    }

    public int getDOB() {
        return DOB;
    }

    public void setDOB(int DOB) {
        this.DOB = DOB;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getHtown() {
        return Htown;
    }

    public void setHtown(String htown) {
        Htown = htown;
    }


    public Long getMno() {
        return Mno;
    }

    public void setMno(Long mno) {
        Mno = mno;
    }

    public Long getTno() {
        return Tno;
    }

    public void setTno(Long tno) {
        Tno = tno;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
