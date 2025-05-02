package org.infotoast.lorax;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldInitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import org.infotoast.lorax.command.LoraxGenCommand;

import java.io.*;
import java.nio.file.Files;
import java.util.logging.Logger;

public final class Lorax extends JavaPlugin {
    private final Populator pop = new Populator();
    private static Logger logger;
    @Override
    public void onEnable() {
        // Plugin startup logic
        logger = this.getLogger();
        logger.info("Starting Lorax Engine...");
        getCommand("loraxgen").setExecutor(new LoraxGenCommand(this, pop));
        getServer().getPluginManager().registerEvents(new WorldListener(), this);
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
}