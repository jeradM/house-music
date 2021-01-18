package com.jeradmeisner.audioserver.mopidy.command.playback;

public class PlaybackGetCurrentTlTrackCommand extends PlaybackCommand {
    @Override
    public String getAction() {
        return "get_current_tl_track";
    }
}
