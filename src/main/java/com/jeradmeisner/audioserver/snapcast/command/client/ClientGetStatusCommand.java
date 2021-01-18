package com.jeradmeisner.audioserver.snapcast.command.client;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jeradmeisner.audioserver.snapcast.command.SnapcastCommandParams;

public class ClientGetStatusCommand extends ClientCommand {
    @JsonProperty("params")
    public ClientGetStatusParams params;

    public ClientGetStatusCommand() {
        this(null);
    }

    public ClientGetStatusCommand(String id) {
        super();
        params = new ClientGetStatusParams();
        params.id = id;
    }

    public void setId(String id) {
        this.params.id = id;
    }

    @Override
    public String getAction() {
        return "GetStatus";
    }

    private class ClientGetStatusParams extends SnapcastCommandParams {
    }
}
