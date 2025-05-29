package org.infotoast.lorax.command;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.infotoast.lorax.Lorax;
import org.infotoast.lorax.WorldImproved;
import org.infotoast.lorax.customobject.CustomObject;
import org.infotoast.lorax.customobject.PlacedObject;
import org.infotoast.lorax.customobject.datatype.ObjectLocation;

public class SpawnCommand extends BaseCommand {
    public SpawnCommand(String name) {
        super(name);
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length < 2) {
                sender.sendMessage("§4Usage: " + getUsage());
                return false;
            }

            Location loc = player.getLocation();
            ObjectLocation objectCenter = new ObjectLocation(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
            WorldImproved world = WorldImproved.get(loc.getWorld());

            CustomObject toSpawn = Lorax.loader.getCustomObject(args[1]);
            int rotation;
            if (args.length == 3) {
                try {
                    rotation = Integer.parseInt(args[2]);
                } catch (NumberFormatException e) {
                    sender.sendMessage("§4Rotation can be a number between 0 and 3.");
                    sender.sendMessage("§4Usage: " + getUsage());
                    return false;
                }
            } else {
                rotation = world.getHashedRand(objectCenter.getX(), objectCenter.getY(), objectCenter.getZ()).nextInt(4);
            }

            PlacedObject obj = toSpawn.getPlacedObject(objectCenter, rotation, world);
            obj.placeAll();

            sender.sendMessage("§bObject placed!");
            return true;
        } else {
            sender.sendMessage("Must be sent as a player.");
            sender.sendMessage("Usage: " + getUsage());
            return false;
        }
    }

    @Override
    public String getUsage() {
        return "/lorax spawn <structure> [rotation]";
    }

    @Override
    public String getHelpMessage() {
        return "Spawns a specific structure /swg spawn <structure> [rotation]";
    }
}
