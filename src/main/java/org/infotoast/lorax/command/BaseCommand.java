package org.infotoast.lorax.command;

import org.bukkit.command.CommandSender;

public abstract class BaseCommand {
    protected final String name;
    protected String helpMessage = "No help message provided.";
    protected String usage = "No usage provided.";

    public BaseCommand(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getHelpMessage() {
        return helpMessage;
    }

    public String getUsage() {
        return usage;
    }

    public abstract boolean execute(CommandSender sender, String[] args);
}
