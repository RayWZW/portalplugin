package com.example;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.TabCompleter;

import java.io.File; 
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WorldCommandExecutor implements CommandExecutor, TabCompleter {

    private final WorldCreatorPlugin plugin;

    public WorldCommandExecutor(WorldCreatorPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof ConsoleCommandSender) && !sender.isOp()) {
            sender.sendMessage("You must be an operator to use this command.");
            return false;
        }

        if (args.length == 0) {
            sender.sendMessage("Usage: /world create <name>");
            return false;
        }

        if (args[0].equalsIgnoreCase("create")) {
            if (args.length != 2) {
                sender.sendMessage("Usage: /world create <name>");
                return false;
            }

            String worldName = args[1];

            WorldCreator worldCreator = new WorldCreator(worldName).environment(World.Environment.NORMAL);
            World world = Bukkit.createWorld(worldCreator);

            if (world != null) {
                sender.sendMessage("World created: " + worldName);
            } else {
                sender.sendMessage("Failed to create world: " + worldName);
            }
        } else {
            sender.sendMessage("Unknown command. Usage: /world create <name>");
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completions = new ArrayList<>();
        if (args.length == 1) {

            completions.add("create");
        } else if (args.length == 2 && args[0].equalsIgnoreCase("create")) {

            File worldContainer = plugin.getServer().getWorldContainer();
            File[] worldFiles = worldContainer.listFiles(File::isDirectory);

            if (worldFiles != null) {
                for (File file : worldFiles) {
                    if (file.isDirectory() && new File(file, "session.lock").exists()) {
                        completions.add(file.getName());
                    }
                }
            }
        }
        return completions;
    }
}