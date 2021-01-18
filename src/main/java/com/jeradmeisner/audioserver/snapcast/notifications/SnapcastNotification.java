package com.jeradmeisner.audioserver.snapcast.notifications;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jeradmeisner.audioserver.snapcast.dao.SnapcastResult;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SnapcastNotification {
    public String jsonrpc;
    public String method;
    public SnapcastResult params;
}
