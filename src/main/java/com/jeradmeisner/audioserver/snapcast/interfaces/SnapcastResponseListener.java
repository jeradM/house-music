package com.jeradmeisner.audioserver.snapcast.interfaces;

import com.jeradmeisner.audioserver.snapcast.response.SnapcastResponse;

public interface SnapcastResponseListener {
    void onResponse(String id, String method, SnapcastResponse response);
}
