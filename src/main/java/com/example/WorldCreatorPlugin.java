package com.example;

import org.bukkit.plugin.java.JavaPlugin;

public class WorldCreatorPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        getLogger().info("WorldCreatorPlugin enabled!");

        // Register the commands
        getCommand("world").setExecutor(new WorldCommandExecutor(this));
        getCommand("world").setTabCompleter(new WorldCommandExecutor(this));
        getCommand("listworlds").setExecutor(new ListWorldsCommand(this));
    }

    @Override
    public void onDisable() {
        getLogger().info("WorldCreatorPlugin disabled!");
    }
}
