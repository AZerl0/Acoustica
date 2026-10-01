package com.azerl0.acoustica.song;

import net.kyori.adventure.text.Component;


import java.util.ArrayList;
import java.util.List;

public class Song {
    private final String id;
    private final Component songName;
    private final List<SongNote> notes;
    private final float volume;
    private final String fileName;

    public Song(
            final String id,
            final Component songName,
            final List<SongNote> notes,
            float volume,
            final String fileName
    ) {
        this.id = id;
        this.songName = songName;
        this.notes = notes;
        this.volume = volume;
        this.fileName = fileName;
    }

    public String getId() {
        return id;
    }

    public List<SongNote> getNotes() {
        return new ArrayList<>(notes);
    }

    public Component getSongName() {
        return songName;
    }

    public float getVolume() {
        return volume;
    }

    public String getFileName() {
        return fileName;
    }
}
