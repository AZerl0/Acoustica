package com.azerl0.acoustica.song;

import com.azerl0.acoustica.nbs.NbsLayer;
import com.azerl0.acoustica.nbs.NbsNote;
import com.azerl0.acoustica.nbs.NbsSong;
import com.azerl0.acoustica.nbs.NoteInstrument;
import com.azerl0.acoustica.util.NbsUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.Sound;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class Song {
    private Component songName;
    private final List<SongNote> notes;

    public Song(Component songName, List<SongNote> notes) {
        this.songName = songName;
        this.notes = notes;
    }

    public List<SongNote> getNotes() {
        return new ArrayList<>(notes);
    }

    public static @NotNull Song fromNbsSong(NbsSong nbsSong) {
        List<SongNote> noteList = new ArrayList<>();
        float speed = (float) (20 / nbsSong.getMeta().getTempo());

        for (NbsNote nbsNote : nbsSong.getNotes()) {
            int songTick = (int) (nbsNote.getTick() * speed);

            Sound sound = NoteInstrument.getSoundFromNumber(nbsNote.getInstrument(), nbsSong.getCustomInstruments());
            if (sound == null) continue;

            NbsLayer layer = new NbsLayer("_", false, 100, 100);
            if (nbsSong.getLayers().size() > nbsNote.getLayer()) layer = nbsSong.getLayers().get(nbsNote.getLayer());
            float volume = (float) layer.getVolume() / 10000 * nbsNote.getVelocity();
            if (volume <= 0.0f) continue;
            int key = nbsNote.getKey() + nbsNote.getPitch() / 100;
            float pitch = NbsUtil.getPitch(key);
            noteList.add(new SongNote(songTick, sound, volume, pitch));
        }
        return new Song(Component.text(nbsSong.getMeta().getName()), noteList);
    }

    public Component getSongName() {
        return songName;
    }
}
