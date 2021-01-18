package com.jeradmeisner.audioserver.service;

import com.jeradmeisner.audioserver.dao.WebsocketCommandMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class CommandService {
    private final Logger log = LoggerFactory.getLogger(CommandService.class);

    private PlaylistService playlistService;
    private StreamService streamService;

    public CommandService(PlaylistService playlistService, StreamService streamService) {
        this.playlistService = playlistService;
        this.streamService = streamService;
    }

    public void handleCommand(WebsocketCommandMessage message) {
        switch (message.type) {
            case SET_PLAYLIST:
                playlistService.setClientPlaylist(message.id, (String)message.params.get("playlistId"));
                break;
            case SET_STREAM:
                streamService.setClientStream(message.id, (String)message.params.get("streamId"));
            default:
                log.debug("Unhandled command: {}", message.type);
        }
    }
}
