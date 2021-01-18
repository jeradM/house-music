package com.jeradmeisner.audioserver.dao;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WebsocketCommandMessage {
    public enum MessageTarget {
        SNAPCAST, MOPIDY, SPOTIFY, NONE
    }

    public enum CommandType {
        next, prev, pause, resume, VOL_DOWN, VOL_UP, MUTE, UNMUTE, SET_STREAM, SET_PLAYLIST, CHOOSE_CATEGORY
    }

    public WebsocketCommandMessage() {
        return;
    }

    public String id;
    public MessageTarget target = MessageTarget.NONE;
    public CommandType type;
    public Map<String, Object> params;
}
