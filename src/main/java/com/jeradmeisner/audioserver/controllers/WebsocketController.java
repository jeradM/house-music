package com.jeradmeisner.audioserver.controllers;

import com.jeradmeisner.audioserver.command.CommandHandler;
import com.jeradmeisner.audioserver.dao.WebsocketCommandMessage;
import com.jeradmeisner.audioserver.interfaces.StateObject;
import com.jeradmeisner.audioserver.spotify.service.SpotifyService;
import com.jeradmeisner.audioserver.state.StateContainer;
import com.wrapper.spotify.model_objects.specification.PlaylistSimplified;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class WebsocketController {
    private StateContainer state;
    private CommandHandler commandHandler;
    private SpotifyService spotifyService;

    public WebsocketController(StateContainer state, CommandHandler commandHandler, SpotifyService spotifyService) {
        this.state = state;
        this.commandHandler = commandHandler;
        this.spotifyService = spotifyService;
    }

    @SubscribeMapping("/init")
    public StateObject init() {
        return state.getStateObject();
    }

    @GetMapping("/getPlaylists/{categoryId}")
    public List<PlaylistSimplified> getPlaylists(@PathVariable String categoryId) {
        System.out.println(categoryId);
        return spotifyService.getCategoryPlaylists(categoryId);
    }

    @MessageMapping("/cmd")
    public void cmd(WebsocketCommandMessage message) {
        commandHandler.handleCommand(message);
    }
}
