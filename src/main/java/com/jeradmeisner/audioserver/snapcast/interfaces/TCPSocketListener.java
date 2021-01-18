package com.jeradmeisner.audioserver.snapcast.interfaces;

public interface TCPSocketListener {
    void onConnected();
    void onDisconnected();
    void onMessage(String message);
}
