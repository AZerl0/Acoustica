package com.azerl0.acoustica.config;

import com.azerl0.acoustica.Acoustica;
import com.azerl0.acoustica.nbs.NbsReader;
import com.azerl0.acoustica.nbs.NbsSong;
import com.azerl0.acoustica.song.Song;
import com.azerl0.acoustica.song.SongManager;
import com.azerl0.acoustica.song.SongNote;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public class ConfigManager {
    private final Acoustica acoustica;
    private FileConfiguration config;
    private Config defaultConfig;

    public ConfigManager(Acoustica acoustica) {
        this.acoustica = acoustica;
    }

    public void processConfigs() {
        config = acoustica.getConfig();
        defaultConfig = new Config(config);
        loadSongs(Acoustica.getInstance().getSongManager());
    }

    public void reload() {
        acoustica.reloadConfig();
        processConfigs();
    }

    public Config getDefaultConfig() {
        return defaultConfig;
    }

    public void loadSongs(SongManager songManager) {
        Bukkit.getServer().getAsyncScheduler().runNow(Acoustica.getInstance(), task -> {
            ConfigurationSection songs = config.getConfigurationSection("songs");
            if (songs == null) return;

            songManager.clearSongs();
            for (String id : songs.getKeys(false)) {
                ConfigurationSection songSec = songs.getConfigurationSection(id);
                if (songSec == null) continue;

                String fileName = songSec.getString("fileName");
                if (fileName == null) continue;

                Path path = acoustica.getDataPath().resolve("songs").resolve(fileName + ".nbs");
                NbsSong nbsSong;
                try {
                    nbsSong = NbsReader.read(path);
                } catch (IOException _) {
                    continue;
                }
                float volume = (float) songSec.getDouble("volume", 1.0);
                List<SongNote> notes = SongNote.getNotesFromNbsSong(nbsSong);
                Component name = Component.text(id);
                String legacy = songSec.getString("name");
                if (legacy != null) name = LegacyComponentSerializer.legacyAmpersand().deserialize(legacy);
                Song song = new Song(id, name,notes, volume, fileName);
                songManager.addSong(id, song);
            }

        });
    }
}