package com.jeradmeisner.audioserver.snapcast.interfaces;

import com.jeradmeisner.audioserver.snapcast.notifications.SnapcastNotification;

public interface SnapcastNotificationListener {
    void onNotification(SnapcastNotification notification);
}
