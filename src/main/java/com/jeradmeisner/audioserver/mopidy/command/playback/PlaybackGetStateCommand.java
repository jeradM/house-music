package com.jeradmeisner.audioserver.mopidy.command.playback;

public class PlaybackGetStateCommand extends PlaybackCommand {
    @Override
    public String getAction() {
        return "get_state";
    }
}
