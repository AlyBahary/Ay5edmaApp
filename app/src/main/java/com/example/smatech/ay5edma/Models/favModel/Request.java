
package com.example.smatech.ay5edma.Models.favModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Request {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("shop_id")
    @Expose
    private String shopId;
    @SerializedName("body")
    @Expose
    private String body;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("longitude")
    @Expose
    private String longitude;
    @SerializedName("latitude")
    @Expose
    private String latitude;
    @SerializedName("category_id")
    @Expose
    private String categoryId;
    @SerializedName("subcategory_id")
    @Expose
    private String subcategoryId;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("created")
    @Expose
    private String created;
    @SerializedName("updated")
    @Expose
    private String updated;
    @SerializedName("user_rate")
    @Expose
    private String userRate;
    @SerializedName("provider_rate")
    @Expose
    private String providerRate;
    @SerializedName("favourite")
    @Expose
    private Boolean favourite;
    @SerializedName("User")
    @Expose
    private User user;
    @SerializedName("Shop")
    @Expose
    private Shop shop;
    @SerializedName("Category")
    @Expose
    private Category category;
    @SerializedName("Subcategory")
    @Expose
    private Subcategory subcategory;
    @SerializedName("finished")
    @Expose
    private Boolean finished;
    @SerializedName("category_name")
    @Expose
    private Object categoryName;
    @SerializedName("category_name_ar")
    @Expose
    private Object categoryNameAr;
    @SerializedName("sub_name")
    @Expose
    private Object subName;
    @SerializedName("sub_name_ar")
    @Expose
    private Object subNameAr;
    @SerializedName("user_name")
    @Expose
    private String userName;
    @SerializedName("shop_name")
    @Expose
    private String shopName;

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

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getSubcategoryId() {
        return subcategoryId;
    }

    public void setSubcategoryId(String subcategoryId) {
        this.subcategoryId = subcategoryId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public String getUserRate() {
        return userRate;
    }

    public void setUserRate(String userRate) {
        this.userRate = userRate;
    }

    public String getProviderRate() {
        return providerRate;
    }

    public void setProviderRate(String providerRate) {
        this.providerRate = providerRate;
    }

    public Boolean getFavourite() {
        return favourite;
    }

    public void setFavourite(Boolean favourite) {
        this.favourite = favourite;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Subcategory getSubcategory() {
        return subcategory;
    }

    public void setSubcategory(Subcategory subcategory) {
        this.subcategory = subcategory;
    }

    public Boolean getFinished() {
        return finished;
    }

    public void setFinished(Boolean finished) {
        this.finished = finished;
    }

    public Object getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(Object categoryName) {
        this.categoryName = categoryName;
    }

    public Object getCategoryNameAr() {
        return categoryNameAr;
    }

    public void setCategoryNameAr(Object categoryNameAr) {
        this.categoryNameAr = categoryNameAr;
    }

    public Object getSubName() {
        return subName;
    }

    public void setSubName(Object subName) {
        this.subName = subName;
    }

    public Object getSubNameAr() {
        return subNameAr;
    }

    public void setSubNameAr(Object subNameAr) {
        this.subNameAr = subNameAr;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

}
