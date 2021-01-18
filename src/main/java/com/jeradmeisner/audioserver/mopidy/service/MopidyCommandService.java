package com.jeradmeisner.audioserver.mopidy.service;

import com.jeradmeisner.audioserver.command.CommandMessage;
import com.jeradmeisner.audioserver.mopidy.command.playback.PlaybackNextCommand;
import com.jeradmeisner.audioserver.mopidy.command.playback.PlaybackPauseCommand;
import com.jeradmeisner.audioserver.mopidy.command.playback.PlaybackPreviousCommand;
import com.jeradmeisner.audioserver.mopidy.command.playback.PlaybackResumeCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class MopidyCommandService {
    private final Logger log = LoggerFactory.getLogger(MopidyCommandService.class);

    private MopidyService mopidyService;

    public MopidyCommandService(MopidyService mopidyService) {
        this.mopidyService = mopidyService;
    }

    public void handleCommand(CommandMessage message) {
        String id = message.getId();
        switch (message.getType()) {
            case next:
                mopidyService.sendCommand(id, new PlaybackNextCommand());
                break;
            case prev:
                mopidyService.sendCommand(id, new PlaybackPreviousCommand());
                break;
            case pause:
                mopidyService.sendCommand(id, new PlaybackPauseCommand());
                break;
            case resume:
                mopidyService.sendCommand(id, new PlaybackResumeCommand());
                break;
            default:
                log.debug("Unhandled mopidy command: {}", message.getType());
        }
    }
}
