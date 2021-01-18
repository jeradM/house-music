package com.jeradmeisner.audioserver.mopidy.interfaces;

import com.jeradmeisner.audioserver.mopidy.response.MopidyResponse;

public interface MopidyResponseListener {
    void onResponse(String id, MopidyResponse response);
}
