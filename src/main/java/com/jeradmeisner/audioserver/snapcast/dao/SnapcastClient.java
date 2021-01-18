
package com.jeradmeisner.audioserver.snapcast.dao;

import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SnapcastClient {

    public SnapcastConfig config;
    public Boolean connected;
    public SnapcastHost host;
    public String id;
    public SnapcastLastSeen lastSeen;
    public SnapcastSnapclient snapclient;
    public Map<String, Object> additionalProperties = new HashMap<>();

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
