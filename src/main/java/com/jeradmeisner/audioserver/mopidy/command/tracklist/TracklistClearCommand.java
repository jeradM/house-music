package com.jeradmeisner.audioserver.mopidy.command.tracklist;

public class TracklistClearCommand extends TracklistCommand {
    @Override
    public String getAction() {
        return "clear";
    }
}
