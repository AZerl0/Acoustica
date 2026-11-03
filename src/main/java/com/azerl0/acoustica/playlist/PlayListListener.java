package com.azerl0.acoustica.playlist;

import com.azerl0.acoustica.event.speaker.SpeakerSetSongEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayListListener implements Listener {

    private final PlayListManager playListManager;

    public PlayListListener(PlayListManager playListManager) {
        this.playListManager = playListManager;
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (event.getClickedBlock() != null) {
            PlayList playList = playListManager.getPlayList(event.getClickedBlock().getLocation());
            if (playList != null) {
                playList.getGui().open(event.getPlayer());
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
    }
}
