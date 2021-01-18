
package com.jeradmeisner.audioserver.mopidy.dao;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TracklistTrack {
    public MopidyTrack track;
    public long tlid;
}
