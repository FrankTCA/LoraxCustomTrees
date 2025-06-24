package org.infotoast.lorax.command;

import org.bukkit.command.CommandSender;
import org.infotoast.lorax.Lorax;

public class ExportCommandNoWE extends BaseCommand {
    public ExportCommandNoWE(String name) {
        super(name);
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        sender.sendMessage(Lorax.colorize(Lorax.plugin.getConfig().getString("messages.no-worledit")));
        return true;
    }
}
