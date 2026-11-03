package com.azerl0.acoustica.api.speaker;

import com.azerl0.acoustica.song.Song;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public interface Speaker {
    int SONG_FINISH_OFFSET_TICK = 2 * 20;


    @NotNull UUID getUniqueId();

    @NotNull Location getLocation();

    @Nullable Song getSong();

    boolean isPause();

    void start();

    void stop();

    void pause();

    void setSong(@Nullable Song song);

    int getTick();

    void setTick(int tick);

    float getVolume();

    void setVolume(float volume);

    boolean songIsFinished();

    boolean isDisableAdvancement();

    void setDisableAdvancement(boolean disable);

    void remove();
}
