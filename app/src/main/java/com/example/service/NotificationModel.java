package com.example.service;

import android.graphics.Bitmap;

public class NotificationModel {

    private int id = 0;
    private String title = "";
    private String content = "";
    private int icon = 0;
    private String bigText = "";
    private String category = "";
    private Bitmap bigPicture = null;

    public NotificationModel(int id, String title, String content, int icon, String bigText,
                             String category, Bitmap bigPicture) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.icon = icon;
        this.bigText = bigText;
        this.category = category;
        this.bigPicture = bigPicture;
    }

    public NotificationModel() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getBigText() {
        return bigText;
    }

    public void setBigText(String bigText) {
        this.bigText = bigText;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Bitmap getBigPicture() {
        return bigPicture;
    }

    public void setBigPicture(Bitmap bigPicture) {
        this.bigPicture = bigPicture;
    }
}