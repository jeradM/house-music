package com.jeradmeisner.audioserver.mopidy.command.playlists;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jeradmeisner.audioserver.mopidy.command.MopidyCommand;

public abstract class PlaylistsCommand extends MopidyCommand {
    private static final String CONTROLLER = "core.playlists";

    @JsonProperty("method")
    public final String getMethod() {
        return String.format("%s.%s", CONTROLLER, getAction());
    }
}
