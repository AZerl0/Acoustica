package com.azerl0.acoustica.event.speaker;

import com.azerl0.acoustica.api.speaker.Speaker;
import com.azerl0.acoustica.song.Song;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class SpeakerSetSongEvent extends Event {
    private static final HandlerList handlers = new HandlerList();

    private Speaker speaker;
    private Song song;

    public SpeakerSetSongEvent(Speaker speaker, Song song) {
        this.speaker = speaker;
        this.song = song;
    }

    public Speaker getSpeaker() {
        return speaker;
    }

    public Song getSong() {
        return song;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
