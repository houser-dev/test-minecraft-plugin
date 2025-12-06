package me.houserlab.trainingPlugin;

import org.bukkit.NamespacedKey;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class TrainingPlugin extends JavaPlugin {
    public static NamespacedKey COWSPLOSION_KEY;
    private static TrainingPlugin instance;

    public static TrainingPlugin getInstance() {
        return instance;
    }

    public void log(String msg) {
        getLogger().info(msg);
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        getLogger().info("@ TRAINING PLUGIN: ENABLED AND LOADING!");
        COWSPLOSION_KEY = new NamespacedKey(this, "cowsplosion");

        getServer().getPluginManager().registerEvents(new EntityListener(), this);
        Objects.requireNonNull(getCommand("cow")).setExecutor(new CowCommand());


        getLogger().info("@ TRAINING PLUGIN: LOADED");

    }

    @Override
    public void onDisable() {
        getLogger().info("@ TRAINING PLUGIN: DISABLED");
        // Plugin shutdown logic
    }
}
