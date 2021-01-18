package com.jeradmeisner.audioserver.spotify.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PlaylistTrack {
    Track track;
}
