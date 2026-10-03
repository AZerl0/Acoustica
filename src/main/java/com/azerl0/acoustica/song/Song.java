package com.azerl0.acoustica.song;

import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;


import java.util.ArrayList;
import java.util.List;

public class Song {
    private final @NotNull String id;
    private final @NotNull Component songName;
    private final @NotNull List<SongNote> notes;
    private final float volume;
    private final @NotNull String fileName;
    private final @NotNull List<String> advancementKeys;

    public Song(
            final @NotNull String id,
            final @NotNull Component songName,
            final @NotNull List<SongNote> notes,
            float volume,
            final @NotNull String fileName,
            final @NotNull List<String> advancements
    ) {
        this.id = id;
        this.songName = songName;
        this.notes = notes;
        this.volume = volume;
        this.fileName = fileName;
        this.advancementKeys = advancements;
    }

    public @NotNull String getId() {
        return id;
    }

    public List<SongNote> getNotes() {
        return new ArrayList<>(notes);
    }

    public @NotNull Component getSongName() {
        return songName;
    }

    public float getVolume() {
        return volume;
    }

    public @NotNull String getFileName() {
        return fileName;
    }

    public @NotNull List<String> getAdvancementKeys() {
        return advancementKeys;
    }
}
