package com.example.commands;

import com.example.AdvancedPortals;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public class WorldCommand implements CommandExecutor, TabCompleter {

    private final AdvancedPortals plugin;

    public WorldCommand(AdvancedPortals plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // Check if the sender is a player and has permission
        if (!(sender instanceof Player) || !sender.hasPermission("world.create")) {
            sender.sendMessage("You need to be an OP to use this command.");
            return true;
        }

        // Ensure the correct number of arguments
        if (args.length < 1) {
            sender.sendMessage("Usage: /world create <worldname>");
            return true;
        }

        // Handle world creation
        if ("create".equalsIgnoreCase(args[0])) {
            if (args.length != 2) {
                sender.sendMessage("Usage: /world create <worldname>");
                return true;
            }
            String worldName = args[1];

            // Create the world
            Bukkit.createWorld(new org.bukkit.WorldCreator(worldName));
            sender.sendMessage("World created: " + worldName);
            return true;
        }

        // List worlds
        if ("list".equalsIgnoreCase(args[0])) {
            sender.sendMessage("Worlds:");
            for (String worldName : Bukkit.getWorlds().stream().map(w -> w.getName()).toList()) {
                sender.sendMessage("- " + worldName);
            }
            return true;
        }

        sender.sendMessage("Unknown command. Use /world create <name> or /world list.");
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (!(sender instanceof Player) || !sender.hasPermission("world.create")) {
            return Arrays.asList();
        }

        if (args.length == 1) {
            return Arrays.asList("create", "list");
        } else if (args.length == 2 && "create".equalsIgnoreCase(args[0])) {
            return Arrays.asList("world_the_nether", "world_the_end", "world_nether"); // Example, adjust as needed
        }

        return Arrays.asList();
    }
}
