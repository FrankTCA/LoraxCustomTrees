package org.infotoast.lorax.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.infotoast.lorax.Lorax;

public class CentralCommandExecutor implements CommandExecutor {
    private final Lorax plugin;
    public CentralCommandExecutor(Lorax plugin) {
        this.plugin = plugin;
        if (Lorax.plugin.getServer().getPluginManager().isPluginEnabled("WorldEdit")) {
            EXPORT = new ExportCommand("export");
        } else {
            EXPORT = new ExportCommandNoWE("export");
        }
    }

    private final BaseCommand EXPORT;
    private final BaseCommand SPAWN = new SpawnCommand("spawn");

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sendHelp(sender);
        }

        if (args[0].equalsIgnoreCase("export")) {
            return EXPORT.execute(sender, args);
        }

        if (args[0].equalsIgnoreCase("spawn")) {
            return SPAWN.execute(sender, args);
        }
        return false;
    }

    private void sendHelp(CommandSender sender) {
        sender.sendMessage("/lorax help");
        sender.sendMessage(SPAWN.getUsage() + " - " + SPAWN.getHelpMessage());
        sender.sendMessage(EXPORT.getUsage() + " - " + EXPORT.getHelpMessage());
    }
}
