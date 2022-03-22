package com.bettingtipsking.app.model;

public class NotificationsModel {
    String notificationText;

    public NotificationsModel(String notificationText) {
        this.notificationText = notificationText;
    }

    public String getNotificationText() {
        return notificationText;
    }

    public void setNotificationText(String notificationText) {
        this.notificationText = notificationText;
    }
}
