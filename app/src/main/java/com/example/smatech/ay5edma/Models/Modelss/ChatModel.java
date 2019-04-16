package com.example.smatech.ay5edma.Models.Modelss;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChatModel {
    @SerializedName("chat_id")
    @Expose
    private String chatId;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("to_id")
    @Expose
    private String toId;
    @SerializedName("from_id")
    @Expose
    private String fromId;
    @SerializedName("date")
    @Expose
    private String date;
@SerializedName("seen")
    @Expose
    private String seen;

    public String getSeen() {
        return seen;
    }

    public void setSeen(String seen) {
        this.seen = seen;
    }

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getToId() {
        return toId;
    }

    public void setToId(String toId) {
        this.toId = toId;
    }

    public String getFromId() {
        return fromId;
    }

    public void setFromId(String fromId) {
        this.fromId = fromId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ChatModel(String chatId, String message, String toId, String fromId, String date) {
        this.chatId = chatId;
        this.message = message;
        this.toId = toId;
        this.fromId = fromId;
        this.date = date;
    }

    public ChatModel(String chatId, String message, String toId, String fromId, String date, String seen) {
        this.chatId = chatId;
        this.message = message;
        this.toId = toId;
        this.fromId = fromId;
        this.date = date;
        this.seen = seen;
    }
}
