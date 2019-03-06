package com.example.smatech.ay5edma.Models;

public class ChatModel {
    String Owner;
    String User1;
    String OwnerMsgdate;

    public String getOwner() {
        return Owner;
    }

    public void setOwner(String owner) {
        Owner = owner;
    }

    public String getUser1() {
        return User1;
    }

    public void setUser1(String user1) {
        User1 = user1;
    }

    public String getOwnerMsgdate() {
        return OwnerMsgdate;
    }

    public void setOwnerMsgdate(String ownerMsgdate) {
        OwnerMsgdate = ownerMsgdate;
    }

    public String getUserMsgDate() {
        return UserMsgDate;
    }

    public void setUserMsgDate(String userMsgDate) {
        UserMsgDate = userMsgDate;
    }

    public ChatModel(String owner, String user1, String ownerMsgdate, String userMsgDate) {

        Owner = owner;
        User1 = user1;
        OwnerMsgdate = ownerMsgdate;
        UserMsgDate = userMsgDate;
    }

    String UserMsgDate;
}
