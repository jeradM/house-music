package com.jeradmeisner.audioserver.dto;

import com.jeradmeisner.audioserver.interfaces.StateObject;
import com.jeradmeisner.audioserver.state.Client;

import java.util.List;

public class ClientStateObject implements StateObject {
    public List<Client> clients;

    public ClientStateObject(List<Client> clients) {
        this.clients = clients;
    }
}
