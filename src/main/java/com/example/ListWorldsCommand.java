package com.example;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;

import java.io.File;

public class ListWorldsCommand implements CommandExecutor {

    private final WorldCreatorPlugin plugin;

    public ListWorldsCommand(WorldCreatorPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof ConsoleCommandSender) && !sender.isOp()) {
            sender.sendMessage("You must be an operator to use this command.");
            return false;
        }

        listWorlds(sender);
        return true;
    }

    private void listWorlds(CommandSender sender) {
        File worldContainer = plugin.getServer().getWorldContainer();
        File[] worldFiles = worldContainer.listFiles(File::isDirectory);

        if (worldFiles == null || worldFiles.length == 0) {
            sender.sendMessage("No worlds found.");
            return;
        }

        sender.sendMessage("Worlds:");
        for (File file : worldFiles) {
            File sessionLock = new File(file, "session.lock");
            if (sessionLock.exists()) {
                sender.sendMessage("- " + file.getName());
            }
        }
    }
}