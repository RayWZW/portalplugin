package com.example;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;
import com.example.commands.WorldCommand;
import com.example.commands.RTPCommand;
import com.example.commands.WhereAmICommand;

import java.io.File;

public class AdvancedPortals extends JavaPlugin {

    @Override
    public void onEnable() {
        getLogger().info("AdvancedPortals enabled!");

        // Save the default configuration if not present
        saveDefaultConfig();

        // Register the /world command (create and list worlds)
        getCommand("world").setExecutor(new WorldCommand(this));
        getCommand("worldrtp").setExecutor(new RTPCommand(this));
        getCommand("whereami").setExecutor(new WhereAmICommand());

        // Automatically load any extra world folders on server startup
        loadWorldFolders();
    }

    @Override
    public void onDisable() {
        getLogger().info("AdvancedPortals disabled!");
    }

    // Method to load extra world folders that are not part of the default worlds
    private void loadWorldFolders() {
        File worldContainer = getServer().getWorldContainer();
        getLogger().info("Loading extra world folders...");

        for (File file : worldContainer.listFiles()) {
            if (file.isDirectory() && !file.getName().equals("world")) {
                String worldName = file.getName();
                File sessionLock = new File(file, "session.lock");
                File paperWorldYaml = new File(file, "paper-world.yml");

                if (sessionLock.exists() && paperWorldYaml.exists()) {
                    if (Bukkit.getWorld(worldName) == null) {
                        Bukkit.createWorld(new org.bukkit.WorldCreator(worldName));
                        getLogger().info("Loaded world: " + worldName);
                    }
                } else {
                    getLogger().info("Skipped world folder: " + worldName + " (missing session.lock or paper-world.yml)");
                }
            }
        }
    }
}
