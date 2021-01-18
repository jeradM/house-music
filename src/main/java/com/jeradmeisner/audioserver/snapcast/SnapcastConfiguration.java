package com.jeradmeisner.audioserver.snapcast;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "snapcast")
@PropertySource("classpath:application.yml")
public class SnapcastConfiguration {
    public String host;
    public int port;
    public int volumeIncrement = 10;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getVolumeIncrement() {
        return volumeIncrement;
    }

    public void setVolumeIncrement(int volumeIncrement) {
        this.volumeIncrement = volumeIncrement;
    }
}
