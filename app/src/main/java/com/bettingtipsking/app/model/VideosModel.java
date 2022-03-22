package com.bettingtipsking.app.model;

public class VideosModel {
    String newsTitle;

    public VideosModel(String newsTitle) {
        this.newsTitle = newsTitle;
    }

    public String getNewsTitle() {
        return newsTitle;
    }

    public void setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
    }
}
