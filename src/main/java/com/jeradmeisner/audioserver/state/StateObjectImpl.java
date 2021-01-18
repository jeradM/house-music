package com.jeradmeisner.audioserver.state;

import com.jeradmeisner.audioserver.interfaces.StateObject;

import java.util.List;

public class StateObjectImpl implements StateObject {
    private List<Client> clients;
    private List<Stream> streams;

    public StateObjectImpl(StateContainer state) {
        this.clients = state.getConnectedClients();
        this.streams = state.getStreamList();
    }

    public List<Client> getClients() {
        return clients;
    }

    public void setClients(List<Client> clients) {
        this.clients = clients;
    }

    public List<Stream> getStreams() {
        return streams;
    }

    public void setStreams(List<Stream> streams) {
        this.streams = streams;
    }
}

