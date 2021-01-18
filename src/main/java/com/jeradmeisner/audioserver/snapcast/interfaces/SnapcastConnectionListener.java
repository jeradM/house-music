package com.jeradmeisner.audioserver.snapcast.interfaces;

public interface SnapcastConnectionListener {
    default void onConnected() {}
    default void onDisconnected() {}
}
