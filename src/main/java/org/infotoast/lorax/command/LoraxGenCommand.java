package org.infotoast.lorax.command;

import org.bukkit.Chunk;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.infotoast.lorax.Lorax;
import org.infotoast.lorax.Populator;

import java.util.Random;

public class LoraxGenCommand implements CommandExecutor {
    private final Lorax plugin;
    private final Populator pop = new Populator();

    public LoraxGenCommand(Lorax plugin, Populator pop) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender.hasPermission("lorax.loraxgen")) {
            if (sender instanceof Player) {
                Chunk chunk = ((Player) sender).getLocation().getChunk();
                pop.populate(((Player) sender).getWorld(), new Random(), chunk);
                sender.sendMessage("§bChunk has been loraxed!");
                ConsoleCommandSender console = plugin.getServer().getConsoleSender();
                console.sendMessage("[Lorax] §bPlayer §2" + ((Player) sender).getDisplayName() + "§b has loraxed chunk §3(" + chunk.getX() + ", " + chunk.getZ() + ")");
                return true;
            }
            sender.sendMessage("§4Must be run as a player.");
            return false;
        }
        sender.sendMessage("§4Permission denied.");
        return false;
    }
}
