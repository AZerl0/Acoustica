package com.azerl0.acoustica.speaker;

import org.bukkit.Location;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class SpeakerManager {
    private final Map<UUID, SpeakerImpl> speakers = new HashMap<>();

    public SpeakerImpl createSpeaker(UUID uuid, Location location) {
        SpeakerImpl speakerImpl = new SpeakerImpl(uuid, location, this);
        speakers.put(uuid, speakerImpl);
        return speakerImpl;
    }

    public SpeakerImpl createSpeaker(Location location) {
        return createSpeaker(UUID.randomUUID(), location);
    }

    public @Nullable SpeakerImpl getSpeaker(UUID uuid) {
        return speakers.getOrDefault(uuid, null);
    }

    public Collection<SpeakerImpl> getSpeakers() {
        return speakers.values();
    }

    public Set<SpeakerImpl> getNearbySpeakers(Location location, double r) {
        Set<SpeakerImpl> nearSpeakerImpls = new HashSet<>();
        for (SpeakerImpl speakerImpl : getSpeakers()) {
            if (speakerImpl.getLocation().getWorld().equals(location.getWorld())) {
                double distance = location.distance(speakerImpl.getLocation());
                if (distance <= r) nearSpeakerImpls.add(speakerImpl);
            }
        }
        return nearSpeakerImpls;
    }

    protected void removeSpeaker(SpeakerImpl speakerImpl) {
        speakers.remove(speakerImpl.getUniqueId());
    }
}
