package com.kltyton.stardewfishingFabric.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kltyton.stardewfishingFabric.StardewfishingFabric;
import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class StardewFishingConfig {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path CONFIG_PATH = FabricLoader.getInstance().getConfigDir().resolve(StardewfishingFabric.MODID + ".json");
    private static StardewFishingConfig INSTANCE;

    // Config options
    public boolean enableMinigame = true;
    public boolean enableSounds = true;
    public double minigameDifficulty = 1.0;
    public boolean debugMode = false;

    public static StardewFishingConfig getInstance() {
        if (INSTANCE == null) {
            INSTANCE = load();
        }
        return INSTANCE;
    }

    public static void reload() {
        INSTANCE = load();
    }

    private static StardewFishingConfig load() {
        if (Files.exists(CONFIG_PATH)) {
            try {
                String json = Files.readString(CONFIG_PATH);
                StardewFishingConfig config = GSON.fromJson(json, StardewFishingConfig.class);
                if (config != null) {
                    return config;
                }
            } catch (Exception e) {
                System.err.println("[StardewFishing] Failed to load config: " + e.getMessage());
            }
        }
        
        // Create default config
        StardewFishingConfig defaultConfig = new StardewFishingConfig();
        defaultConfig.save();
        return defaultConfig;
    }

    public void save() {
        try {
            Files.createDirectories(CONFIG_PATH.getParent());
            String json = GSON.toJson(this);
            Files.writeString(CONFIG_PATH, json);
        } catch (IOException e) {
            System.err.println("[StardewFishing] Failed to save config: " + e.getMessage());
        }
    }
}
