package com.jeradmeisner.audioserver.mopidy.interfaces;

public interface MopidyConnectionListener {
    default void onConnected(String id) {}
    default void onDisconnected(String id) {}
    default void onError(String id, Exception e) {}
}
