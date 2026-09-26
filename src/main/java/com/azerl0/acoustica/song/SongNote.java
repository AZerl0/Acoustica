package com.azerl0.acoustica.song;

import org.bukkit.Sound;

public class SongNote {
    private final int tick;
    private final Sound sound;
    private final float volume;
    private final float pitch;

    public SongNote(
            final int tick,
            final Sound sound,
            final float volume,
            final float pitch
    ) {
        this.tick = tick;
        this.sound = sound;
        this.volume = volume;
        this.pitch = pitch;
    }

    public int getTick() {
        return tick;
    }

    public Sound getSound() {
        return sound;
    }

    public float getVolume() {
        return volume;
    }

    public float getPitch() {
        return pitch;
    }
}
