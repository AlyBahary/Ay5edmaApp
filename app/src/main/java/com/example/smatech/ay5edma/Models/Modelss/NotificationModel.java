package com.example.smatech.ay5edma.Models.Modelss;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class NotificationModel {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("from_id")
    @Expose
    private String fromId;
    @SerializedName("request_id")
    @Expose
    private String requestId;
    @SerializedName("offer_id")
    @Expose
    private String offerId;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("title_ar")
    @Expose
    private String titleAr;
    @SerializedName("created")
    @Expose
    private String created;
    @SerializedName("user")
    @Expose
    private UserModel user;
    @SerializedName("from")
    @Expose
    private UserModel from;
    @SerializedName("request")
    @Expose
    private RequestModel request;
    @SerializedName("category")
    @Expose
    private CategoryModel category;
    @SerializedName("subcategory")
    @Expose
    private ArrayList<CategoryModel> subcategory;
   /* @SerializedName("subcategory")
    @Expose
    private CategoryModel subcategory1;
*/
  /*  public CategoryModel getSubcategory1() {
        return subcategory1;
    }
*/
    public CategoryModel getCategory() {
        return category;
    }

    public void setCategory(CategoryModel category) {
        this.category = category;
    }

    public ArrayList<CategoryModel> getSubcategory() {
        return subcategory;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFromId() {
        return fromId;
    }

    public void setFromId(String fromId) {
        this.fromId = fromId;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getOfferId() {
        return offerId;
    }

    public void setOfferId(String offerId) {
        this.offerId = offerId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitleAr() {
        return titleAr;
    }

    public void setTitleAr(String titleAr) {
        this.titleAr = titleAr;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    public UserModel getFrom() {
        return from;
    }

    public void setFrom(UserModel from) {
        this.from = from;
    }

    public RequestModel getRequest() {
        return request;
    }

    public void setRequest(RequestModel request) {
        this.request = request;
    }
}
