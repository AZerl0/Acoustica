package com.azerl0.acoustica.speaker;

import com.azerl0.acoustica.Acoustica;
import com.azerl0.acoustica.api.speaker.Speaker;
import com.azerl0.acoustica.event.speaker.SpeakerSetSongEvent;
import com.azerl0.acoustica.song.Song;
import com.azerl0.acoustica.song.SongNote;
import com.azerl0.acoustica.util.AdvancementUtil;
import io.papermc.paper.threadedregions.scheduler.ScheduledTask;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;

public class SpeakerImpl implements Speaker {
    private final UUID uuid;
    private final SpeakerManager manager;
    private final Location location;
    private int tick;
    private @Nullable Song song;
    private List<SongNote> currentNotes = List.of();
    private float currentSongVolume;
    private boolean pause;
    private final ScheduledTask task;

    public SpeakerImpl(UUID uuid, Location location, SpeakerManager manager) {
        this.uuid = uuid;
        this.manager = manager;
        this.location = location;
        pause = true;
        tick = 0;

        resetCurrentNotes();
        task = Bukkit.getServer().getRegionScheduler().runAtFixedRate(Acoustica.getInstance(), location, _ -> onTick(), 1L, 1L);
    }

    private void onTick() {
        if (pause) return;
        while (!currentNotes.isEmpty()) {
            SongNote note = currentNotes.getFirst();
            if (note == null) break;
            if (note.getTick() > tick) break;
            currentNotes.removeFirst();
            for (Player player : location.getNearbyPlayers(64)) {
                double distance = player.getLocation().distance(location);
                float playerVolume = 1.0f - (float) (distance / 64);
                player.playSound(player, note.getSound(), SoundCategory.RECORDS, note.getVolume() * playerVolume * currentSongVolume, note.getPitch());
            }

        }
        if (tick % 20 == 0 && !currentNotes.isEmpty()) {
            Location particleLoc = new Location(location.getWorld(), location.getBlockX(), location.getBlockY(), location.getBlockZ()).add(0.5, 0.0, 0.5);
            Particle.NOTE.builder().location(particleLoc).count(1).spawn();

            if (song != null) {
                for (Player player : location.getNearbyPlayers(5)) {
                    AdvancementUtil.grantAdvancement(player, "acoustica:root");
                    for (String advPath : song.getAdvancementKeys()) {
                        AdvancementUtil.grantAdvancement(player, advPath);
                    }
                }
            }
        }
        tick++;
    }

    @Override
    public @NotNull UUID getUniqueId() {
        return uuid;
    }

    @Override
    public @NotNull Location getLocation() {
        return location;
    }

    @Override
    public @Nullable Song getSong() {
        return song;
    }

    @Override
    public void setSong(@Nullable Song song) {
        this.song = song;
        if (song != null) currentSongVolume = song.getVolume();
        resetCurrentNotes();
        SpeakerSetSongEvent setSongEvent = new SpeakerSetSongEvent(this, song);
        Bukkit.getPluginManager().callEvent(setSongEvent);
    }

    @Override
    public void start() {
        pause = false;
    }

    @Override
    public void stop() {
        tick = 0;
        pause = true;
        resetCurrentNotes();
    }

    @Override
    public void pause() {
        pause = true;
    }

    @Override
    public boolean isPause() {
        return pause;
    }

    @Override
    public int getTick() {
        return tick;
    }

    public void remove() {
        stop();
        task.cancel();
        manager.removeSpeaker(this);
    }

    @Override
    public void setTick(int tick) {
        if (song == null) return;
        if (tick == this.tick) return;

        boolean isStep = tick > this.tick;
        this.tick = tick;


        if (isStep) {
            while (true) {
                if (currentNotes.isEmpty()) return;

                SongNote note = currentNotes.getFirst();

                if (note.getTick() > tick) {
                    return;
                } else {
                    currentNotes.removeFirst();
                }
            }
        } else {
            int i = song.getNotes().size() - currentNotes.size();
            while (true) {
                if (i < 0 || i > song.getNotes().size()) return;

                SongNote note = song.getNotes().get(i);
                if (note.getTick() < tick) return;

                currentNotes.addFirst(note);
                i--;
            }
        }

    }

    private void resetCurrentNotes() {
        if (song != null) currentNotes = song.getNotes();
    }
}
