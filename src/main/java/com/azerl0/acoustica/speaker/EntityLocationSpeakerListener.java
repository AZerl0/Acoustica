package com.azerl0.acoustica.speaker;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityRemoveEvent;

public class EntityLocationSpeakerListener implements Listener {
    private final EntityLocationSpeakerManager entityLocationSpeakerManager;

    public EntityLocationSpeakerListener(EntityLocationSpeakerManager entityLocationSpeakerManager) {
        this.entityLocationSpeakerManager = entityLocationSpeakerManager;
    }

    @EventHandler
    public void onEntityRemove(EntityRemoveEvent event) {
        entityLocationSpeakerManager.removeSpeaker(event.getEntity());
    }
}
