package com.jeradmeisner.audioserver.mopidy.notifications;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.jeradmeisner.audioserver.mopidy.interfaces.MopidyNotificationHandler;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;

@MopidyNotificationHandler(type = {"playback_state_changed"})
@JsonIgnoreProperties(ignoreUnknown = true)
public class PlaybackStateChangeNotification extends MopidyNotification {
    private String oldState;
    private String newState;

    public PlaybackStateChangeNotification() {
        super(NotificationType.PLAYBACK_STATE);
    }

    public String getOldState() {
        return oldState;
    }

    public void setOldState(String oldState) {
        this.oldState = oldState;
    }

    public String getNewState() {
        return newState;
    }

    public void setNewState(String newState) {
        this.newState = newState;
    }

    public void load() {
        this.oldState = this.additionalProperties.get("old_state").toString();
        this.newState = this.additionalProperties.get("new_state").toString();
    }
}
