package com.example.smatech.ay5edma.Models.Modelss;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class UserModel {@SerializedName("id")
@Expose
private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("longitude")
    @Expose
    private String longitude;
    @SerializedName("latitude")
    @Expose
    private String latitude;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("notification")
    @Expose
    private String notification;
    @SerializedName("created")
    @Expose
    private String created;
    @SerializedName("role")
    @Expose
    private String role;
    @SerializedName("login")
    @Expose
    private String login;
    @SerializedName("last_login")
    @Expose
    private String lastLogin;
    @SerializedName("activated")
    @Expose
    private String activate;
    @SerializedName("forget")
    @Expose
    private String forget;
    @SerializedName("points")
    @Expose
    private String points;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("rate")
    @Expose
    private String rate;
    @SerializedName("device")
    @Expose
    private String device;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("about")
    @Expose
    private String about;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("category_id")
    @Expose
    private String categoryId;
    @SerializedName("subcategory_id")
    @Expose
    private String subcategoryId;
    @SerializedName("subcategory_id2")
    @Expose
    private String subcategory_id2;
    @SerializedName("badge")
    @Expose
    private String badge;
    @SerializedName("birthday")
    @Expose
    private String birthday;
    @SerializedName("rejected")
    @Expose
    private String rejected;
    @SerializedName("accepted")
    @Expose
    private String accepted;
    @SerializedName("people")
    @Expose
    private String people;
    @SerializedName("ads_count")
    @Expose
    private Integer adsCount;
    @SerializedName("Category")
    @Expose
    private CategoryModel category;
    @SerializedName("Subcategory")
    @Expose
    private CategoryModel subcategory;
    @SerializedName("subcategory2")
    @Expose
    private CategoryModel subcategory2;
    @SerializedName("job")
    @Expose
    private String job;
    @SerializedName("job_ar")
    @Expose
    private Object jobAr;
    /*@SerializedName("images")
    @Expose
    private List<String> images = null;

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }*/

    public String getSubcategory_id2() {
        return subcategory_id2;
    }

    public String getRejected() {
        return rejected;
    }

    public void setRejected(String rejected) {
        this.rejected = rejected;
    }

    public String getAccepted() {
        return accepted;
    }

    public void setAccepted(String accepted) {
        this.accepted = accepted;
    }

    public String getPeople() {
        return people;
    }

    public void setPeople(String people) {
        this.people = people;
    }

    public void setCategory(CategoryModel category) {
        this.category = category;
    }

    public void setSubcategory(CategoryModel subcategory) {
        this.subcategory = subcategory;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public void setJobAr(Object jobAr) {
        this.jobAr = jobAr;
    }

    public CategoryModel getCategory() {
        return category;
    }

    public CategoryModel getSubcategory() {
        return subcategory;
    }

    public String getJob() {
        return job;
    }

    public Object getJobAr() {
        return jobAr;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBadge() {
        return badge;
    }

    public void setBadge(String badge) {
        this.badge = badge;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getNotification() {
        return notification;
    }

    public void setNotification(String notification) {
        this.notification = notification;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(String lastLogin) {
        this.lastLogin = lastLogin;
    }

    public String getActivate() {
        return activate;
    }

    public void setActivate(String activate) {
        this.activate = activate;
    }

    public String getForget() {
        return forget;
    }

    public void setForget(String forget) {
        this.forget = forget;
    }

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public Integer getAdsCount() {
        return adsCount;
    }

    public void setAdsCount(Integer adsCount) {
        this.adsCount = adsCount;
    }
}
