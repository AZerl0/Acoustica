package com.azerl0.acoustica.songitem;

import com.azerl0.acoustica.Acoustica;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.Nullable;

public class SongItem {
    private static final NamespacedKey songIdKey = new NamespacedKey(Acoustica.getInstance(), "song_id");

    public static @Nullable String getSongId(ItemStack itemStack) {
        return itemStack.getPersistentDataContainer().get(songIdKey, PersistentDataType.STRING);
    }

    public static void setSongId(ItemStack itemStack, String songId) {
        itemStack.editPersistentDataContainer(pdc -> pdc.set(songIdKey, PersistentDataType.STRING, songId));
    }
}
