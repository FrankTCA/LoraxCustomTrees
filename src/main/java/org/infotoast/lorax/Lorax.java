package org.infotoast.lorax;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldInitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import de.tr7zw.changeme.nbtapi.NBT;

import java.io.*;
import java.nio.file.Files;
import java.util.logging.Logger;

public final class Lorax extends JavaPlugin {

    private static Logger logger;
    private static Populator pop;
    private static AppleDropListener appleDropper;
	private static Lorax instance;
    
    public Lorax() {

    	instance = this;
    	pop = new Populator();
    	appleDropper = new AppleDropListener();
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        logger = this.getLogger();
        logger.info("Starting Lorax Engine...");
        
        logger.info("Pre-loading NBT-API...");
        if (!NBT.preloadApi()) {
            getLogger().warning("NBT-API wasn't initialized properly, disabling the plugin");
            getPluginLoader().disablePlugin(this);
            return;
        }

        getServer().getPluginManager().registerEvents(new WorldListener(), this);
        getServer().getPluginManager().registerEvents(appleDropper, this);
        this.getCommand("loraxme").setExecutor(new CommandListener( appleDropper ));
        this.getCommand("loraxset").setExecutor(new CommandListener( appleDropper ));
    }

    private void extract() {
        File worldFolder = getServer().getWorldContainer();
        String worldName = getServer().getWorlds().get(0).getName();
        String datapackFolder = worldFolder.getAbsolutePath() + File.separator + worldName + File.separator + "datapacks" + File.separator;
        File dataPackFile = new File(datapackFolder, "EasyTrees.zip");
        if (!dataPackFile.exists()) {
            logger.info("Extracting trees datapack!");
            copy("EasyTrees.zip", dataPackFile);
        }
    }

    private void copy(String fileName, File file) {
        InputStream is = null;
        try {
            is = this.getResource(fileName);
            OutputStream o = Files.newOutputStream(file.toPath());
            byte[] buf = new byte[1024];
            int len;
            try {
                while ((len = is.read(buf)) > 0) {
                    o.write(buf, 0, len);
                }
            } catch (IOException ex) {
                logger.severe("Could not copy datapack file!");
            } finally {
                try {
                    o.close();
                } catch (IOException e) {
                    logger.severe("Could not close datapack output stream.");
                }
            }
        } catch (IOException ex) {
            logger.severe("Could not find datapack file!");
        } finally {
            if (is != null)
                try {
                    is.close();
                } catch (IOException ex) {
                    logger.severe("Could not close datapack input stream.");
                }
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private class WorldListener implements Listener {
        @EventHandler(priority= EventPriority.LOW)
        public void onWorldInit(WorldInitEvent evt) {
            extract();
            getServer().reloadData();
            getServer().getDatapackManager().getPacks().stream().forEach((d) -> {
                if (d.getName().contains("EasyTrees.zip")) {
                    d.setEnabled(true);
                }
            });
            evt.getWorld().getPopulators().add(pop);
        }
    }
    
    public static Lorax getInstance() {
    	return instance;
    }

    public static Populator getPopulator() {
    	return pop;
    }
}