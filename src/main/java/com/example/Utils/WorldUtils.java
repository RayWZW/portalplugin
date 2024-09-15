package com.example.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Random;

public class WorldUtils {

    // Create a new world with specified settings
    public static void createWorld(JavaPlugin plugin, String worldName, boolean pvp, boolean keepInventory) {
        WorldCreator worldCreator = new WorldCreator(worldName);
        World world = worldCreator.createWorld();

        // Set world settings
        world.setPVP(pvp);
        FileConfiguration config = plugin.getConfig();
        config.set("worlds." + worldName + ".keepInventory", keepInventory);
        plugin.saveConfig();
    }

    // Generate a random teleport location in the specified world
    public static Location getRandomLocation(World world) {
        Random random = new Random();
        int x = random.nextInt(20000) - 10000;
        int z = random.nextInt(20000) - 10000;
        int y = world.getHighestBlockYAt(x, z);
        return new Location(world, x, y, z);
    }
}
