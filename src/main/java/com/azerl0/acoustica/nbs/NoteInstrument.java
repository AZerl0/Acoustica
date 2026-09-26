package com.azerl0.acoustica.nbs;

import org.bukkit.Sound;
import org.jetbrains.annotations.Nullable;

public class NoteInstrument {

    public static @Nullable Sound getSoundFromNumber(int num) {
        return switch (num) {
            case 0 -> Sound.BLOCK_NOTE_BLOCK_HARP;
            case 1 -> Sound.BLOCK_NOTE_BLOCK_BASS;
            case 2 -> Sound.BLOCK_NOTE_BLOCK_BASEDRUM;
            case 3 -> Sound.BLOCK_NOTE_BLOCK_SNARE;
            case 4 -> Sound.BLOCK_NOTE_BLOCK_HAT;
            case 5 -> Sound.BLOCK_NOTE_BLOCK_GUITAR;
            case 6 -> Sound.BLOCK_NOTE_BLOCK_FLUTE;
            case 7 -> Sound.BLOCK_NOTE_BLOCK_BELL;
            case 8 -> Sound.BLOCK_NOTE_BLOCK_CHIME;
            case 9 -> Sound.BLOCK_NOTE_BLOCK_XYLOPHONE;
            case 10 -> Sound.BLOCK_NOTE_BLOCK_IRON_XYLOPHONE;
            case 11 -> Sound.BLOCK_NOTE_BLOCK_COW_BELL;
            case 12 -> Sound.BLOCK_NOTE_BLOCK_DIDGERIDOO;
            case 13 -> Sound.BLOCK_NOTE_BLOCK_BIT;
            case 14 -> Sound.BLOCK_NOTE_BLOCK_BANJO;
            case 15 -> Sound.BLOCK_NOTE_BLOCK_PLING;
            default -> null;
        };
    }
}
