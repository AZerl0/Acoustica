package com.azerl0.acoustica.nbs;

public class NbsLayer {
    private final String name;
    private final boolean locked;
    private final int volume;
    private final int stereo;

    public NbsLayer(
            final String name,
            final boolean locked,
            final int volume,
            final int stereo
    ) {
        this.name = name;
        this.locked = locked;
        this.volume = volume;
        this.stereo = stereo;
    }

    public String getName() {
        return name;
    }

    public boolean isLocked() {
        return locked;
    }

    public int getVolume() {
        return volume;
    }

    public int getStereo() {
        return stereo;
    }
}
