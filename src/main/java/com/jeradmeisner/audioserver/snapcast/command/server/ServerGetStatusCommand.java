package com.jeradmeisner.audioserver.snapcast.command.server;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jeradmeisner.audioserver.snapcast.command.SnapcastCommandParams;

public class ServerGetStatusCommand extends ServerCommand {
    @JsonIgnore
    public SnapcastCommandParams params;
    @Override
    public String getAction() {
        return "GetStatus";
    }
}
