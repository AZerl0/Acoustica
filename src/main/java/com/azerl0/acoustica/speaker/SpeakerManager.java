package com.azerl0.acoustica.speaker;

import org.bukkit.Location;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class SpeakerManager {
    private final Map<UUID, Speaker> speakers = new HashMap<>();

    public Speaker createSpeaker(UUID uuid, Location location) {
        Speaker speaker = new Speaker(uuid, location, this);
        speakers.put(uuid, speaker);
        return speaker;
    }

    public Speaker createSpeaker(Location location) {
        return createSpeaker(UUID.randomUUID(), location);
    }

    public @Nullable Speaker getSpeaker(UUID uuid) {
        return speakers.getOrDefault(uuid, null);
    }

    public Collection<Speaker> getSpeakers() {
        return speakers.values();
    }

    public Set<Speaker> getNearbySpeakers(Location location, double r) {
        Set<Speaker> nearSpeakers = new HashSet<>();
        for (Speaker speaker : getSpeakers()) {
            if (speaker.getLocation().getWorld().equals(location.getWorld())) {
                double distance = location.distance(speaker.getLocation());
                if (distance <= r) nearSpeakers.add(speaker);
            }
        }
        return nearSpeakers;
    }

    protected void removeSpeaker(Speaker speaker) {
        speakers.remove(speaker.getUniqueId());
    }
}
