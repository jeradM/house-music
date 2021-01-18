package com.jeradmeisner.audioserver.command;

public class CommandMessage {
    private CommandType type;
    private String id;

    public CommandMessage() {
    }

    public CommandMessage(CommandType type, String id) {
        this.type = type;
        this.id = id;
    }

    public CommandType getType() {
        return type;
    }

    public void setType(CommandType type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
