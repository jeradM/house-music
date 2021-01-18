package com.jeradmeisner.audioserver.mopidy;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "mopidy")
@PropertySource("classpath:application.yml")
public class MopidyConfiguration {
    public String host;
    public List<InstanceConfig> instances = new ArrayList<>();

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public List<InstanceConfig> getInstances() {
        return instances;
    }

    public void setInstances(List<InstanceConfig> instances) {
        this.instances = instances;
    }

//    @Configuration
//    @EnableConfigurationProperties
//    @ConfigurationProperties(prefix = "mopidy.instances")
//    @PropertySource("classpath:application.yml")
    public static class InstanceConfig {
        public String name;
        public int port;

        public InstanceConfig() {
            System.out.println();
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getPort() {
            return port;
        }

        public void setPort(int port) {
            this.port = port;
        }
    }
}
