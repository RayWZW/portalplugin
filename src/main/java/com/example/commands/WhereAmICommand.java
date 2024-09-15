package com.example.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WhereAmICommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            String worldName = player.getWorld().getName();
            player.sendMessage("§aYou are currently in world: " + worldName);
            return true;
        } else {
            sender.sendMessage("§cThis command can only be used by players.");
            return false;
        }
    }
}
