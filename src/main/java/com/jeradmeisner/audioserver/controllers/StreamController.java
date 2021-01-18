package com.jeradmeisner.audioserver.controllers;

import com.jeradmeisner.audioserver.command.CommandMessage;
import com.jeradmeisner.audioserver.command.CommandType;
import com.jeradmeisner.audioserver.mopidy.service.MopidyCommandService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/stream")
@CrossOrigin
public class StreamController {
    private final Logger log = LoggerFactory.getLogger(StreamController.class);

    private MopidyCommandService mopidyCommandService;

    public StreamController(MopidyCommandService mopidyCommandService) {
        this.mopidyCommandService = mopidyCommandService;
    }

    @PutMapping("/{id}/{cmd}")
    public void command(@PathVariable CommandType cmd, @PathVariable String id) {
        mopidyCommandService.handleCommand(new CommandMessage(cmd, id));
    }
}
