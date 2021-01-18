package com.jeradmeisner.audioserver.state;

import com.jeradmeisner.audioserver.mopidy.dao.MopidyAlbum;

public class Album {
    public String title;
    public String artist;
    public String imageUri;
    public String uri;

    public static Album fromDao(MopidyAlbum mopidyAlbum) {
        Album a = new Album();
        a.title = mopidyAlbum.name;
        a.artist = mopidyAlbum.artists.get(0).name;
        a.uri = mopidyAlbum.uri;
        return a;
    }
}
