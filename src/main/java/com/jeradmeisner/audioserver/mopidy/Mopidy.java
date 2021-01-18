package com.jeradmeisner.audioserver.mopidy;

import com.jeradmeisner.audioserver.mopidy.command.MopidyCommand;
import com.jeradmeisner.audioserver.mopidy.io.MopidyWebSocket;

public class Mopidy {
    private MopidyWebSocket socket;

    public Mopidy(MopidyWebSocket socket) {
        this.socket = socket;
        this.socket.connect();
    }

    public void sendCommand(MopidyCommand cmd) {
        socket.sendCommand(cmd);
    }
}
