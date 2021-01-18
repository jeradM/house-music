package com.jeradmeisner.audioserver.snapcast.command;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class SnapcastCommand {
    private int id;
    public SnapcastCommandParams params = null;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @JsonProperty("jsonrpc")
    public String getJsonrpc() {
        return "2.0";
    }

    public SnapcastCommandParams getParams() {
        return null;
    }

    public abstract String getMethod();
    @JsonIgnore
    public abstract String getAction();
}
