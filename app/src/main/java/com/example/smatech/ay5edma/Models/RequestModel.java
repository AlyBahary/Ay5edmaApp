package com.example.smatech.ay5edma.Models;

public class RequestModel {
    String Catgry1,Catgry2,Date,name,Type,Favourite;

    public String getCatgry1() {
        return Catgry1;
    }

    public void setCatgry1(String catgry1) {
        Catgry1 = catgry1;
    }

    public String getCatgry2() {
        return Catgry2;
    }

    public void setCatgry2(String catgry2) {
        Catgry2 = catgry2;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RequestModel(String catgry1, String catgry2, String date, String name, String type, String favourite) {
        Catgry1 = catgry1;
        Catgry2 = catgry2;
        Date = date;
        this.name = name;
        Type = type;
        Favourite = favourite;
    }

    public String getType() {

        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getFavourite() {
        return Favourite;
    }

    public void setFavourite(String favourite) {
        Favourite = favourite;
    }
}
