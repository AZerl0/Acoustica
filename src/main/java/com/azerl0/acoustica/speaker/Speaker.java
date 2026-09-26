package com.azerl0.acoustica.speaker;

import com.azerl0.acoustica.Acoustica;
import com.azerl0.acoustica.nbs.*;
import com.azerl0.acoustica.song.Song;
import com.azerl0.acoustica.song.SongNote;
import io.papermc.paper.threadedregions.scheduler.ScheduledTask;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;

public class Speaker {
    private final UUID uuid;
    private final SpeakerManager manager;
    private final Location location;
    private int tick;
    private @Nullable Song song;
    private List<SongNote> currentNotes = List.of();
    private boolean stop;
    private final ScheduledTask task;

    public Speaker(UUID uuid, Location location, SpeakerManager manager) {
        this.uuid = uuid;
        this.manager = manager;
        this.location = location;
        stop = true;
        tick = 0;

        resetCurrentNotes();
        task = Bukkit.getServer().getRegionScheduler().runAtFixedRate(Acoustica.getInstance(), location, task -> {
            onTick();
        }, 1L, 1L);
    }

    protected void onTick() {
        if (stop) return;
        while (!currentNotes.isEmpty()) {
            SongNote note = currentNotes.getFirst();
            if (note == null) break;
            if (note.getTick() > tick) break;
            currentNotes.removeFirst();
            for (Player player : location.getNearbyPlayers(64)) {
                double distance = player.getLocation().distance(location);
                float playerVolume = 1.0f -  (float) (distance / 64);
                player.playSound(player, note.getSound(), SoundCategory.RECORDS, note.getVolume() * playerVolume * 0.9f, note.getPitch());
            }

        }
        if (tick % 20 == 0 && !currentNotes.isEmpty()) {
            Location particleLoc = new Location(location.getWorld(), location.getBlockX(), location.getBlockY(), location.getBlockZ()).add(0.5, 0.0, 0.5);
            Particle.NOTE.builder().location(particleLoc).count(1).spawn();

        }
        tick++;
    }

    public @NotNull UUID getUniqueId() {
        return uuid;
    }

    public @NotNull Location getLocation() {
        return location;
    }

    public void setSong(Song song) {
        this.song = song;
        resetCurrentNotes();
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
        stop();
        task.cancel();
        manager.removeSpeaker(this);
    }

    private void resetCurrentNotes() {
        if (song != null) currentNotes = song.getNotes();
    }
}
