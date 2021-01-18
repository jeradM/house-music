package com.jeradmeisner.audioserver.service;

import com.jeradmeisner.audioserver.mopidy.service.MopidyService;
import com.jeradmeisner.audioserver.snapcast.service.SnapcastService;
import com.jeradmeisner.audioserver.spotify.service.SpotifyService;
import com.jeradmeisner.audioserver.state.Client;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlaylistService {
    private SpotifyService spotifyService;
    private StateService stateService;
    private MopidyService mopidyService;
    private SnapcastService snapcastService;

    public PlaylistService(SpotifyService spotifyService,
                           StateService stateService,
                           MopidyService mopidyService,
                           SnapcastService snapcastService) {
        this.spotifyService = spotifyService;
        this.stateService = stateService;
        this.mopidyService = mopidyService;
        this.snapcastService = snapcastService;
    }

    public String getClientPlaylist(String clientId) {
        return stateService.getState().getClientPlaylist(clientId);
    }

    public void setClientPlaylist(String clientId, String playlist) {
        if (playlist.equals(getClientPlaylist(clientId))) return;
        Client client = stateService.getState().getClient(clientId);
        String group = client.group;
        String streamId = stateService.getStreamByPlaylist(playlist);
        if (streamId == null) {
            streamId = stateService.getAvailableStream(clientId);
            List<String> playlistTracks = spotifyService.getPlaylistTrackUris(playlist);
            mopidyService.setPlaylist(streamId, playlist, playlistTracks);
        }
        snapcastService.setGroupStream(group, streamId);
    }
}
