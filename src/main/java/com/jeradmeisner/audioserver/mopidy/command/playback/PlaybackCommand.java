package com.jeradmeisner.audioserver.mopidy.command.playback;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jeradmeisner.audioserver.mopidy.command.MopidyCommand;

public abstract class PlaybackCommand extends MopidyCommand {
    private static final String CONTROLLER = "core.playback";

    @JsonProperty("method")
    public final String getMethod() {
        return String.format("%s.%s", CONTROLLER, getAction());
    }
}
