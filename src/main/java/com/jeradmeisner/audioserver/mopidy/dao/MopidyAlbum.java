
package com.jeradmeisner.audioserver.mopidy.dao;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MopidyAlbum {
    public String date;
    public List<MopidyArtist> artists = null;
    public String name;
    public String uri;
}
