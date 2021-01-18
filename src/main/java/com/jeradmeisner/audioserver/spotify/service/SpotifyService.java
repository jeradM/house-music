package com.jeradmeisner.audioserver.spotify.service;

import com.jeradmeisner.audioserver.config.SpotifyConfiguration;
import com.jeradmeisner.audioserver.spotify.model.PlaylistTrackCache;
import com.neovisionaries.i18n.CountryCode;
import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.credentials.ClientCredentials;
import com.wrapper.spotify.model_objects.specification.*;
import com.wrapper.spotify.requests.authorization.client_credentials.ClientCredentialsRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
public class SpotifyService {
    private static final Logger log = LoggerFactory.getLogger(SpotifyService.class);

    private SpotifyApi api;
    private Date expires;

    private PlaylistTrackCache trackCache;
    private Map<String, String> albumArtCache = new ConcurrentHashMap<>();

    public SpotifyService(SpotifyConfiguration configuration) throws IOException, SpotifyWebApiException {
        String clientId = configuration.getClient().getId();
        String clientSecret = configuration.getClient().getSecret();
        trackCache = new PlaylistTrackCache();
        api = new SpotifyApi.Builder()
                .setClientId(clientId)
                .setClientSecret(clientSecret)
                .build();
        updateAccessToken();
    }

    public void updateAccessToken() throws IOException, SpotifyWebApiException {
        if (expires != null && (new Date()).before(expires)) return;
        log.debug("Spotify access token expired. Refreshing...");
        ClientCredentialsRequest request = api.clientCredentials().build();
        ClientCredentials creds = request.execute();
        api.setAccessToken(creds.getAccessToken());
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.SECOND, creds.getExpiresIn());
        expires = cal.getTime();
        log.debug("Spotify access token updated.");
    }

    public String getAlbumArtwork(String albumUri) {
        String albumId = albumUri;
        if (albumId.contains(":"))
            albumId = albumId.split(":")[2];
        String imageUrl = albumArtCache.get(albumId);
        if (imageUrl != null) return imageUrl;
        try {
            updateAccessToken();
            Album album = this.api.getAlbum(albumId).build().execute();
            Image[] images = album.getImages();
            if (images != null && images.length > 0) {
                imageUrl = images[0].getUrl();
                albumArtCache.putIfAbsent(albumId, imageUrl);
                return imageUrl;
            }
        } catch (IOException | SpotifyWebApiException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getPlaylistArtwork(String playlistId) {
        try {
            updateAccessToken();
            Playlist playlist = this.api.getPlaylist(playlistId).build().execute();
            Image[] images = playlist.getImages();
            if (images != null && images.length > 0) {
                return images[0].getUrl();
            }
        } catch (SpotifyWebApiException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<String> getPlaylistTrackUris(String playlistId) {
        if (playlistId.startsWith("spotify")) {
            playlistId = playlistId.split(":")[2];
        }
        List<String> trackIds = trackCache.get(playlistId);
        if (trackIds != null) {
            log.debug("Playlist tracklist found in cache");
            return trackIds;
        }
        try {
            updateAccessToken();
            List<PlaylistTrack> tracks = Arrays.asList(this.api.getPlaylistsTracks(playlistId)
                    .build()
                    .execute()
                    .getItems());

            trackIds = tracks.stream()
                    .map(t -> t.getTrack().getUri())
                    .collect(Collectors.toList());
            trackCache.put(playlistId, trackIds);
            return trackIds;
        } catch (SpotifyWebApiException | IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public List<Category> getCategories() {
        try {
            updateAccessToken();
            Paging<Category> cats = this.api.getListOfCategories().limit(12).country(CountryCode.US).locale("en_US").build().execute();
            return Arrays.asList(cats.getItems());
        } catch (SpotifyWebApiException | IOException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    public Category getCategory(String category) {
        try {
            updateAccessToken();
            final Category c = this.api.getCategory(category).build().execute();
            return c;
        } catch (SpotifyWebApiException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<PlaylistSimplified> getCategoryPlaylists(String category) {
        try {
            updateAccessToken();
            Paging<PlaylistSimplified> playlists = this.api.getCategorysPlaylists(category)
                    .country(CountryCode.US)
                    .limit(10)
                    .build()
                    .execute();
            return Arrays.asList(playlists.getItems());
        } catch (SpotifyWebApiException | IOException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }
}
