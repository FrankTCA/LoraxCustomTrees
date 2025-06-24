package org.infotoast.lorax;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldInitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import org.infotoast.lorax.command.CentralCommandExecutor;
import org.infotoast.lorax.customobject.CustomObjectLoader;
import org.infotoast.lorax.customobject.exception.CustomObjectLoadingFailedException;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;
import java.util.logging.Logger;

public final class Lorax extends JavaPlugin {
    private final Populator pop = new Populator();
    private static Logger logger;
    public static Lorax plugin;
    public static CustomObjectLoader loader;
    @Override
    public void onEnable() {
        // Plugin startup logic
        logger = this.getLogger();
        logger.info("Starting Lorax Engine...");
        saveDefaultConfig();
        plugin = this;
        logger.info("Loading bo5 custom objects...");
        this.loader = new CustomObjectLoader();
        try {
            loader.loadObjects();
        } catch (CustomObjectLoadingFailedException e) {
            e.printStackTrace();
        }
        logger.info("Latching lorax engine to run when world loading begins.");
        getCommand("lorax").setExecutor(new CentralCommandExecutor(this));
        getServer().getPluginManager().registerEvents(new WorldListener(), this);

        if (getConfig().getBoolean("disable-vanilla-trees")) {
            logger.info("Attempting to disable vanilla tree generation!");

        }
        logger.info("Lorax Engine loaded successfully!");
        if (getConfig().getBoolean("enable-splash-text")) {
            BufferedReader splashReader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(getResource("splash.txt"))));
            String line;
            try {
                line = splashReader.readLine();
                while (line != null) {
                    getServer().getConsoleSender().sendMessage(colorize("&a" + line + "&r"));
                    line = splashReader.readLine();
                }
            } catch (IOException ex) {
                getLogger().warning("Could not read splash text! Is your build compiled correctly?");
            }
        }
    }

    public @NotNull Logger getBukkitLogger() {
        return logger;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static String colorize(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    private class WorldListener implements Listener {
        @EventHandler(priority= EventPriority.LOW)
        public void onWorldInit(WorldInitEvent evt) {
            getLogger().info("World " + evt.getWorld().getName() + " is being initialized...");
            if (getConfig().getBoolean("enable-populator")) {
                if (getConfig().getBoolean("select-worlds"))
                    if (!getConfig().getStringList("enabled-worlds").contains(evt.getWorld().getName()))
                        return;
                evt.getWorld().getPopulators().add(pop);
                getLogger().info("Populator enabled for world " + evt.getWorld().getName() + "!");
            }
        }
    }
}