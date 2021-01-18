package com.jeradmeisner.audioserver.mopidy.utils;

import com.jeradmeisner.audioserver.mopidy.interfaces.MopidyNotificationHandler;
import com.jeradmeisner.audioserver.mopidy.interfaces.MopidyResponseHandler;
import com.jeradmeisner.audioserver.mopidy.notifications.MopidyNotification;
import com.jeradmeisner.audioserver.mopidy.response.MopidyResponse;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Component
public class MopidyMessageProcessor {
    private final Logger log = LoggerFactory.getLogger(MopidyMessageProcessor.class);

    private Reflections reflections;
    private Set<Class<?>> responseHandlers;
    private Set<Class<?>> notificationHandlers;

    public MopidyMessageProcessor() {
        this.reflections = new Reflections("com.jeradmeisner.audioserver.mopidy");
        this.responseHandlers = reflections.getTypesAnnotatedWith(MopidyResponseHandler.class);
        this.notificationHandlers = reflections.getTypesAnnotatedWith(MopidyNotificationHandler.class);
    }

    public MopidyResponse findResponseHandler(String type) throws IllegalAccessException, InstantiationException {
        for (Class<?> c : responseHandlers) {
            MopidyResponseHandler responseHandler = c.getAnnotation(MopidyResponseHandler.class);
            List<String> typeList = Arrays.asList(responseHandler.type());
            if (typeList.contains(type)) {
                Object o = c.newInstance();
                return (MopidyResponse)o;
            }
        }
        throw new InstantiationException("Unable to find a suitable response handler for " + type);
    }

    public MopidyNotification findNotificationHandler(String type) throws IllegalAccessException, InstantiationException {
        for (Class<?> c : notificationHandlers) {
            MopidyNotificationHandler notificationHandler = c.getAnnotation(MopidyNotificationHandler.class);
            List<String> typeList = Arrays.asList(notificationHandler.type());
            if (typeList.contains(type)) {
                Object o = c.newInstance();
                return (MopidyNotification) o;
            }
        }
        throw new InstantiationException("Unable to find a suitable notification handler for " + type);
    }
}
