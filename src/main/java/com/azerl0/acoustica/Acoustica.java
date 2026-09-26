package com.azerl0.acoustica;

import com.azerl0.acoustica.config.ConfigManager;
import com.azerl0.acoustica.itemframe.ItemFrameListener;
import com.azerl0.acoustica.song.SongManager;
import com.azerl0.acoustica.speaker.EntityLocationSpeakerListener;
import com.azerl0.acoustica.speaker.EntityLocationSpeakerManager;
import com.azerl0.acoustica.speaker.SpeakerManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class Acoustica extends JavaPlugin {
    private static Acoustica INSTANCE;

    private ConfigManager configManager;
    private SpeakerManager speakerManager;
    private EntityLocationSpeakerManager entityLocationSpeakerManager;
    private SongManager songManager;

    @Override
    public void onEnable() {
        INSTANCE = this;
        configManager = new ConfigManager(this);
        speakerManager = new SpeakerManager();
        entityLocationSpeakerManager = new EntityLocationSpeakerManager(speakerManager);
        songManager = new SongManager();

        saveDefaultConfig();
        configManager.processConfigs();

        PluginManager pluginManager = Bukkit.getPluginManager();

        pluginManager.registerEvents(new EntityLocationSpeakerListener(entityLocationSpeakerManager), this);
        pluginManager.registerEvents(new ItemFrameListener(entityLocationSpeakerManager, songManager), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static Acoustica getInstance() {
        return INSTANCE;
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public SpeakerManager getSpeakerManager() {
        return speakerManager;
    }

    public EntityLocationSpeakerManager getEntityLocationSpeakerManager() {
        return entityLocationSpeakerManager;
    }

    public SongManager getSongManager() {
        return songManager;
    }
}
