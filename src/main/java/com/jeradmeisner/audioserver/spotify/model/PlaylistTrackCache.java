package com.jeradmeisner.audioserver.spotify.model;

import lombok.Data;

import java.time.Duration;
import java.time.Instant;
import java.util.*;

@Data
public class PlaylistTrackCache {
    private static final long EXPIRE_HOURS = 24;
    private Map<String, TrackList> trackCache = new HashMap<>();

    public void put(String playlistId, List<String> trackIds) {
        TrackList tl = trackCache.get(playlistId);
        if (tl == null) tl = new TrackList();
        tl.expires = Instant.now().plus(Duration.ofHours(EXPIRE_HOURS));
        tl.trackIds = trackIds;
        trackCache.put(playlistId, tl);
    }

    public List<String> get(String playlistId) {
        TrackList tl = trackCache.get(playlistId);
        if (tl == null) return null;
        if (Instant.now().isAfter(tl.expires)) return null;
        return tl.trackIds;
    }

    private class TrackList {
        Instant expires;
        List<String> trackIds;
    }
}
