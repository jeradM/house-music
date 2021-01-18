package com.jeradmeisner.audioserver.service;

import com.jeradmeisner.audioserver.mopidy.dao.MopidyTrack;
import com.jeradmeisner.audioserver.mopidy.interfaces.MopidyStateListener;
import com.jeradmeisner.audioserver.mopidy.service.MopidyService;
import com.jeradmeisner.audioserver.snapcast.service.SnapcastService;
import com.jeradmeisner.audioserver.spotify.service.SpotifyService;
import com.jeradmeisner.audioserver.state.Client;
import com.jeradmeisner.audioserver.state.Stream;
import com.jeradmeisner.audioserver.state.Track;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StreamService implements MopidyStateListener {
    private final Logger log = LoggerFactory.getLogger(StreamService.class);

    private StateService stateService;
    private SpotifyService spotifyService;
    private SnapcastService snapcastService;

    public StreamService(StateService stateService,
                         SpotifyService spotifyService,
                         MopidyService mopidyService,
                         SnapcastService snapcastService) {
        this.stateService = stateService;
        this.spotifyService = spotifyService;
        this.snapcastService = snapcastService;
        mopidyService.listenState(this);
    }

    public boolean isStreamInUse(String streamId) {
        return stateService.getState().getStreamClients(streamId).size() > 0;
    }

    public List<Stream> findAvailableStreams() {
        Map<Stream, List<Client>> streams = stateService.getState().getStreamClients();
        return streams.keySet()
                .stream()
                .filter(stream -> streams.get(stream) == null || streams.get(stream).size() == 0)
                .collect(Collectors.toList());
    }

    public void setClientStream(String clientId, String streamId) {
        Client client = stateService.getState().getClient(clientId);
        if (client == null) {
            log.warn("Client not found: {}", clientId);
            return;
        }
        String group = client.group;
        snapcastService.setGroupStream(group, streamId);
    }

    @Override
    public void onTrack(String id, MopidyTrack track) {
        Stream stream = stateService.getState().getStream(id);
        if (stream == null) {
            stream = new Stream();
            stream.id = id;
            stateService.getState().addStream(stream);
        }
        if (track != null) {
            stream.track = Track.fromDao(track);
            if (stream.track.album.imageUri == null)
                stream.track.album.imageUri = spotifyService.getAlbumArtwork(track.album.uri);
        }
        log.debug("Mopidy Track updated: {} - {}", id, track != null ? track.name : "No Track");
        stateService.publishStreams();
    }

    @Override
    public void onPlaybackState(String id, String oldState, String newState) {
        Stream stream = stateService.getState().getStream(id);
        if (stream == null) {
            log.debug("onPlaybackState: Stream not found: {}", id);
            return;
        }
        stream.status = Stream.StreamStatus.valueOf(newState.toUpperCase());
        log.debug("Mopidy playback state updated: {} - {}", id, newState);
        stateService.publishStreams();
    }

    @Override
    public void onSetPlaylist(String id, String playlistId) {
        Stream stream = stateService.getState().getStream(id);
        if (stream == null) return;
        stream.currentPlaylist = playlistId;
    }
}
