package com.azerl0.acoustica.nbs;

public class NbsCustomInstrument {
    private final int i;
    private final String instrumentName;
    private final String soundFile;
    private final int key;
    private final boolean press;

    public NbsCustomInstrument(
            final int i,
            final String instrumentName,
            final String soundFile,
            final int key,
            final boolean press
    ) {
        this.i = i;
        this.instrumentName = instrumentName;
        this.soundFile = soundFile;
        this.key = key;
        this.press = press;
    }

    public int getI() {
        return i;
    }

    public String getInstrumentName() {
        return instrumentName;
    }

    public String getSoundFile() {
        return soundFile;
    }

    public int getKey() {
        return key;
    }

    public boolean isPress() {
        return press;
    }
}
