package com.bettingtipsking.app.Model;

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
