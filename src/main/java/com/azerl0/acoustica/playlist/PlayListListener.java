package com.azerl0.acoustica.playlist;

import com.azerl0.acoustica.event.speaker.SpeakerSetSongEvent;
import org.bukkit.Sound;
import org.bukkit.block.Chest;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

public class PlayListListener implements Listener {

    private final PlayListManager playListManager;

    public PlayListListener(PlayListManager playListManager) {
        this.playListManager = playListManager;
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (event.useInteractedBlock() == Event.Result.DENY) return;
        if (event.useItemInHand() == Event.Result.DENY) return;
        if (event.getClickedBlock() != null && event.getAction().isRightClick()) {
            PlayList playList = playListManager.getPlayList(event.getClickedBlock().getLocation());
            if (playList != null) {
                if (event.getHand() == EquipmentSlot.OFF_HAND) {
                    event.setCancelled(true);
                    return;
                }
                playList.getJukeboxBlock().getLocation().getWorld().playSound(playList.getSpeaker().getLocation(), Sound.BLOCK_COPPER_CHEST_OPEN, 0.5f, 2.0f);
                playList.getGui().open(event.getPlayer());
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onSpeakerSetSong(SpeakerSetSongEvent event) {
        for (PlayList playList : playListManager.getAllPlayList()) {
            if (playList.getSpeaker() == event.getSpeaker()) {
                playList.getGui().updateGUI();
            }
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        PlayList playList = playListManager.getPlayList(event.getBlock().getLocation());
        if (playList != null) playList.remove();
        if (event.getBlock().getState() instanceof Chest) {
            playList = playListManager.getPlayList(event.getBlock().getLocation().clone().add(0.0, -1.0, 0.0));
            if (playList != null) playList.remove();
        }
    }
}
