package com.jeradmeisner.audioserver.mopidy.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jeradmeisner.audioserver.mopidy.Mopidy;
import com.jeradmeisner.audioserver.mopidy.MopidyConfiguration;
import com.jeradmeisner.audioserver.mopidy.command.MopidyCommand;
import com.jeradmeisner.audioserver.mopidy.command.playback.PlaybackGetCurrentTlTrackCommand;
import com.jeradmeisner.audioserver.mopidy.command.playback.PlaybackGetStateCommand;
import com.jeradmeisner.audioserver.mopidy.command.playback.PlaybackPlayCommand;
import com.jeradmeisner.audioserver.mopidy.command.playback.PlaybackStopCommand;
import com.jeradmeisner.audioserver.mopidy.command.tracklist.TracklistAddCommand;
import com.jeradmeisner.audioserver.mopidy.command.tracklist.TracklistClearCommand;
import com.jeradmeisner.audioserver.mopidy.command.tracklist.TracklistShuffleCommand;
import com.jeradmeisner.audioserver.mopidy.dao.MopidyTrack;
import com.jeradmeisner.audioserver.mopidy.interfaces.MopidyConnectionListener;
import com.jeradmeisner.audioserver.mopidy.interfaces.MopidyNotificationListener;
import com.jeradmeisner.audioserver.mopidy.interfaces.MopidyResponseListener;
import com.jeradmeisner.audioserver.mopidy.interfaces.MopidyStateListener;
import com.jeradmeisner.audioserver.mopidy.io.MopidyWebSocket;
import com.jeradmeisner.audioserver.mopidy.notifications.MopidyNotification;
import com.jeradmeisner.audioserver.mopidy.notifications.PlaybackStateChangeNotification;
import com.jeradmeisner.audioserver.mopidy.notifications.TrackPlaybackStateNotification;
import com.jeradmeisner.audioserver.mopidy.response.MopidyResponse;
import com.jeradmeisner.audioserver.mopidy.response.PlaybackStateResponse;
import com.jeradmeisner.audioserver.mopidy.response.TracklistTrackResponse;
import com.jeradmeisner.audioserver.mopidy.utils.MopidyMessageProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.*;

@Service
public class MopidyService implements MopidyNotificationListener, MopidyResponseListener, MopidyConnectionListener {
    private final Logger log = LoggerFactory.getLogger(MopidyService.class);

    private Map<String, Mopidy> instances = new HashMap<>();
    private List<MopidyStateListener> stateListeners = new ArrayList<>();

    private boolean connected = false;

    public MopidyService(MopidyConfiguration mopidyConfiguration,
                         @Qualifier("MopidyMapper") ObjectMapper mapper,
                         MopidyMessageProcessor messageProcessor) {
        String host = mopidyConfiguration.host;

        mopidyConfiguration.instances.forEach(instance -> {
            log.debug("Mopidy Instance: {} {}", instance.name, instance.port);
            URI uri = URI.create(String.format("ws://%s:%d/mopidy/ws", host, instance.port));
            MopidyWebSocket socket = new MopidyWebSocket(instance.name, uri, mapper, messageProcessor);
            socket.listenNotifications(this);
            socket.listenResponse(this);
            socket.listenConnection(this);
            Mopidy mopidy = new Mopidy(socket);
            instances.put(instance.name, mopidy);
        });
    }

    public void sendCommand(String id, MopidyCommand cmd) {
        Mopidy instance = instances.get(id);
        if (instance == null) {
            log.error("sendCommand: Instance not found: {}", id);
            return;
        }
        instance.sendCommand(cmd);
    }

    public void setPlaylist(String id, String playlistId, List<String> tracks) {
        String firstTrack = tracks.remove(0);
        sendCommand(id, new PlaybackStopCommand());
        sendCommand(id, new TracklistClearCommand());
        sendCommand(id, new TracklistAddCommand(Collections.singletonList(firstTrack)));
        sendCommand(id, new TracklistAddCommand(tracks));
        sendCommand(id, new TracklistShuffleCommand());
        sendCommand(id, new PlaybackPlayCommand());
        stateListeners.forEach(l -> l.onSetPlaylist(id, playlistId));
    }

    public void clearInstance(String id) {
        Mopidy instance = instances.get(id);
        if (instance == null) {
            log.warn("Instance not found: {}", id);
            return;
        }
        instance.sendCommand(new PlaybackStopCommand());
        instance.sendCommand(new TracklistClearCommand());
    }

    public void listenState(MopidyStateListener listener) {
        this.stateListeners.add(listener);
        if (connected)
            instances.keySet().forEach(this::requestRefresh);
    }

    @Override
    public void onNotification(String id, MopidyNotification notification) {
        log.debug("Mopidy notification: {} - {}", id, notification.type);
        switch (notification.type) {
            case PLAYBACK_STATE:
                PlaybackStateChangeNotification sn = (PlaybackStateChangeNotification)notification;
                stateListeners.forEach(l -> l.onPlaybackState(id, sn.getOldState(), sn.getNewState()));
                break;
            case TRACK_PLAYBACK_STATE:
                TrackPlaybackStateNotification tn = (TrackPlaybackStateNotification)notification;
                MopidyTrack track = tn.tlTrack != null ? tn.tlTrack.track : null;
                stateListeners.forEach(l -> l.onTrack(id, track));
                break;
            default:
                log.debug("Unhandled notification type: {}", notification.type);

        }
    }

    @Override
    public void onResponse(String id, MopidyResponse response) {
        log.debug("Mopidy Response: {} - {}", id, response.type);
        switch (response.type) {
            case PLAYBACK_STATE:
                PlaybackStateResponse pr = (PlaybackStateResponse)response;
                stateListeners.forEach(l -> l.onPlaybackState(id, null, pr.state));
                break;
            case TL_TRACK:
                TracklistTrackResponse tr = (TracklistTrackResponse)response;
                MopidyTrack track = tr.tlTrack != null ? tr.tlTrack.track : null;
                stateListeners.forEach(l -> l.onTrack(id, track));
                break;
            default:
                log.debug("Unhandled response type: {}", response.type);
        }
    }

    @Override
    public void onConnected(String id) {
        connected = true;
        if (this.stateListeners.size() > 0)
            requestRefresh(id);
    }

    private void requestRefresh(String id) {
        sendCommand(id, new PlaybackGetCurrentTlTrackCommand());
        sendCommand(id, new PlaybackGetStateCommand());
    }

    public static class InstanceConfig {
        public String name;
        public int port;
    }
}
