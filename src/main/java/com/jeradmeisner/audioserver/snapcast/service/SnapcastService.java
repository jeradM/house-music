package com.jeradmeisner.audioserver.snapcast.service;

import com.jeradmeisner.audioserver.snapcast.SnapcastConfiguration;
import com.jeradmeisner.audioserver.snapcast.command.SnapcastCommand;
import com.jeradmeisner.audioserver.snapcast.command.group.GroupSetStreamCommand;
import com.jeradmeisner.audioserver.snapcast.command.server.ServerGetStatusCommand;
import com.jeradmeisner.audioserver.snapcast.interfaces.SnapcastConnectionListener;
import com.jeradmeisner.audioserver.snapcast.interfaces.SnapcastNotificationListener;
import com.jeradmeisner.audioserver.snapcast.interfaces.SnapcastResponseListener;
import com.jeradmeisner.audioserver.snapcast.interfaces.SnapcastStateListener;
import com.jeradmeisner.audioserver.snapcast.io.SnapcastSocket;
import com.jeradmeisner.audioserver.snapcast.notifications.SnapcastNotification;
import com.jeradmeisner.audioserver.snapcast.response.SnapcastResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SnapcastService implements SnapcastNotificationListener, SnapcastResponseListener, SnapcastConnectionListener {
    private final Logger log = LoggerFactory.getLogger(SnapcastService.class);

    private List<SnapcastStateListener> stateListeners = new ArrayList<>();
    private SnapcastSocket socket;

    public SnapcastService(SnapcastConfiguration snapcastConfig) {
        this.socket = new SnapcastSocket(snapcastConfig.host, snapcastConfig.port);
        this.socket.listenConnection(this);
        this.socket.listenNotifications(this);
        this.socket.listenResponse(this);
    }

    public void listenState(SnapcastStateListener listener) {
        this.stateListeners.add(listener);
        this.socket.send(new ServerGetStatusCommand());
    }

    public void setGroupStream(String groupId, String streamId) {
        GroupSetStreamCommand command = new GroupSetStreamCommand();
        command.setGroupId(groupId).setStreamId(streamId);
        this.socket.send(command);
    }

    @Override
    public void onNotification(SnapcastNotification notification) {
        switch (notification.method) {
            default:
                log.warn("Unhandled notification method: {}", notification.method);
        }
    }

    @Override
    public void onResponse(String id, String method, SnapcastResponse response) {
        log.debug("Snapcast response received: {}", method);
        switch (method) {
            case "Server.GetStatus":
                stateListeners.forEach(l -> l.onServerStatus(response.result.server));
                break;
            case "Client.SetVolume":
                stateListeners.forEach(l -> l.onClientVolumeChanged(id, response.result.volume));
                break;
            case "Group.SetStream":
                stateListeners.forEach(SnapcastStateListener::onGroupStreamChanged);
                sendCommand(new ServerGetStatusCommand());
                break;
            default:
                log.warn("Unhandled response method: {}", method);
        }
    }

    @Override
    public void onConnected() {
        log.debug("Snapcast connected");
        this.socket.send(new ServerGetStatusCommand());
    }

    public void sendCommand(SnapcastCommand command) {
        this.socket.send(command);
    }
}
