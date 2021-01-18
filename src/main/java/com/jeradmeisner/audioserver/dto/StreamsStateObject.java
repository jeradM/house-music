package com.jeradmeisner.audioserver.dto;

import com.jeradmeisner.audioserver.interfaces.StateObject;
import com.jeradmeisner.audioserver.state.Stream;

import java.util.List;

public class StreamsStateObject implements StateObject {
    public List<Stream> streams;

    public StreamsStateObject(List<Stream> streams) {
        this.streams = streams;
    }
}
