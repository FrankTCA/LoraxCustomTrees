package org.infotoast.lorax;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldInitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import org.infotoast.lorax.command.LoraxGenCommand;
import org.infotoast.lorax.util.DatapackDecompressor;

import org.jetbrains.annotations.NotNull;

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
        plugin = this;
        getCommand("loraxgen").setExecutor(new LoraxGenCommand(this, pop));
        getServer().getPluginManager().registerEvents(new WorldListener(), this);
    }

    public @NotNull Logger getBukkitLogger() {
        return logger;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private class WorldListener implements Listener {
        @EventHandler(priority= EventPriority.LOW)
        public void onWorldInit(WorldInitEvent evt) {
            evt.getWorld().getPopulators().add(pop);
            new DatapackDecompressor(plugin).onWorldInit();
        }
    }
}