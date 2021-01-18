
package com.jeradmeisner.audioserver.snapcast.dao;

import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SnapcastResult {

    public String id;
    public SnapcastClient client;
    public SnapcastVolume volume;
    public Integer latency;
    public String name;
    public Boolean mute;
    public String streamId;
    public Integer major;
    public Integer minor;
    public Integer patch;
    public SnapcastGroup group;
    public SnapcastServer server;
    public SnapcastStream_ stream;
    public Map<String, Object> additionalProperties = new HashMap<>();

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
