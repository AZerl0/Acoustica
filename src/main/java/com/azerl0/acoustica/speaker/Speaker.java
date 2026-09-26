package com.azerl0.acoustica.speaker;

import com.azerl0.acoustica.Acoustica;
import com.azerl0.acoustica.nbs.*;
import com.azerl0.acoustica.util.NbsUtil;
import io.papermc.paper.threadedregions.scheduler.ScheduledTask;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public class Speaker {
    private final SpeakerManager manager;
    private Location location;
    private int tick;
    private NbsSong song;
    private List<NbsNote> currentNotes = List.of();
    private boolean stop;
    private ScheduledTask task;

    public Speaker(Location location, SpeakerManager manager) {
        this.manager = manager;
        this.location = location;
        stop = true;
        resetCurrentNotes();

        try {
            song = NbsReader.read(Path.of(""));
            resetCurrentNotes();
        } catch (IOException _) {
        }

        task = Bukkit.getServer().getRegionScheduler().runAtFixedRate(Acoustica.getInstance(), location, task -> {
            onTick();
        }, 1L, 1L);
    }

    protected void onTick() {
        if (stop) return;
        while (!currentNotes.isEmpty()) {
            NbsNote note = currentNotes.getFirst();
            if (note == null) break;
            float speed = (float) (20 / song.getMeta().getTempo());
            int songTick = (int) (note.getTick() * speed);
            if (songTick > tick) break;
            currentNotes.removeFirst();

            Sound sound = NoteInstrument.getSoundFromNumber(note.getInstrument());
            if (sound == null) continue;

            NbsLayer layer = new NbsLayer("_", false, 100, 100);
            if (song.getLayers().size() > note.getLayer()) layer = song.getLayers().get(note.getLayer());
            float volume = (float) layer.getVolume() / 10000 * note.getVelocity();
            if (volume <= 0.0f) continue;
            int key = note.getKey() + note.getPitch() / 100;
            float pitch = NbsUtil.getPitch(key);
            location.getWorld().playSound(location, sound, volume, pitch);
        }
        tick++;
    }

    public @NotNull Location getLocation() {
        return location;
    }

    public void start() {
        stop = false;
    }

    public void pause() {
        stop = true;
    }

    public void stop() {
        tick = 0;
        stop = true;
        resetCurrentNotes();
    }

    public void remove() {
        task.cancel();
        manager.removeSpeaker(this);
    }

    private void resetCurrentNotes() {
        currentNotes = song.getNotes();
    }
}
