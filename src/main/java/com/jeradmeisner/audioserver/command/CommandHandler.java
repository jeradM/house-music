package com.jeradmeisner.audioserver.command;

import com.jeradmeisner.audioserver.dao.WebsocketCommandMessage;
import com.jeradmeisner.audioserver.mopidy.service.MopidyCommandService;
import com.jeradmeisner.audioserver.service.CommandService;
import com.jeradmeisner.audioserver.snapcast.service.SnapcastCommandService;
import com.jeradmeisner.audioserver.spotify.service.SpotifyCommandService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CommandHandler {
    private final Logger log = LoggerFactory.getLogger(CommandHandler.class);

    private CommandService commandService;
    private MopidyCommandService mopidyCommandService;
    private SnapcastCommandService snapcastCommandService;
    private SpotifyCommandService spotifyCommandService;

    public CommandHandler(CommandService commandService,
                          MopidyCommandService mopidyCommandService,
                          SnapcastCommandService snapcastCommandService,
                          SpotifyCommandService spotifyCommandService) {
        this.commandService = commandService;
        this.mopidyCommandService = mopidyCommandService;
        this.snapcastCommandService = snapcastCommandService;
        this.spotifyCommandService = spotifyCommandService;
    }

    public void handleCommand(WebsocketCommandMessage message) {
        switch (message.target) {
//            case MOPIDY:
//                log.debug("Received mopidy command: {}", message.type);
//                mopidyCommandService.handleCommand(message);
//                break;
//            case SNAPCAST:
//                log.debug("Received snapcast command: {}", message.type);
//                snapcastCommandService.handleCommand(message);
//                break;
            case SPOTIFY:
                log.debug("Received spotify command {}", message.type);
                spotifyCommandService.handleCommand(message);
            default:
                log.debug("Received non-targeted command: {}:{}", message.target, message.type);
                commandService.handleCommand(message);
        }
    }
}
