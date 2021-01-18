package com.jeradmeisner.audioserver.service;

import com.jeradmeisner.audioserver.state.StateContainer;
import org.springframework.stereotype.Service;

@Service
public class ClientService {
    private StateService stateService;
    private PlaylistService playlistService;

    public ClientService(StateService stateService, PlaylistService playlistService) {
        this.stateService = stateService;
        this.playlistService = playlistService;
    }

    public void setPlaylist(String clientId, String playlistId) {

    }
}
