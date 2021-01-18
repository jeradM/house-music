package com.jeradmeisner.audioserver.snapcast.command.group;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jeradmeisner.audioserver.snapcast.command.SnapcastCommand;

public abstract class GroupCommand extends SnapcastCommand {
    private static final String CONTROLLER = "Group";

    @Override
    @JsonProperty("method")
    public String getMethod() {
        return String.format("%s.%s", CONTROLLER, getAction());
    }
}
