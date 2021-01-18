
package com.jeradmeisner.audioserver.snapcast.dao;

import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SnapcastGroup {

    public List<SnapcastClient> clients;
    public String id;
    public Boolean muted;
    public String name;
    @JsonProperty("stream_id")
    public String streamId;
    public Map<String, Object> additionalProperties = new HashMap<>();

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
