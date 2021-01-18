package com.jeradmeisner.audioserver.snapcast.command.server;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jeradmeisner.audioserver.snapcast.command.SnapcastCommand;

public abstract class ServerCommand extends SnapcastCommand {
    private static final String CONTROLLER = "Server";

    @Override
    @JsonProperty("method")
    public String getMethod() {
        return String.format("%s.%s", CONTROLLER, getAction());
    }
}
