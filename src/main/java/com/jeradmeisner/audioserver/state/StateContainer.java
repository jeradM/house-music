package com.jeradmeisner.audioserver.state;

import com.jeradmeisner.audioserver.interfaces.StateObject;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class StateContainer {
    private Map<String, Client> clients = new HashMap<>();
    private Map<String, Stream> streams = new HashMap<>();
    private List<Playlist> playlists = new ArrayList<>();
    private List<Category> categories = new ArrayList<>();

    public StateObject getStateObject() {
        return new StateObjectImpl(this);
    }

    public void addClient(Client client) {
        this.clients.put(client.id, client);
    }

    public Client getClient(String id) {
        return this.clients.get(id);
    }

    public void addStream(Stream stream) {
        this.streams.put(stream.id, stream);
    }

    public Stream getStream(String id) {
        return this.streams.get(id);
    }

    public List<Client> getConnectedClients() {
        return clients.values()
                .stream()
                .filter(c -> c.connected)
                .collect(Collectors.toList());
    }

    public List<Stream> getStreamList() {
        return new ArrayList<>(streams.values());
    }

    public List<Client> getStreamClients(String streamId) {
        return this.clients.values()
                .stream()
                .filter(c -> c.stream.equals(streamId))
                .collect(Collectors.toList());
    }

    public Map<Stream, List<Client>> getStreamClients() {
        Map<Stream, List<Client>> result = new HashMap<>();
        streams.values().forEach(stream -> {
            List<Client> streamClients = this.clients.values()
                    .stream()
                    .filter(c -> c.stream.equals(stream.id))
                    .collect(Collectors.toList());
            result.put(stream, streamClients);
        });
        return result;
    }

    public String getClientPlaylist(String id) {
        Client c = getClient(id);
        if (c == null) return null;
        Stream s = getStream(c.stream);
        if (s == null) return null;
        return s.currentPlaylist;
    }

    public Map<String, Client> getClients() {
        return clients;
    }

    public void setClients(Map<String, Client> clients) {
        this.clients = clients;
    }

    public Map<String, Stream> getStreams() {
        return streams;
    }

    public void setStreams(Map<String, Stream> streams) {
        this.streams = streams;
    }

    public List<Playlist> getPlaylists() {
        return this.playlists;
    }

    public void setPlaylists(List<Playlist> playlists) {
        this.playlists = playlists;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }
}
