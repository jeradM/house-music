package com.jeradmeisner.audioserver.state;

public class Category {
    public String id;
    public String title;
    public String name;
    public String imageUri;

    public Category(String id, String title, String imageUri) {
        this.id = id;
        this.title = title;
        this.name = title;
        this.imageUri = imageUri;
    }
}
