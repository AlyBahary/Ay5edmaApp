package com.example.smatech.ay5edma.Models.Modelss;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class StatusModel {

    @SerializedName("user")
    @Expose
    private UserModel user;

    @SerializedName("status")
    @Expose
    private Boolean status;

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("message_ar")
    @Expose
    private String message_ar;

    @SerializedName("categories")
    @Expose
    private ArrayList<CategoryModel> categories = null;

    @SerializedName("subcategories")
    @Expose
    private ArrayList<SubCategoryModel> subCategoryModels = null;

    @SerializedName("requests")
    @Expose
    private ArrayList<RequestModel> requests = null;
    @SerializedName("offers")
    @Expose
    private ArrayList<OffersModel> offers = null;
    @SerializedName("banks")
    @Expose
    private ArrayList<BankModel> banks = null;
    @SerializedName("notifications")
    @Expose
    private ArrayList<NotificationModel> notifications = null;
    @SerializedName("chats")
    @Expose
    private ArrayList<ChatModel> chats = null;
    @SerializedName("points")
    @Expose
    private ArrayList<PointsModel> points = null;
    @SerializedName("review")
    @Expose
    private ArrayList<ReviewModel> review = null;
    @SerializedName("category")
    @Expose
    private CategoryModel category;
    @SerializedName("subcategory")
    @Expose
    private CategoryModel subcategory;

    public ArrayList<ReviewModel> getReview() {
        return review;
    }

    public CategoryModel getCategory() {
        return category;
    }

    public ArrayList<PointsModel> getPoints() {
        return points;
    }

    @SerializedName("sliders")
    @Expose

    private ArrayList<SliderModel> sliders = null;

    public ArrayList<SliderModel> getSliders() {
        return sliders;
    }

    public CategoryModel getSubcategory() {
        return subcategory;
    }

    public ArrayList<ChatModel> getChats() {
        return chats;
    }

    public void setChats(ArrayList<ChatModel> chats) {
        this.chats = chats;
    }

    public ArrayList<NotificationModel> getNotifications() {
        return notifications;
    }

    public void setNotifications(ArrayList<NotificationModel> notifications) {
        this.notifications = notifications;
    }

    public ArrayList<BankModel> getBanks() {
        return banks;
    }

    public void setBanks(ArrayList<BankModel> banks) {
        this.banks = banks;
    }

    public ArrayList<OffersModel> getOffers() {
        return offers;
    }

    public void setOffers(ArrayList<OffersModel> offers) {
        this.offers = offers;
    }

    public ArrayList<CategoryModel> getCategories() {
        return categories;
    }

    public ArrayList<SubCategoryModel> getSubCategoryModels() {
        return subCategoryModels;
    }

    public void setSubCategoryModels(ArrayList<SubCategoryModel> subCategoryModels) {
        this.subCategoryModels = subCategoryModels;
    }

    public void setCategories(ArrayList<CategoryModel> categories) {
        this.categories = categories;
    }


    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getMessage_ar() {
        return message_ar;
    }

    public void setMessage_ar(String message_ar) {
        this.message_ar = message_ar;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<RequestModel> getRequests() {
        return requests;
    }

    public void setRequests(ArrayList<RequestModel> requests) {
        this.requests = requests;
    }
}
