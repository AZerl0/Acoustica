package com.azerl0.acoustica.nbs;

import org.bukkit.Sound;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class NoteInstrument {

    public static @Nullable Sound getSoundFromNumber(int num, List<NbsCustomInstrument> customInstruments) {
        if (num <= 15) {
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
        } else {
            int i = num - 16;
            if (customInstruments.size() >= i) {
                // SPECIAL
                String fileName = customInstruments.get(i).getSoundFile();
                return switch (fileName) {
                    case "minecraft/dig/sand1.ogg" -> Sound.BLOCK_SAND_BREAK;
                    case "minecraft/random/anvil_land.ogg" -> Sound.BLOCK_ANVIL_LAND;
                    case "minecraft/portal/trigger.ogg" -> Sound.BLOCK_PORTAL_TRIGGER;
                    case "minecraft/random/orb.ogg" -> Sound.ENTITY_EXPERIENCE_ORB_PICKUP;
                    case "minecraft/random/fizz.ogg" -> Sound.ENTITY_GENERIC_BURN;
                    case "minecraft/dig/stone.ogg" -> Sound.BLOCK_STONE_BREAK;
                    case "minecraft/entity/shulker/hurt_closed1.ogg" -> Sound.ENTITY_SHULKER_HURT_CLOSED;
                    default -> null;
                };
            } else {
                return null;
            }
        }
    }
}
