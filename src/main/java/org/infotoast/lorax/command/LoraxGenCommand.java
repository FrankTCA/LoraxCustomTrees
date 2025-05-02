package org.infotoast.lorax.command;

import org.bukkit.Chunk;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import org.infotoast.lorax.ChunkBasedPopulator;
import org.infotoast.lorax.Lorax;
import org.infotoast.lorax.Populator;

import java.util.Random;

public class LoraxGenCommand implements CommandExecutor {
    private final Lorax plugin;
    private final ChunkBasedPopulator pop = new ChunkBasedPopulator();

    public LoraxGenCommand(Lorax plugin, Populator pop) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender.hasPermission("lorax.loraxgen")) {
            if (sender instanceof Player) {
                int chunkX = ((int)((Player) sender).getLocation().getX()) >> 4;
                int chunkZ = ((int)((Player) sender).getLocation().getZ()) >> 4;
                Chunk chunk = ((Player)sender).getChunk();
                pop.populate(((Player) sender).getWorld(), new Random(), chunkX, chunkZ, chunk);
                sender.sendMessage(Lorax.colorize(plugin.getConfig().getString("messages.loraxgen-success")));
                ConsoleCommandSender console = plugin.getServer().getConsoleSender();
                console.sendMessage(Lorax.colorize("[Lorax] &bPlayer &2" + ((Player) sender).getDisplayName() + "&b has loraxed chunk &3(" + chunkX + ", " + chunkZ + ")"));
                return true;
            }
            sender.sendMessage(Lorax.colorize(plugin.getConfig().getString("messages.player-only-command")));
            return false;
        }
        sender.sendMessage(Lorax.colorize(plugin.getConfig().getString("messages.no-permission")));
        return false;
    }
}
