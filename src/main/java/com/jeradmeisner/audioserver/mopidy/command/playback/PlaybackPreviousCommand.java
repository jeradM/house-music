package com.jeradmeisner.audioserver.mopidy.command.playback;

public class PlaybackPreviousCommand extends PlaybackCommand {
    @Override
    public String getAction() {
        return "previous";
    }
}
