package com.jeradmeisner.audioserver.controllers;

import com.jeradmeisner.audioserver.command.CommandMessage;
import com.jeradmeisner.audioserver.command.CommandType;
import com.jeradmeisner.audioserver.service.PlaylistService;
import com.jeradmeisner.audioserver.service.StreamService;
import com.jeradmeisner.audioserver.snapcast.service.SnapcastCommandService;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/client")
@CrossOrigin
public class ClientController {
    private PlaylistService playlistService;
    private SnapcastCommandService snapcastCommandService;
    private StreamService streamService;

    public ClientController(PlaylistService playlistService,
                            SnapcastCommandService snapcastCommandService,
                            StreamService streamService) {
        this.playlistService = playlistService;
        this.snapcastCommandService = snapcastCommandService;
        this.streamService = streamService;
    }

    @PutMapping("/{clientId}/setPlaylist/{playlistId}")
    public void setPlaylist(@PathVariable String clientId, @PathVariable String playlistId) {
        playlistService.setClientPlaylist(clientId, playlistId);
    }

    @PutMapping("/{clientId}/setStream/{streamId}")
    public void setStream(@PathVariable String clientId, @PathVariable String streamId) {
        streamService.setClientStream(clientId, streamId);
    }

    @PutMapping("/{clientId}/{cmd}")
    public void command(@PathVariable CommandType cmd, @PathVariable String clientId) {
        snapcastCommandService.handleCommand(new CommandMessage(cmd, clientId));
    }
}
