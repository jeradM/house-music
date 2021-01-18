package com.jeradmeisner.audioserver.mopidy.response;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.jeradmeisner.audioserver.exception.MusicHubException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MopidyResponse {
    private final Logger log = LoggerFactory.getLogger(MopidyResponse.class);

    public int id;
    public ResponseType type;
    public Map<String, Object> additionalProperties = new HashMap<>();

    ObjectMapper mapper = new ObjectMapper();

    public MopidyResponse() {
        this(null);
    }

    public MopidyResponse(ResponseType type) {
        this.type = type;
        this.mapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    public void load() {
        log.error("load is not implemented");
        throw new RuntimeException("load() is not implemented");
    }
}
