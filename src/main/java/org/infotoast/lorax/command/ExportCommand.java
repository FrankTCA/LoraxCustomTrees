package org.infotoast.lorax.command;

import com.sk89q.worldedit.IncompleteRegionException;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.regions.Region;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.infotoast.lorax.Lorax;
import org.infotoast.lorax.customobject.CustomObject;
import org.infotoast.lorax.customobject.creator.CustomObjectCreator;
import org.infotoast.lorax.customobject.creator.CustomObjectWriter;

import java.io.IOException;

public class ExportCommand extends BaseCommand {
    public ExportCommand(String name) {
        super(name);
    }

    private static Region getCurrentRegion(Player source) {
        try {
            Region reg = WorldEdit.getInstance().getSessionManager().get(BukkitAdapter.adapt(source))
                    .getSelection(BukkitAdapter.adapt(source.getWorld()));
            return reg;
        } catch (IncompleteRegionException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            if (sender.hasPermission("lorax.export")) {
                Player player = (Player) sender;
                String objectName = args[1];
                Region region = getCurrentRegion(player);

                CustomObjectCreator creator = new CustomObjectCreator(region.getBoundingBox(), region.getCenter());
                CustomObject object = creator.create(objectName, player.getWorld());

                try {
                    CustomObjectWriter.writeToFile(object);
                    sender.sendMessage(Lorax.colorize(Lorax.plugin.getConfig().getString("messages.export-success").replace("%name%", objectName)));
                    return true;
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            sender.sendMessage(Lorax.colorize(Lorax.plugin.getConfig().getString("messages.no-permission")));
            return false;
        }
        sender.sendMessage(Lorax.colorize(Lorax.plugin.getConfig().getString("messages.player-only-command")));
        return false;
    }

    @Override
    public String getUsage() {
        return "/lorax export <name>";
    }

    @Override
    public String getHelpMessage() {
        return "Exports WorldEdit-selected structure to file /lorax export <name>";
    }
}
