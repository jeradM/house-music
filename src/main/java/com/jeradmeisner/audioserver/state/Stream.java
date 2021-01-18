package com.jeradmeisner.audioserver.state;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Arrays;
import java.util.stream.Collectors;

public class Stream {
    public enum StreamStatus {
        PLAYING, PAUSED, IDLE, STOPPED
    }
    public String id;
    public String currentPlaylist;
    public StreamStatus status;
    public Track track;

    @JsonProperty("imageUri")
    public String getImageUri() {
        if (this.track == null || this.track.album == null) return "";
        return this.track.album.imageUri;
    }

    @JsonProperty("name")
    public String getName() {
        return Arrays.stream(id.split("_"))
                .map(p -> p.substring(0, 1).toUpperCase() + p.substring(1))
                .collect(Collectors.joining());
    }
}
