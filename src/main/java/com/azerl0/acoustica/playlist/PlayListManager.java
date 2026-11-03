package com.azerl0.acoustica.playlist;

import org.bukkit.Location;
import org.bukkit.block.Chest;
import org.bukkit.block.Jukebox;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class PlayListManager {

    Map<Location, PlayList> playListMap = new HashMap<>();


    public @Nullable PlayList getPlayList(@NotNull Location location) {
        Location loc = getKeyLocation(location);
        if (playListMap.containsKey(loc)) {
            return playListMap.get(loc);
        }
        if (location.getBlock().getState() instanceof Jukebox && location.clone().add(0.0, 1.0, 0.0).getBlock().getState() instanceof Chest chest) {
            if (chest.getInventory().getSize() == 27) {

                PlayList playList = new PlayList(this, location.getBlock());
                playListMap.put(loc, playList);
                return playList;
            }
        }
        return null;
    }

    public Collection<PlayList> getAllPlayList() {
        return playListMap.values();
    }

    public static Location getKeyLocation(Location location) {
        Location loc = location.clone();
        loc.setX(loc.getBlockX());
        loc.setY(loc.getBlockY());
        loc.setZ(loc.getBlockZ());
        return loc;
    }

    protected void onRemovePlayList(PlayList playList) {
        Location loc = getKeyLocation(playList.getJukeboxBlock().getLocation());
        playListMap.remove(loc);
    }
}
