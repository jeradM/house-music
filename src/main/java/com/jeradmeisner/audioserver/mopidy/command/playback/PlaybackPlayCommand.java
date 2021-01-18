package com.jeradmeisner.audioserver.mopidy.command.playback;

public class PlaybackPlayCommand extends PlaybackCommand {
    @Override
    public String getAction() {
        return "play";
    }
}
