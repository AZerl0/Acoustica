package com.azerl0.acoustica.itemframe;

import com.azerl0.acoustica.song.Song;
import com.azerl0.acoustica.song.SongManager;
import com.azerl0.acoustica.songitem.SongItem;
import com.azerl0.acoustica.speaker.EntityLocationSpeakerManager;
import com.azerl0.acoustica.speaker.SpeakerImpl;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.ItemFrame;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;


public class ItemFrameListener implements Listener {
    EntityLocationSpeakerManager entityLocationSpeakerManager;
    SongManager songManager;

    public ItemFrameListener(EntityLocationSpeakerManager entityLocationSpeakerManager, SongManager songManager) {
        this.entityLocationSpeakerManager = entityLocationSpeakerManager;
        this.songManager = songManager;
    }

    @EventHandler
    public void onInteract(PlayerInteractEntityEvent event) {
        if (event.isCancelled()) return;
        if (event.getRightClicked() instanceof ItemFrame itemFrame) {
            ItemStack itemFrameItem = itemFrame.getItem();
            if (!itemFrameItem.isEmpty()) return;
            ItemStack handItem = event.getPlayer().getInventory().getItem(event.getHand());
            if (handItem.isEmpty()) return;

            if (itemFrame.getAttachedFace() != BlockFace.DOWN) return;
            Block attachedBlock = itemFrame.getLocation().getBlock()
                    .getRelative(BlockFace.DOWN);
            if (attachedBlock.getType() != Material.JUKEBOX) return;
            if (!entityLocationSpeakerManager.hasSpeaker(itemFrame)) {
                String songId = SongItem.getSongId(handItem);
                if (songId == null) return;
                Song song = songManager.getSong(songId);
                if (song == null) return;

                SpeakerImpl speakerImpl = entityLocationSpeakerManager.createSpeaker(itemFrame);
                speakerImpl.setSong(song);
                speakerImpl.start();
            }
        }
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        if (event.isCancelled()) return;
        if (event.getEntity() instanceof ItemFrame itemFrame) {
            if (!itemFrame.getItem().isEmpty()) {
                entityLocationSpeakerManager.removeSpeaker(itemFrame);
            }
        }
    }
}
