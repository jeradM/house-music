package com.jeradmeisner.audioserver.state;

import com.jeradmeisner.audioserver.mopidy.dao.MopidyTrack;

public class Track {
    public String title;

    public Album album;

    public static Track fromDao(MopidyTrack dao) {
        Track track = new Track();
        track.album = Album.fromDao(dao.album);
        track.title = dao.name;
        return track;
    }
}
