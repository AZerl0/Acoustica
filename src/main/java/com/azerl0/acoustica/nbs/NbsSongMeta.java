package com.azerl0.acoustica.nbs;

public class NbsSongMeta {
    private final int version;
    private final int vanillaInstrumentCount;
    private final int songLength;
    private final int layerCount;

    private final String name;
    private final String author;
    private final String originalAuthor;
    private final String description;

    private final double tempo;
    private final boolean autoSaving;
    private final int autoSavingDuration;
    private final int timeSignature;
    private final int minutesSpent;
    private final int leftClicks;
    private final int rightClicks;
    private final int noteBlocksAdded;
    private final int noteBlocksRemoved;
    private final String midiFileName;
    private final boolean loop;
    private final int maxLoopCount;
    private final int loopStartTick;

    public NbsSongMeta(
            int version,
            int vanillaInstrumentCount,
            int songLength,
            int layerCount,
            String name,
            String author,
            String originalAuthor,
            String description,
            double tempo,
            final boolean autoSaving,
            final int autoSavingDuration,
            final int timeSignature,
            final int minutesSpent,
            final int leftClicks,
            final int rightClicks,
            final int noteBlocksAdded,
            final int noteBlocksRemoved,
            final String midiFileName,
            final boolean loop,
            final int maxLoopCount,
            final int loopStartTick
            ) {
        this.version = version;
        this.vanillaInstrumentCount = vanillaInstrumentCount;
        this.songLength = songLength;
        this.layerCount = layerCount;
        this.name = name;
        this.author = author;
        this.originalAuthor = originalAuthor;
        this.description = description;
        this.tempo = tempo;
        this.autoSaving = autoSaving;
        this.autoSavingDuration = autoSavingDuration;
        this.timeSignature = timeSignature;
        this.minutesSpent = minutesSpent;
        this.leftClicks = leftClicks;
        this.rightClicks = rightClicks;
        this.noteBlocksAdded = noteBlocksAdded;
        this.noteBlocksRemoved = noteBlocksRemoved;
        this.midiFileName = midiFileName;
        this.loop = loop;
        this.maxLoopCount = maxLoopCount;
        this.loopStartTick = loopStartTick;
    }

    public int getVersion() {
        return version;
    }

    public int getVanillaInstrumentCount() {
        return vanillaInstrumentCount;
    }

    public int getSongLength() {
        return songLength;
    }

    public int getLayerCount() {
        return layerCount;
    }

    public String getName() {
        return name;
    }

    public String getAuthor() {
        return author;
    }

    public String getOriginalAuthor() {
        return originalAuthor;
    }

    public String getDescription() {
        return description;
    }

    public double getTempo() {
        return tempo;
    }

    public boolean isAutoSaving() {
        return autoSaving;
    }

    public int getAutoSavingDuration() {
        return autoSavingDuration;
    }

    public int getTimeSignature() {
        return timeSignature;
    }

    public int getMinutesSpent() {
        return minutesSpent;
    }

    public int getLeftClicks() {
        return leftClicks;
    }

    public int getRightClicks() {
        return rightClicks;
    }

    public int getNoteBlocksAdded() {
        return noteBlocksAdded;
    }


    public int getNoteBlocksRemoved() {
        return noteBlocksRemoved;
    }

    public String getMidiFileName() {
        return midiFileName;
    }

    public boolean isLoop() {
        return loop;
    }

    public int getMaxLoopCount() {
        return maxLoopCount;
    }

    public int getLoopStartTick() {
        return loopStartTick;
    }
}
