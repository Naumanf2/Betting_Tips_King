package com.bettingtipsking.app.Model;

import java.util.List;

public class VideosMainModel {
    private List<VideosModel> newsVideosList;

    public VideosMainModel(List<VideosModel> newsVideosList) {
        this.newsVideosList = newsVideosList;
    }

    public List<VideosModel> getNewsVideosList() {
        return newsVideosList;
    }

    public void setNewsVideosList(List<VideosModel> newsVideosList) {
        this.newsVideosList = newsVideosList;
    }
}
