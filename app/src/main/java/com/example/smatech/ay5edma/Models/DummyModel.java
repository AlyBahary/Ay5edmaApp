package com.example.smatech.ay5edma.Models;

public class DummyModel {
    String url , Desc;

    public DummyModel(String url, String desc) {
        this.url = url;
        Desc = desc;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDesc() {
        return Desc;
    }

    public void setDesc(String desc) {
        Desc = desc;
    }
}
