package com.jeradmeisner.audioserver.mopidy.command.tracklist;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jeradmeisner.audioserver.mopidy.command.MopidyCommand;

public abstract class TracklistCommand extends MopidyCommand {
    private static final String CONTROLLER = "core.tracklist";

    @JsonProperty("method")
    public final String getMethod() {
        return String.format("%s.%s", CONTROLLER, getAction());
    }
}
