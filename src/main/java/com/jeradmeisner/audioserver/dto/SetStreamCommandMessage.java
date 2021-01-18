package com.jeradmeisner.audioserver.dto;

public class SetStreamCommandMessage {
    private String playlistId;

    public SetStreamCommandMessage() {
    }

    public SetStreamCommandMessage(String playlistId) {
        this.playlistId = playlistId;
    }

    public String getPlaylistId() {
        return playlistId;
    }

    public void setPlaylistId(String playlistId) {
        this.playlistId = playlistId;
    }
}
