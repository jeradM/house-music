package com.jeradmeisner.audioserver.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "frontend")
@PropertySource("classpath:application.yml")
public class FrontendConfiguration {
    private String hassUrl;
    private String hassAccessToken;

    private ConfigDto dto = new ConfigDto();

    public FrontendConfiguration() { }

    public String getHassUrl() {
        return hassUrl;
    }

    public void setHassUrl(String hassUrl) {
        this.hassUrl = hassUrl;
        this.dto.hassUrl = hassUrl;
    }

    public String getHassAccessToken() {
        return hassAccessToken;
    }

    public void setHassAccessToken(String hassAccessToken) {
        this.hassAccessToken = hassAccessToken;
        this.dto.hassAccessToken = hassAccessToken;
    }

    public ConfigDto getDto() {
        return this.dto;
    }

    public class ConfigDto {
        public String hassUrl;
        public String hassAccessToken;
    }
}
