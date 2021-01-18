package com.jeradmeisner.audioserver.spotify.interfaces;

import com.wrapper.spotify.model_objects.specification.Category;
import com.wrapper.spotify.model_objects.specification.PlaylistSimplified;

import java.util.List;

public interface SpotifyListener {
    default void onPlaylistsUpdated(List<PlaylistSimplified> playlists) {}
    default void onCategoriesUpdate(List<Category> categories) {}
}
