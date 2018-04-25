package com.example.android.newsapp;

/**
 * Created by JOAO on 25-Apr-18.
 */

public class News {

    private String title;
    private String author;
    private String category;
    private String date;
    private String url;
    private String imageUrl;

    public News(String title, String author, String category, String date, String url, String imageUrl) {
        this.title = title;
        this.author = author;
        this.category = category;
        this.date = date;
        this.url = url;
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getCategory() {
        return category;
    }

    public String getDate() {
        return date;
    }

    public String getUrl() {
        return url;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
