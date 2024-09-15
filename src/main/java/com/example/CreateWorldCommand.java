package com.example;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.Arrays;

public class CreateWorldCommand implements CommandExecutor {

    private final WorldCreatorPlugin plugin;

    public CreateWorldCommand(WorldCreatorPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length != 5) {
            sender.sendMessage("Usage: /world create <world_name> <pvp> <keepinv> <border_size> <world_type>");
            return false;
        }

        String worldName = args[1];
        String pvpArg = args[2].toLowerCase();
        String keepInvArg = args[3].toLowerCase();
        String borderSizeArg = args[4];
        String worldType = args[5].toLowerCase();

        boolean pvpEnabled;
        boolean keepInventory;
        int borderSize;

        // Validate PvP and Keep Inventory arguments
        if (!pvpArg.equals("true") && !pvpArg.equals("false")) {
            sender.sendMessage("Invalid PvP value. Use 'true' or 'false'.");
            return false;
        }
        pvpEnabled = Boolean.parseBoolean(pvpArg);

        if (!keepInvArg.equals("true") && !keepInvArg.equals("false")) {
            sender.sendMessage("Invalid Keep Inventory value. Use 'true' or 'false'.");
            return false;
        }
        keepInventory = Boolean.parseBoolean(keepInvArg);

        // Validate border size
        try {
            borderSize = Integer.parseInt(borderSizeArg);
        } catch (NumberFormatException e) {
            sender.sendMessage("Invalid border size. Please enter a valid integer.");
            return false;
        }

        // Validate world type
        if (!Arrays.asList("nether", "end", "overworld").contains(worldType)) {
            sender.sendMessage("Invalid world type. Valid types are: nether, end, overworld.");
            return false;
        }

        WorldCreator worldCreator = new WorldCreator(worldName);

        switch (worldType) {
            case "nether":
                worldCreator.environment(World.Environment.NETHER);
                break;
            case "end":
                worldCreator.environment(World.Environment.THE_END);
                break;
            case "overworld":
                worldCreator.environment(World.Environment.NORMAL);
                break;
        }

        World world = Bukkit.createWorld(worldCreator);

        if (world != null) {
            world.setPVP(pvpEnabled);
            world.getWorldBorder().setSize(borderSize);

            // Set keep inventory rule
            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "gamerule keepInventory " + keepInventory);

            sender.sendMessage("World created: " + worldName);
            sender.sendMessage("PvP Enabled: " + pvpEnabled);
            sender.sendMessage("Keep Inventory Enabled: " + keepInventory);
            sender.sendMessage("World Border Size: " + borderSize);
            sender.sendMessage("World Type: " + worldType);
        } else {
            sender.sendMessage("Failed to create world: " + worldName);
        }

        return true;
    }
}
