package com.example.smatech.ay5edma.Models.Modelss;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReviewModel {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("comment")
    @Expose
    private String comment;
    @SerializedName("rate")
    @Expose
    private String rate;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("image")
    @Expose
    private String image;

    public String getId() {
        return id;
    }

    public String getComment() {
        return comment;
    }

    public String getRate() {
        return rate;
    }

    public String getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getDate() {
        return date;
    }

    public String getImage() {
        return image;
    }
}
