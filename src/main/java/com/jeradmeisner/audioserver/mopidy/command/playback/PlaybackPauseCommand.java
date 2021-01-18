package com.jeradmeisner.audioserver.mopidy.command.playback;

public class PlaybackPauseCommand extends PlaybackCommand {
    @Override
    public String getAction() {
        return "pause";
    }
}
