package com.jeradmeisner.audioserver.interfaces;

public interface PublishStateListener {
    void publish(StateObject state, String topic);
}
