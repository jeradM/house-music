package com.jeradmeisner.audioserver.controllers;

import com.jeradmeisner.audioserver.config.FrontendConfiguration;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/config")
public class ConfigController {
    private FrontendConfiguration frontendConfiguration;

    public ConfigController(FrontendConfiguration frontendConfiguration) {
        this.frontendConfiguration = frontendConfiguration;
    }

    @GetMapping("/")
    public FrontendConfiguration.ConfigDto getConfig() {
        return frontendConfiguration.getDto();
    }

    @PutMapping("/")
    public void updateConfig(FrontendConfiguration.ConfigDto config) {
        frontendConfiguration.setHassUrl(config.hassUrl);
        frontendConfiguration.setHassAccessToken(config.hassAccessToken);
    }
}
