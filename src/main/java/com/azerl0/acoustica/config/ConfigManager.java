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

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.logging.Logger;

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
        File songsFolder = new File(acoustica.getDataFolder(), "songs");

        if (!songsFolder.exists()) {
            songsFolder.mkdirs();
        }
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
        Bukkit.getServer().getAsyncScheduler().runNow(Acoustica.getInstance(), _ -> {
            ConfigurationSection songs = config.getConfigurationSection("songs");
            if (songs == null) return;

            getLogger().info("start load songs");
            songManager.clearSongs();
            for (String id : songs.getKeys(false)) {
                ConfigurationSection songSec = songs.getConfigurationSection(id);
                if (songSec == null) {
                    getLogger().warning("failed load song section. id: " + id);
                    continue;
                }

                String fileName = songSec.getString("fileName");
                if (fileName == null) {
                    getLogger().warning("failed load song file name. id: " + id);
                    continue;
                }

                Path path = acoustica.getDataPath().resolve("songs").resolve(fileName + ".nbs");
                NbsSong nbsSong;
                try {
                    nbsSong = NbsReader.read(path);
                } catch (IOException e) {
                    getLogger().warning("failed load nbs file. id: " + id + ", message: " + e.getMessage());
                    continue;
                }
                float volume = (float) songSec.getDouble("volume", 1.0);
                List<SongNote> notes = SongNote.getNotesFromNbsSong(nbsSong);
                Component name = Component.text(id);
                String legacy = songSec.getString("name");
                if (legacy != null) name = LegacyComponentSerializer.legacyAmpersand().deserialize(legacy);
                List<String> advancements = songSec.getStringList("advancements");
                Song song = new Song(id, name,notes, volume, fileName, advancements);
                songManager.addSong(id, song);
            }
            getLogger().info("complete load songs");

        });
    }

    public void saveSong(Song song) {
        String path = "songs." + song.getId();
        config.set(path, null);

        config.set(path + ".name", LegacyComponentSerializer.legacyAmpersand().serialize(song.getSongName()));
        config.set(path + ".fileName", song.getFileName());
        config.set(path + ".volume", song.getVolume());
        if (!song.getAdvancementKeys().isEmpty()) config.set(path + ".advancements", song.getAdvancementKeys());

        acoustica.saveConfig();
    }

    private Logger getLogger() {
        return acoustica.getLogger();
    }
}