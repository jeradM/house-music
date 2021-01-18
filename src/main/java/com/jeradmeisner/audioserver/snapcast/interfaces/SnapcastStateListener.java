package com.jeradmeisner.audioserver.snapcast.interfaces;

import com.jeradmeisner.audioserver.snapcast.dao.SnapcastClient;
import com.jeradmeisner.audioserver.snapcast.dao.SnapcastServer;
import com.jeradmeisner.audioserver.snapcast.dao.SnapcastVolume;

public interface SnapcastStateListener {
    default void onClientConnected(String clientId) {}
    default void onClientDisconnected(String clientId) {}
    default void onClientVolumeChanged(String clientId, SnapcastVolume volume) {}
    default void onGroupStreamChanged() {}
    default void onStreamUpdate(String streamId) {}
    default void onServerStatus(SnapcastServer serverStatus) {}
    default void onClientStatus(SnapcastClient clientStatus) {}
}
