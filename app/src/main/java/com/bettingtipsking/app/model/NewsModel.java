package com.bettingtipsking.app.model;

public class NewsModel {
    String newsTitle;

    public NewsModel(String newsTitle) {
        this.newsTitle = newsTitle;
    }

    public String getNewsTitle() {
        return newsTitle;
    }

    public void setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
    }
}
