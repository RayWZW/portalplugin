package com.example.commands;

import com.example.AdvancedPortals;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Random;

public class RTPCommand implements CommandExecutor {

    private final AdvancedPortals plugin;

    public RTPCommand(AdvancedPortals plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length != 1) {
            sender.sendMessage("Usage: /worldrtp <worldname>");
            return true;
        }

        String worldName = args[0];
        World world = Bukkit.getWorld(worldName);

        if (world == null) {
            sender.sendMessage("World not found.");
            return true;
        }

        // Check if the command is executed by a player
        if (sender instanceof Player) {
            Player player = (Player) sender;
            teleportPlayer(player, world);
        } else {
            // Command blocks will execute this
            sender.sendMessage("Command executed from a command block.");
            if (sender instanceof org.bukkit.command.BlockCommandSender) {
                teleportPlayer(sender, world);
            }
        }

        return true;
    }

    private void teleportPlayer(CommandSender sender, World world) {
        Random random = new Random();
        int x = random.nextInt(10000) - 5000;
        int z = random.nextInt(10000) - 5000;

        // Ensure coordinates are within the world border limits (e.g., -5000 to 5000)
        x = Math.max(-5000, Math.min(x, 5000));
        z = Math.max(-5000, Math.min(z, 5000));

        // Teleport sender to random location
        Location location = new Location(world, x, world.getHighestBlockYAt(x, z), z);

        if (sender instanceof Player) {
            ((Player) sender).teleport(location);
        } else {
            ((org.bukkit.command.BlockCommandSender) sender).getBlock().getWorld().getBlockAt(location).setType(org.bukkit.Material.AIR);
        }

        sender.sendMessage("Teleported to a random location in " + world.getName());
    }
}
