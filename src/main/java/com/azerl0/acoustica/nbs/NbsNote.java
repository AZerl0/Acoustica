package com.azerl0.acoustica.nbs;

public class NbsNote {
    private final int tick;
    private final int layer;
    private final int instrument;
    private final int key;
    private final int velocity;
    private final int panning;
    private final short pitch;

    public NbsNote(
            final int tick,
            final int layer,
            final int instrument,
            final int key,
            final int velocity,
            final int panning,
            final short pitch
    ) {
        this.tick = tick;
        this.layer = layer;
        this.instrument = instrument;
        this.key = key;
        this.velocity = velocity;
        this.panning = panning;
        this.pitch = pitch;
    }

    public int getTick() {
        return tick;
    }

    public int getLayer() {
        return layer;
    }

    public int getInstrument() {
        return instrument;
    }

    public int getKey() {
        return key;
    }

    public int getVelocity() {
        return velocity;
    }

    public int getPanning() {
        return panning;
    }

    public short getPitch() {
        return pitch;
    }
}
