package com.azerl0.acoustica.nbs;

import java.util.List;

public class NbsSong {
    private final NbsSongMeta meta;
    private final List<NbsNote> notes;
    private final List<NbsLayer> layers;
    private final List<NbsCustomInstrument> customInstruments;

    public NbsSong(
            final NbsSongMeta meta,
            final List<NbsNote> notes,
            final List<NbsLayer> layers,
            final List<NbsCustomInstrument> customInstruments

    ) {
        this.meta = meta;
        this.notes = notes;
        this.layers = layers;
        this.customInstruments = customInstruments;
    }

    public NbsSongMeta getMeta() {
        return meta;
    }

    public List<NbsNote> getNotes() {
        return notes;
    }

    public List<NbsLayer> getLayers() {
        return layers;
    }

    public List<NbsCustomInstrument> getCustomInstruments() {
        return customInstruments;
    }
}