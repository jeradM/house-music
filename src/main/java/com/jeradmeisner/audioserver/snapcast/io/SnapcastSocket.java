package com.jeradmeisner.audioserver.snapcast.io;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jeradmeisner.audioserver.snapcast.command.SnapcastCommand;
import com.jeradmeisner.audioserver.snapcast.command.server.ServerGetStatusCommand;
import com.jeradmeisner.audioserver.snapcast.interfaces.SnapcastConnectionListener;
import com.jeradmeisner.audioserver.snapcast.interfaces.SnapcastNotificationListener;
import com.jeradmeisner.audioserver.snapcast.interfaces.SnapcastResponseListener;
import com.jeradmeisner.audioserver.snapcast.interfaces.TCPSocketListener;
import com.jeradmeisner.audioserver.snapcast.notifications.SnapcastNotification;
import com.jeradmeisner.audioserver.snapcast.response.SnapcastResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SnapcastSocket implements TCPSocketListener {
    private final Logger log = LoggerFactory.getLogger(SnapcastSocket.class);

    private static int cmdId = 1;

    private List<SnapcastNotificationListener> notificationListeners = new ArrayList<>();
    private List<SnapcastResponseListener> responseListeners = new ArrayList<>();
    private List<SnapcastConnectionListener> connectionListeners = new ArrayList<>();

    private TCPSocketClient client;
    private Map<Integer, SentCommand> commandsSent;
    private ObjectMapper mapper;

    public SnapcastSocket(String host, int port) {
        this.client = new TCPSocketClient(host, port, this);
        this.commandsSent = new HashMap<>();
        this.mapper = new ObjectMapper();
        this.client.connect();
    }

    public void requestUpdate() {
        send(new ServerGetStatusCommand());
    }

    public boolean send(SnapcastCommand cmd) {
        log.debug("Sending snapcast command: {}", cmd.getMethod());
        try {
            SentCommand sentCmd = new SentCommand();
            sentCmd.method = cmd.getMethod();
            if (cmd.getParams() != null) {
                sentCmd.id = cmd.getParams().id;
            }
            cmd.setId(cmdId);
            commandsSent.put(cmdId++, sentCmd);
            this.client.send(mapper.writeValueAsString(cmd));
            return true;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void listenNotifications(SnapcastNotificationListener listener) {
        this.notificationListeners.add(listener);
    }

    public void listenResponse(SnapcastResponseListener listener) {
        this.responseListeners.add(listener);
    }

    public void listenConnection(SnapcastConnectionListener listener) {
        this.connectionListeners.add(listener);
    }

    @Override
    public void onConnected() {
        log.debug("Snapcast connected");
        connectionListeners.forEach(SnapcastConnectionListener::onConnected);
    }

    @Override
    public void onDisconnected() {
        log.debug("Snapcast disconnected");
        connectionListeners.forEach(SnapcastConnectionListener::onDisconnected);
    }

    @Override
    public void onMessage(String message) {
        try {
            if (message.contains("method\":")) {
                SnapcastNotification notification = mapper.readValue(message, SnapcastNotification.class);
                notificationListeners.forEach(l -> l.onNotification(notification));
                log.debug("Snapcast notification received: {}", notification.method);
            } else {
                SnapcastResponse resp = mapper.readValue(message, SnapcastResponse.class);
                SentCommand sentCmd = commandsSent.remove(resp.id);
                String method = sentCmd.method;
                String id = sentCmd.id;
                responseListeners.forEach(l -> l.onResponse(id, method, resp));
                log.debug("Snapcast response received: {}", method);
            }
        } catch (IOException e) {
            log.error("Error deserializing snapcast message: {}", e.getMessage());
        }
    }

    public static class SentCommand {
        public String method;
        public String id;
        public static SentCommand of(String method, String id) {
            SentCommand cmd = new SentCommand();
            cmd.method = method;
            cmd.id = id;
            return cmd;
        }
    }
}
