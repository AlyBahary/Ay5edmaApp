package com.example.smatech.ay5edma.Models;

public class OffersDummyModel {
    String Name,Distance,url,Stars;

    public OffersDummyModel(String name, String distance, String url, String stars) {
        Name = name;
        Distance = distance;
        this.url = url;
        Stars = stars;
    }

    public String getName() {

        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDistance() {
        return Distance;
    }

    public void setDistance(String distance) {
        Distance = distance;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getStars() {
        return Stars;
    }

    public void setStars(String stars) {
        Stars = stars;
    }
}
