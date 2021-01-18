package com.jeradmeisner.audioserver.mopidy.command.playback;

public class PlaybackResumeCommand extends PlaybackCommand {
    @Override
    public String getAction() {
        return "resume";
    }
}
