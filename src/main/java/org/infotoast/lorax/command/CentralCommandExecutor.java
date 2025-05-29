package org.infotoast.lorax.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.infotoast.lorax.Lorax;

public class CentralCommandExecutor implements CommandExecutor {
    private final Lorax plugin;
    public CentralCommandExecutor(Lorax plugin) {
        this.plugin = plugin;
    }

    private final ExportCommand EXPORT = new ExportCommand("export");
    private final SpawnCommand SPAWN = new SpawnCommand("spawn");

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
