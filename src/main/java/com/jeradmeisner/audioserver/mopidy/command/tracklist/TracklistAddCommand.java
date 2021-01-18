package com.jeradmeisner.audioserver.mopidy.command.tracklist;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class TracklistAddCommand extends TracklistCommand {
    private TracklistAddParams params;

    public TracklistAddCommand(List<String> uris) {
        super();
        params = new TracklistAddParams();
        params.setUris(uris);
    }

    public TracklistAddParams getParams() {
        return params;
    }

    public void setParams(TracklistAddParams params) {
        this.params = params;
    }

    @Override
    public String getAction() {
        return "add";
    }

    private class TracklistAddParams {
        private List<String> uris = new ArrayList<>();

        public List<String> getUris() {
            return uris;
        }

        public void setUris(List<String> uris) {
            this.uris = uris;
        }
    }
}
