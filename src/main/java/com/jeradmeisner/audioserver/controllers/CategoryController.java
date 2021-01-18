package com.jeradmeisner.audioserver.controllers;

import com.jeradmeisner.audioserver.spotify.service.SpotifyService;
import com.jeradmeisner.audioserver.state.Category;
import com.jeradmeisner.audioserver.state.Playlist;
import com.wrapper.spotify.model_objects.specification.PlaylistSimplified;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
public class CategoryController {
    private SpotifyService spotifyService;

    public CategoryController(SpotifyService spotifyService) {
        this.spotifyService = spotifyService;
    }

    @GetMapping("/categories")
    public List<Category> get() {
        List<com.wrapper.spotify.model_objects.specification.Category> cats = spotifyService.getCategories();
        return cats.stream()
                .map(c -> new Category(c.getId(), c.getName(), c.getIcons()[0].getUrl()))
                .collect(Collectors.toList());
    }

    @GetMapping("/category/{id}/playlists")
    public List<Playlist> getPlaylists(@PathVariable String id) {
        List<PlaylistSimplified> playlists = spotifyService.getCategoryPlaylists(id);
        return playlists.stream()
                .map(p -> new Playlist(p.getId(), p.getUri(), p.getName(), p.getImages()[0].getUrl()))
                .collect(Collectors.toList());
    }
}
