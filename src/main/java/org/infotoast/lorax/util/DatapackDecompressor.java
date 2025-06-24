package org.infotoast.lorax.util;

import org.bukkit.event.Listener;

import org.infotoast.lorax.Lorax;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;

public class DatapackDecompressor implements Listener {
    private final Lorax plugin;

    public DatapackDecompressor(Lorax plugin) {
        this.plugin = plugin;
    }

    private void extract() {
        File worldFolder = plugin.getServer().getWorldContainer();
        String worldName = plugin.getServer().getWorlds().get(0).getName();
        String datapackFolder = worldFolder.getAbsolutePath() + File.separator + worldName + File.separator + "datapacks" + File.separator;
        File dataPackFile = new File(datapackFolder, "EasyTrees.zip");
        if (!dataPackFile.exists()) {
            plugin.getBukkitLogger().info("Extracting trees datapack!");
            copy("EasyTrees.zip", dataPackFile);
        }
    }

    private void copy(String fileName, File file) {
        InputStream is = null;
        try {
            is = plugin.getResource(fileName);
            OutputStream o = Files.newOutputStream(file.toPath());
            byte[] buf = new byte[1024];
            int len;
            try {
                while ((len = is.read(buf)) > 0) {
                    o.write(buf, 0, len);
                }
            } catch (IOException ex) {
                plugin.getBukkitLogger().severe("Could not copy datapack file!");
            } finally {
                try {
                    o.close();
                } catch (IOException e) {
                    plugin.getBukkitLogger().severe("Could not close datapack output stream.");
                }
            }
        } catch (IOException ex) {
            plugin.getBukkitLogger().severe("Could not find datapack file!");
        } finally {
            if (is != null)
                try {
                    is.close();
                } catch (IOException ex) {
                    plugin.getBukkitLogger().severe("Could not close datapack input stream.");
                }
        }
    }
    public void onWorldInit() {
        extract();
        plugin.getServer().reloadData();
        plugin.getServer().getDatapackManager().getPacks().stream().forEach((d) -> {
            if (d.getName().contains("EasyTrees.zip")) {
                d.setEnabled(true);
            }
        });
    }
}
