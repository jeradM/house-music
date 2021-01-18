package com.jeradmeisner.audioserver.mopidy.interfaces;

import com.jeradmeisner.audioserver.mopidy.dao.MopidyTrack;

public interface MopidyStateListener {
    default void onTrack(String id, MopidyTrack track) { }
    default void onPlaybackState(String id, String oldState, String newState) { }
    default void onSetPlaylist(String id, String playlistId) {}
}
