package com.jeradmeisner.audioserver.mopidy.io;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jeradmeisner.audioserver.mopidy.command.MopidyCommand;
import com.jeradmeisner.audioserver.mopidy.interfaces.MopidyConnectionListener;
import com.jeradmeisner.audioserver.mopidy.notifications.GenericNotification;
import com.jeradmeisner.audioserver.mopidy.notifications.MopidyNotification;
import com.jeradmeisner.audioserver.mopidy.interfaces.MopidyNotificationListener;
import com.jeradmeisner.audioserver.mopidy.response.MopidyResponse;
import com.jeradmeisner.audioserver.mopidy.interfaces.MopidyResponseListener;
import com.jeradmeisner.audioserver.mopidy.utils.MopidyMessageProcessor;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class MopidyWebSocket extends WebSocketClient {
     private final Logger log = LoggerFactory.getLogger(MopidyWebSocket.class);
    private final static Object lock = new Object();

    private static int requestId = 1;
    private static Map<Integer, String> commandsSent = new ConcurrentHashMap<>();
    private List<MopidyNotificationListener> notificationListeners = new ArrayList<>();
    private List<MopidyResponseListener> responseListeners = new ArrayList<>();
    private List<MopidyConnectionListener> connectionListeners = new ArrayList<>();

    private MopidyMessageProcessor processor;
    private ObjectMapper mapper;
    private String instanceId;

    public MopidyWebSocket(String instanceId, URI uri, ObjectMapper mapper, MopidyMessageProcessor processor) {
        super(uri);
        this.instanceId = instanceId;
        this.mapper = mapper;
        this.processor = processor;
    }

    public boolean sendCommand(MopidyCommand command) {
        synchronized (lock) {
            try {
                if (!this.isOpen()) {
                    this.reconnectBlocking();
                }
                commandsSent.putIfAbsent(requestId, command.getMethod());
                command.setId(requestId++);
                String cmdMsg = mapper.writeValueAsString(command);
                this.send(cmdMsg);
                return true;
            } catch (JsonProcessingException | InterruptedException e) {
                log.error(e.getMessage());
                return false;
            }
        }
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        this.connectionListeners.forEach(l -> l.onConnected(instanceId));
    }

    @Override
    public void onMessage(String message) {
        try {
            if (message.contains("jsonrpc")) {
                MopidyResponse tmpResp = mapper.readValue(message, MopidyResponse.class);
                String method = commandsSent.remove(tmpResp.id);
                MopidyResponse resp = processor.findResponseHandler(method);
                resp.additionalProperties = tmpResp.additionalProperties;
                resp.load();
                responseListeners.forEach(l -> l.onResponse(instanceId, resp));
            } else {
                GenericNotification genericNotification = mapper.readValue(message, GenericNotification.class);
                MopidyNotification notification = processor.findNotificationHandler(genericNotification.event);
                notification.additionalProperties = genericNotification.additionalProperties;
                notification.load();
                notificationListeners.forEach(l -> l.onNotification(instanceId, notification));
            }
        } catch (IOException e) {
            log.error("IOException: {}", e.getMessage());
        } catch (IllegalAccessException | InstantiationException e) {
            log.warn(e.getMessage());
        }
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        this.connectionListeners.forEach(l -> l.onDisconnected(instanceId));
    }

    @Override
    public void onError(Exception ex) {
        this.connectionListeners.forEach(l -> l.onError(instanceId, ex));
    }

    public void listenNotifications(MopidyNotificationListener listener) {
        this.notificationListeners.add(listener);
    }

    public void listenResponse(MopidyResponseListener listener) {
        this.responseListeners.add(listener);
    }

    public void listenConnection(MopidyConnectionListener listener) {
        this.connectionListeners.add(listener);
    }
}

