package org.infotoast.lorax;

import org.bukkit.Chunk;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldInitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.nio.file.Files;
import java.util.Random;

public final class Lorax extends JavaPlugin {
    private final Populator pop = new Populator();
    @Override
    public void onEnable() {
        // Plugin startup logic
        System.out.println("Starting Lorax Engine...");
        getServer().getPluginManager().registerEvents(new WorldListener(), this);
    }

    private void extract() {
        File worldFolder = getServer().getWorldContainer();
        String worldName = getServer().getWorlds().get(0).getName();
        String datapackFolder = worldFolder.getAbsolutePath() + File.separator + worldName + File.separator + "datapacks" + File.separator;
        File dataPackFile = new File(datapackFolder, "EasyTrees.zip");
        if (!dataPackFile.exists()) {
            System.out.println("Extracting trees datapack!");
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
                System.err.println("Could not copy datapack file!");
            } finally {
                try {
                    o.close();
                } catch (IOException e) {
                    System.err.println("Could not close datapack output stream.");
                }
            }
        } catch (IOException ex) {
            System.err.println("Could not find datapack file!");
        } finally {
            if (is != null)
                try {
                    is.close();
                } catch (IOException ex) {
                    System.err.println("Could not close datapack input stream.");
                }
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (label.equals("loraxmedaddy")) {
            if (sender.hasPermission("lorax.loraxmedaddy")) {
                if (sender instanceof Player) {
                    Chunk chunk = ((Player) sender).getLocation().getChunk();
                    pop.populate(((Player) sender).getWorld(), new Random(), chunk);
                    sender.sendMessage("§bChunk has been loraxed!");
                    ConsoleCommandSender console = getServer().getConsoleSender();
                    console.sendMessage("[Lorax] §bPlayer §2" + ((Player) sender).getDisplayName() + "§b has loraxed chunk §3(" + chunk.getX() + ", " + chunk.getZ() + ")");
                    return true;
                }
                sender.sendMessage("§4Must be run as a player.");
                return false;
            }
            sender.sendMessage("§4Permission denied.");
        }
        return false;
    }

    private class WorldListener implements Listener {
        @EventHandler(priority= EventPriority.LOW)
        public void onWorldInit(WorldInitEvent evt) {
            extract();
            getServer().reloadData();
            getServer().getDatapackManager().getPacks().stream().forEach((d) -> {
                System.out.println(d.getName());
                if (d.getName().contains("EasyTrees.zip")) {
                    System.out.println("The datapack:" + d.getName());
                    d.setEnabled(true);
                }
            });
            evt.getWorld().getPopulators().add(pop);
        }
    }
}