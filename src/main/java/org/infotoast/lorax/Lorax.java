package org.infotoast.lorax;

import de.tr7zw.changeme.nbtapi.NBT;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldInitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import org.infotoast.lorax.command.LoraxGenCommand;
import org.infotoast.lorax.util.DatapackDecompressor;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;
import java.util.logging.Logger;

public final class Lorax extends JavaPlugin {
    private final Populator pop = new Populator();
    private static Logger logger;
    private Lorax plugin;
    @Override
    public void onEnable() {
        // Plugin startup logic
        logger = this.getLogger();
        logger.info("Starting Lorax Engine...");
        saveDefaultConfig();
        logger.info("Loading NBT-API...");
        if (!NBT.preloadApi()) {
            getLogger().severe("NBT-API could not be loaded! Must disable plugin.");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        logger.info("NBT-API loaded successfully. Loading Lorax Engine...");
        plugin = this;
        getCommand("loraxgen").setExecutor(new LoraxGenCommand(this, pop));
        getServer().getPluginManager().registerEvents(new WorldListener(), this);
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
            if (getConfig().getBoolean("enable-populator")) {
                if (getConfig().getBoolean("select-worlds"))
                    if (!getConfig().getStringList("enabled-worlds").contains(evt.getWorld().getName()))
                        return;
                evt.getWorld().getPopulators().add(pop);
            }
            new DatapackDecompressor(plugin).onWorldInit();
        }
    }
}