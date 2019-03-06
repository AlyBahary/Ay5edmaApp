package com.example.smatech.ay5edma.Models;

public class ReviewModel {
    String Ctgry1;
    String Ctgry2;

    public ReviewModel(String ctgry1, String ctgry2, String time, String name, String comment) {
        Ctgry1 = ctgry1;
        Ctgry2 = ctgry2;
        Time = time;
        Name = name;
        Comment = comment;
    }

    String Time;
    String Name;
    String Comment;


    public String getCtgry1() {
        return Ctgry1;
    }

    public void setCtgry1(String ctgry1) {
        Ctgry1 = ctgry1;
    }

    public String getCtgry2() {
        return Ctgry2;
    }

    public void setCtgry2(String ctgry2) {
        Ctgry2 = ctgry2;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getComment() {
        return Comment;
    }

    public void setComment(String comment) {
        Comment = comment;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }
}
