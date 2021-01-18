
package com.jeradmeisner.audioserver.snapcast.response;

import com.fasterxml.jackson.annotation.*;
import com.jeradmeisner.audioserver.snapcast.dao.SnapcastResult;

import java.util.HashMap;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SnapcastResponse {

    public Integer id;
    public String jsonrpc;
    public String method;
    public SnapcastResult result;
    public SnapcastResult params;
    public Map<String, Object> additionalProperties = new HashMap<>();

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
