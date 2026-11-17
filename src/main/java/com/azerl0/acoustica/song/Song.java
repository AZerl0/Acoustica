package com.azerl0.acoustica.song;

import com.azerl0.acoustica.Acoustica;
import com.azerl0.acoustica.nbs.NbsReader;
import com.azerl0.acoustica.nbs.NbsSong;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;


import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Song {
    private final @NotNull String id;
    private final @NotNull Component songName;
    private @NotNull List<SongNote> notes;
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

    public int getLength() {
        if (notes.isEmpty()) {
            return 0;
        } else {
            return notes.getLast().getTick();
        }
    }

    public void updateNotes(Acoustica acoustica) {
        notes = List.of();
        Path path = acoustica.getDataPath().resolve("songs").resolve(fileName + ".nbs");
        NbsSong nbsSong;
        try {
            nbsSong = NbsReader.read(path);
        } catch (IOException e) {
            acoustica.getLogger().warning("failed load nbs file. id: " + id + ", message: " + e.getMessage());
            return;
        }
        notes = SongNote.getNotesFromNbsSong(nbsSong);

    }
}
