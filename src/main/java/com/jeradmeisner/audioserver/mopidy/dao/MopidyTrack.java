
package com.jeradmeisner.audioserver.mopidy.dao;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MopidyTrack {
    public MopidyAlbum album;
    public String name;
    public long discNo;
    public String uri;
    public long length;
    public long trackNo;
    public List<MopidyArtist> artists = null;
    public String date;
    public long bitrate;
}
