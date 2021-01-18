package com.jeradmeisner.audioserver.mopidy.command.playback;

public class PlaybackNextCommand extends PlaybackCommand {
    @Override
    public String getAction() {
        return "next";
    }
}
