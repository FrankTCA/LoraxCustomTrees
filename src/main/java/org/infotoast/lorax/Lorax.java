package org.infotoast.lorax;

import org.bukkit.Chunk;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldInitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Random;

public final class Lorax extends JavaPlugin {
    private final Populator pop = new Populator();
    @Override
    public void onEnable() {
        // Plugin startup logic
        System.out.println("Starting Lorax Engine...");
        getServer().getPluginManager().registerEvents(new WorldListener(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (label.equals("loraxmedaddy")) {
            if (sender.hasPermission("lorax.loraxmedaddy")) {
                if (sender instanceof Player) {
                    Chunk chunk = ((Player) sender).getLocation().getChunk();
                    pop.populate(((Player) sender).getWorld(), new Random(), chunk);
                    sender.sendMessage("§bChunk has been loraxed!");
                    ConsoleCommandSender console = getServer().getConsoleSender();
                    console.sendMessage("[Lorax] §bPlayer §2" + ((Player) sender).getDisplayName() + "§b has loraxed chunk §3(" + chunk.getX() + ", " + chunk.getZ() + ")");
                    return true;
                }
                sender.sendMessage("§4Must be run as a player.");
                return false;
            }
            sender.sendMessage("§4Permission denied.");
        }
        return false;
    }

    private class WorldListener implements Listener {
        @EventHandler(priority= EventPriority.LOW)
        public void onWorldInit(WorldInitEvent evt) {
            evt.getWorld().getPopulators().add(pop);
        }
    }
}