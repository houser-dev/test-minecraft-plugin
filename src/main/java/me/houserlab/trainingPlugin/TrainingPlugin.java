package me.houserlab.trainingPlugin;

import org.bukkit.plugin.java.JavaPlugin;

public final class TrainingPlugin extends JavaPlugin {
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

        getServer().getPluginManager().registerEvents(new EntityListener(), this);


        getLogger().info("@ TRAINING PLUGIN: LOADED");

    }

    @Override
    public void onDisable() {
        getLogger().info("@ TRAINING PLUGIN: DISABLED");
        // Plugin shutdown logic
    }
}
