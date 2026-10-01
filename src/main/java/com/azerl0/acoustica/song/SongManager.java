package com.azerl0.acoustica.song;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class SongManager {
    private final Map<String, Song> songs = new HashMap<>();


    public @Nullable Song getSong(@NotNull String songId) {
        return songs.getOrDefault(songId, null);
    }

    public void clearSongs() {
        songs.clear();
    }

    public boolean hasSong(String songId) {
        return songs.containsKey(songId);
    }

    public void addSong(String songId, Song song) {
        songs.put(songId, song);
    }

    public void deleteSong(String songId) {
        songs.remove(songId);
    }

    public Map<String, Song> getSongs() {
        return new HashMap<>(songs);
    }
}
