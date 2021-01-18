package com.jeradmeisner.audioserver.mopidy.response;

import com.jeradmeisner.audioserver.mopidy.interfaces.MopidyResponseHandler;

@MopidyResponseHandler(type = {"core.playback.get_state"})
public class PlaybackStateResponse extends MopidyResponse {
    public String state;

    public PlaybackStateResponse() {
        super(ResponseType.PLAYBACK_STATE);
    }

    @Override
    public void load() {
        this.state = additionalProperties.get("result").toString();
    }
}
