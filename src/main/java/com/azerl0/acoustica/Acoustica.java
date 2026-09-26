package com.azerl0.acoustica;

import com.azerl0.acoustica.speaker.SpeakerManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class Acoustica extends JavaPlugin {
    private static Acoustica INSTANCE;

    private SpeakerManager speakerManager;

    @Override
    public void onEnable() {
        INSTANCE = this;
        speakerManager = new SpeakerManager();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static Acoustica getInstance() {
        return INSTANCE;
    }

    public SpeakerManager getSpeakerManager() {
        return speakerManager;
    }
}
