package com.jeradmeisner.audioserver.mopidy.interfaces;

import com.jeradmeisner.audioserver.mopidy.notifications.MopidyNotification;

public interface MopidyNotificationListener {
    void onNotification(String id, MopidyNotification notification);
}
