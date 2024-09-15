package com.example.commands;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ListWorldsCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        sender.sendMessage("Loaded worlds:");

        for (World world : Bukkit.getWorlds()) {
            sender.sendMessage("- " + world.getName());
        }

        return true;
    }
}
