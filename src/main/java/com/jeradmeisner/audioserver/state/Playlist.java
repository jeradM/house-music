package com.jeradmeisner.audioserver.state;

public class Playlist {
    public String id;
    public String uri;
    public String name;
    public String imageUri;

    public Playlist(String id, String uri, String name, String imageUri) {
        this.id = id;
        this.uri = uri;
        this.name = name;
        this.imageUri = imageUri;
    }
}
