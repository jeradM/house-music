package com.jeradmeisner.audioserver.service;

import com.jeradmeisner.audioserver.dto.ClientStateObject;
import com.jeradmeisner.audioserver.dto.StreamsStateObject;
import com.jeradmeisner.audioserver.interfaces.PublishStateListener;
import com.jeradmeisner.audioserver.interfaces.StateObject;
import com.jeradmeisner.audioserver.mopidy.service.MopidyService;
import com.jeradmeisner.audioserver.snapcast.dao.SnapcastServer;
import com.jeradmeisner.audioserver.snapcast.dao.SnapcastVolume;
import com.jeradmeisner.audioserver.snapcast.interfaces.SnapcastStateListener;
import com.jeradmeisner.audioserver.snapcast.service.SnapcastService;
import com.jeradmeisner.audioserver.spotify.service.SpotifyService;
import com.jeradmeisner.audioserver.state.Client;
import com.jeradmeisner.audioserver.state.StateContainer;
import com.jeradmeisner.audioserver.state.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StateService implements SnapcastStateListener {
    private final Logger log = LoggerFactory.getLogger(StateService.class);

    private List<PublishStateListener> publishListeners = new ArrayList<>();
    private MopidyService mopidyService;
    private SpotifyService spotifyService;

    private StateContainer state;

    public StateService(StateContainer state,
                        SnapcastService snapcastService,
                        MopidyService mopidyService,
                        SpotifyService spotifyService) {
        this.state = state;
        this.mopidyService = mopidyService;
        this.spotifyService = spotifyService;
        snapcastService.listenState(this);
        spotifyService.getCategories();
    }

    public void listenPublishState(PublishStateListener listener) {
        this.publishListeners.add(listener);
    }

    public void publishState() {
        if (this.publishListeners == null) return;
//        this.publishListeners.forEach(l -> l.publish(state.getStateObject()));
    }

    public void publish(StateObject obj, String type) {
        if (this.publishListeners == null) return;
        this.publishListeners.forEach(l -> l.publish(obj, type));
    }

    public void publishClients() {
        publish(new ClientStateObject(state.getConnectedClients()), "clients");
    }

    public void publishStreams() {
        publish(new StreamsStateObject(state.getStreamList()), "streams");
    }

    public String getStreamByPlaylist(String playlistId) {
        return state.getStreams().values()
                .stream()
                .filter(s -> playlistId.equals(s.currentPlaylist))
                .map(s -> s.id)
                .findFirst()
                .orElse(null);
    }

    public String getAvailableStream(String clientId) {
        Client client = state.getClient(clientId);
        if (client == null) {
            log.error("Client not found: {}", clientId);
            return null;
        }
        List<Client> streamClients = state.getStreamClients(client.stream);
        if (streamClients != null && streamClients.size() == 1) return client.stream;
        return state.getStreams().values()
                .stream()
                .filter(s -> state.getStreamClients(s.id).size() == 0)
                .map(s -> s.id)
                .findFirst()
                .orElse(client.stream);
    }

    @Override
    public void onServerStatus(SnapcastServer server) {
        server.streams.forEach(s -> {
            Stream stream = state.getStream(s.id);
            if (stream == null) {
                stream = new Stream();
                stream.id = s.id;
                state.addStream(stream);
            }
        });
        server.groups.forEach(group -> {
            group.clients.forEach(c -> {
                Client client = state.getClient(c.id);
                if (client == null) {
                    client = new Client();
                    client.id = c.id;
                    state.addClient(client);
                }
                client.group = group.id;
                client.muted = c.config.volume.muted;
                client.volume = c.config.volume.percent;
                client.stream = group.streamId;
                client.connected = c.connected;
            });
        });


        state.getStreams().values()
                .stream()
                .filter(s -> s.status != Stream.StreamStatus.STOPPED && state.getStreamClients(s.id).size() == 0)
                .collect(Collectors.toList())
                .forEach(s -> {
                    s.currentPlaylist = null;
                    mopidyService.clearInstance(s.id);
                });

        log.debug("Snapcast Server state updated");
        publishClients();
        publishStreams();
    }

    @Override
    public void onClientVolumeChanged(String clientId, SnapcastVolume volume) {
        Client client = state.getClient(clientId);
        if (client == null) {
            log.error("Client not found: {}", clientId);
            return;
        }
        client.volume = volume.percent;
        client.muted = volume.muted;
        publishClients();
    }

    public StateContainer getState() {
        return state;
    }
}
