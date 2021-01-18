package com.jeradmeisner.audioserver.spotify.service;

import com.jeradmeisner.audioserver.dao.WebsocketCommandMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class SpotifyCommandService {
    private final Logger log = LoggerFactory.getLogger(SpotifyCommandService.class);

    private SpotifyService spotifyService;

    public SpotifyCommandService(SpotifyService spotifyService) {
        this.spotifyService = spotifyService;
    }

    public void handleCommand(WebsocketCommandMessage message) {
        switch (message.type) {
            case CHOOSE_CATEGORY:
                log.debug("Choose category command received: {}", message.id);
                spotifyService.getCategoryPlaylists(message.id);
            default:
                log.debug("Unhandled spotify command message: {}", message.type);
        }
    }
}
