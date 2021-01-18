package com.jeradmeisner.audioserver.mopidy.response;

import com.jeradmeisner.audioserver.mopidy.interfaces.MopidyResponseHandler;

@MopidyResponseHandler(type = {"core.playback.stop"})
public class PlaybackStopResponse extends PlaybackStateResponse {
    @Override
    public void load() {
        this.state = "STOPPED";
    }
}
