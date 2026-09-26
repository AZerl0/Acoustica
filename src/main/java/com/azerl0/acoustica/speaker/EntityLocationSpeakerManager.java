package com.azerl0.acoustica.speaker;

import org.bukkit.entity.Entity;
import org.jetbrains.annotations.Nullable;

public class EntityLocationSpeakerManager {
    private final SpeakerManager speakerManager;

    public EntityLocationSpeakerManager(SpeakerManager speakerManager) {
        this.speakerManager = speakerManager;
    }

    public boolean hasSpeaker(Entity entity) {
        return getSpeaker(entity) != null;
    }

    public @Nullable Speaker getSpeaker(Entity entity) {
        return speakerManager.getSpeaker(entity.getUniqueId());
    }

    public Speaker createSpeaker(Entity entity) {
        return speakerManager.createSpeaker(entity.getUniqueId(), entity.getLocation());
    }

    public void removeSpeaker(Entity entity) {
        Speaker speaker = getSpeaker(entity);
        if (speaker != null) speaker.remove();
    }
}
