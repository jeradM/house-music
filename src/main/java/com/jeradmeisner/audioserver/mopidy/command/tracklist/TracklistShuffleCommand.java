package com.jeradmeisner.audioserver.mopidy.command.tracklist;

public class TracklistShuffleCommand extends TracklistCommand {
    @Override
    public String getAction() {
        return "shuffle";
    }
}
