package com.jeradmeisner.audioserver.snapcast.command.client;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jeradmeisner.audioserver.snapcast.command.SnapcastCommand;

public abstract class ClientCommand extends SnapcastCommand {
    private static final String CONTROLLER = "Client";

    @Override
    @JsonProperty("method")
    public String getMethod() {
        return String.format("%s.%s", CONTROLLER, getAction());
    }
}
