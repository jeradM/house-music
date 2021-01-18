package com.jeradmeisner.audioserver.mopidy.command.playback;

public class PlaybackStopCommand extends PlaybackCommand {
    @Override
    public String getAction() {
        return "stop";
    }
}
