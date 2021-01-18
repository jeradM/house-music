
package com.jeradmeisner.audioserver.mopidy.dao;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MopidyArtist {
    public String name;
    public String uri;
}
