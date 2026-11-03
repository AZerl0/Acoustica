package com.azerl0.acoustica.playlist;

import com.azerl0.acoustica.Acoustica;
import com.azerl0.acoustica.api.speaker.Speaker;
import com.azerl0.acoustica.gui.guis.PlayListGUI;
import com.azerl0.acoustica.song.Song;
import com.azerl0.acoustica.songitem.SongItem;
import io.papermc.paper.threadedregions.scheduler.ScheduledTask;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class PlayList {
    private final PlayListManager playListManager;
    private final Block jukebox;
    private final PlayListGUI gui;
    private final Speaker speaker;
    private final ScheduledTask task;
    private @Nullable ItemStack playingSongItem;

    public PlayList(PlayListManager playListManager, Block jukebox) {
        this.playListManager = playListManager;
        this.jukebox = jukebox;
        gui = new PlayListGUI(this);
        speaker = Acoustica.getInstance().getSpeakerManager().createSpeaker(jukebox.getLocation());
        task = Bukkit.getServer().getRegionScheduler().runAtFixedRate(Acoustica.getInstance(), jukebox.getLocation(), _ -> onTick(), 1L, 1L);
    }

    public Block getJukeboxBlock() {
        return jukebox;
    }

    public void play() {
        if (speaker.getSong() != null) {
            Chest chest = getSingleChest();
            if (chest == null) return;
            Inventory inventory = chest.getInventory();
            ItemStack topItem = inventory.getItem(0);
            if (topItem != null && !topItem.isEmpty()) {
                speaker.start();

            } else {
                skip(0);
            }
        } else {
            skip(0);
        }
    }

    public void skip() {
        skip(1);
    }

    public void skip(int skipOffset) {
        String songId = setNextSong(skipOffset);
        setSong(songId);
    }

    public void previous() {
        previous(1);
    }

    public void previous(int prevOffset) {
        String songId = setPreviousSong(prevOffset);
        setSong(songId);
    }

    private void setSong(String songId) {
        speaker.stop();
        if (songId != null) {
            Song song = Acoustica.getInstance().getSongManager().getSong(songId);
            if (song != null) {
                speaker.setSong(song);
                speaker.start();
            }
        } else {
            speaker.setSong(null);
        }
        gui.updatePauseButton();
    }

    private void onTick() {

        Song song = speaker.getSong();
        if (song != null) {
            gui.updatePlayingSongDisplay();
            if (!speaker.isPause()) {
                Chest chest = getSingleChest();
                if (chest == null) return;
                Inventory inventory = chest.getInventory();
                ItemStack topItem = inventory.getItem(0);
                if (topItem == null || topItem.isEmpty()) {
                    skip(0);
                }

                if (speaker.songIsFinished()) {
                    skip(1);
                }
            }
        }
    }

    private List<Optional<String>> getSongIdList() {
        List<Optional<String>> list = new ArrayList<>();
        BlockState state = jukebox.getLocation().add(0.0, 1.0, 0.0).getBlock().getState();
        if (state instanceof Chest chest) {
            Inventory inventory = chest.getInventory();

            if (inventory.getSize() != 27) {
                return list;
            }

            for (ItemStack item : inventory.getContents()) {
                if (item == null) {
                    list.add(Optional.empty());
                    continue;
                }

                @Nullable String id = SongItem.getSongId(item);
                list.add(Optional.ofNullable(id));
            }
        }

        return list;
    }

    private @Nullable String setNextSong(int skipOffset) {
        Chest chest = getSingleChest();
        if (chest == null) return null;

        Inventory inventory = chest.getInventory();
        for (int i = skipOffset; i < inventory.getSize(); i++) {
            ItemStack item = inventory.getItem(i);
            if (item == null) {
                continue;
            }

            @Nullable String songId = SongItem.getSongId(item);
            if (songId != null) {
                playingSongItem = item.clone();
                shiftInventory(inventory, -i);
                return songId;
            }
        }

        return null;
    }

    private @Nullable String setPreviousSong(int prevOffset) {
        Chest chest = getSingleChest();
        if (chest == null) return null;

        Inventory inventory = chest.getInventory();
        ;
        for (int i = inventory.getSize() - prevOffset; i >= 0; i--) {
            ItemStack item = inventory.getItem(i);
            if (item == null) {
                continue;
            }

            @Nullable String songId = SongItem.getSongId(item);
            if (songId != null) {
                playingSongItem = item.clone();
                shiftInventory(inventory, -i);
                return songId;
            }
        }

        return null;
    }

    private boolean hasSongItem() {
        BlockState state = jukebox.getLocation().add(0.0, 1.0, 0.0).getBlock().getState();
        if (state instanceof Chest chest) {
            Inventory inventory = chest.getInventory();

            if (inventory.getSize() != 27) {
                return false;
            }

            for (int i = 0; i < inventory.getSize(); i++) {
                ItemStack item = inventory.getItem(i);
                if (item == null) {
                    continue;
                }

                if (SongItem.getSongId(item) != null) return true;

            }
        }
        return false;
    }

    private void shuffle() {
        List<Integer> slots = new ArrayList<>();
        List<ItemStack> items = new ArrayList<>();

        BlockState state = jukebox.getLocation().add(0.0, 1.0, 0.0).getBlock().getState();
        if (state instanceof Chest chest) {
            Inventory inventory = chest.getInventory();

            if (inventory.getSize() != 27) {
                return;
            }

            for (int i = 0; i < inventory.getSize(); i++) {
                ItemStack item = inventory.getItem(i);
                if (item == null) {
                    continue;
                }

                slots.add(i);
                items.add(item);
            }

            Collections.shuffle(items);
            for (int slot : slots) {
                inventory.setItem(slot, items.getFirst());

                items.removeFirst();
            }
        }
    }

    public Speaker getSpeaker() {
        return speaker;
    }

    public PlayListGUI getGui() {
        return gui;
    }

    private void shiftInventory(Inventory inventory, int shift) {
        ItemStack[] contents = inventory.getContents();
        ItemStack[] shifted = new ItemStack[contents.length];

        int size = contents.length;
        shift = Math.floorMod(shift, size);

        for (int i = 0; i < size; i++) {
            shifted[(i + shift) % size] = contents[i];
        }

        inventory.setContents(shifted);
    }

    public @Nullable Chest getSingleChest() {
        BlockState state = jukebox.getLocation().add(0.0, 1.0, 0.0).getBlock().getState();
        if (state instanceof Chest chest) {
            Inventory inventory = chest.getInventory();

            if (inventory.getSize() != 27) {
                return null;
            }

            return chest;

        }
        return null;
    }

    public @Nullable ItemStack getPlayingSongItem() {
        return playingSongItem;
    }

    public void remove() {
        speaker.remove();
        task.cancel();
    }
}
