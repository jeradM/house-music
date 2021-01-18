package com.jeradmeisner.audioserver.service;

import com.jeradmeisner.audioserver.interfaces.PublishStateListener;
import com.jeradmeisner.audioserver.interfaces.StateObject;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class Publisher implements PublishStateListener {
    private SimpMessagingTemplate template;

    public Publisher(StateService state, SimpMessagingTemplate template) {
        this.template = template;
        state.listenPublishState(this);
    }

    @Override
    public void publish(StateObject state, String topic) {
        template.convertAndSend("/topic/" + topic, state);
    }
}
