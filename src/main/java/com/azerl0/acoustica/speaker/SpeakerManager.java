package com.azerl0.acoustica.speaker;

import org.bukkit.Location;

import java.util.HashSet;
import java.util.Set;

public class SpeakerManager {
    private final Set<Speaker> speakers = new HashSet<>();

    public Speaker createSpeaker(Location location) {
        Speaker speaker = new Speaker(location, this);
        speakers.add(speaker);
        return speaker;
    }

    public Set<Speaker> getSpeakers() {
        return speakers;
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
        speakers.remove(speaker);
    }
}
