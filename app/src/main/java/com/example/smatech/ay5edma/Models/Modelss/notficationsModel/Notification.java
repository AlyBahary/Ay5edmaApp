
package com.example.smatech.ay5edma.Models.Modelss.notficationsModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Notification {

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
    @SerializedName("from")
    @Expose
    private From from;
    @SerializedName("request")
    @Expose
    private Request request;
    @SerializedName("category")
    @Expose
    private Category___ category;
    @SerializedName("subcategory")
    @Expose
    private Subcategory___ subcategory;

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




    public From getFrom() {
        return from;
    }

    public void setFrom(From from) {
        this.from = from;
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public Category___ getCategory() {
        return category;
    }

    public void setCategory(Category___ category) {
        this.category = category;
    }

    public Subcategory___ getSubcategory() {
        return subcategory;
    }

    public void setSubcategory(Subcategory___ subcategory) {
        this.subcategory = subcategory;
    }

}
