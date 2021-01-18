package com.jeradmeisner.audioserver.snapcast.command.client;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jeradmeisner.audioserver.snapcast.command.SnapcastCommandParams;

public class ClientSetLatencyCommand extends ClientCommand {
    @JsonProperty("params")
    public ClientSetLatencyParams params;

    public ClientSetLatencyCommand() {
        super();
        this.params = new ClientSetLatencyParams();
    }

    public ClientSetLatencyCommand(String id, int latency) {
        this();
        this.params.id = id;
        this.params.latency = latency;
    }

    public ClientSetLatencyCommand setId(String id) {
        this.params.id = id;
        return this;
    }

    public ClientSetLatencyCommand setlatency(int latency) {
        this.params.latency = latency;
        return this;
    }

    @Override
    public String getAction() {
        return "SetLatency";
    }

    private class ClientSetLatencyParams extends SnapcastCommandParams {
        private int latency;

        public int getLatency() {
            return latency;
        }

        public void setLatency(int latency) {
            this.latency = latency;
        }
    }
}
