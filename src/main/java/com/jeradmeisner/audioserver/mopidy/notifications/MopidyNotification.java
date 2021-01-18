package com.jeradmeisner.audioserver.mopidy.notifications;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class MopidyNotification {
    public String event;
    public Map<String, Object> additionalProperties = new HashMap<>();

    ObjectMapper mapper = new ObjectMapper();
    public NotificationType type;

    public MopidyNotification(NotificationType type) {
        this.type = type;
        this.mapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
    }

    @JsonAnySetter
    public void setAdditionProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    public abstract void load() throws IOException;
}
