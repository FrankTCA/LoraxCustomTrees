package org.infotoast.lorax;

import de.tr7zw.changeme.nbtapi.NBTContainer;
import de.tr7zw.changeme.nbtapi.NBTEntity;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.generator.WorldInfo;
import org.bukkit.scheduler.BukkitScheduler;
import org.infotoast.lorax.util.Vector3;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ChunkBasedPopulator {
    private List<Material> okMats = new ArrayList<>();
    private List<Material> notOkMats = new ArrayList<>();
    public ChunkBasedPopulator() {
        okMats.add(Material.AIR);
        okMats.add(Material.OAK_LEAVES);
        okMats.add(Material.OAK_LOG);
        okMats.add(Material.BIRCH_LEAVES);
        okMats.add(Material.BIRCH_LOG);
        okMats.add(Material.SPRUCE_LEAVES);
        okMats.add(Material.SPRUCE_LOG);
        okMats.add(Material.ACACIA_LEAVES);
        okMats.add(Material.ACACIA_LOG);
        okMats.add(Material.DARK_OAK_LEAVES);
        okMats.add(Material.DARK_OAK_LOG);
        okMats.add(Material.SNOW);
        notOkMats.add(Material.WATER);
        notOkMats.add(Material.LAVA);
        notOkMats.add(Material.SAND);
        notOkMats.add(Material.STONE);
    }
    public void populate(WorldInfo world, Random random, int x, int z, Chunk source) {
        WorldImproved worldi = WorldImproved.get(world);
        if (world.getEnvironment().equals(World.Environment.NORMAL)) doBiome(worldi, random, x, z, source);
    }

    private void spawnTree(WorldImproved world, Vector3 v3, int treeType) {
        Location loc = v3.getAsLocation(world);
        if (Lorax.getPlugin(Lorax.class).isEnabled()) {
            BukkitScheduler scheduler = Lorax.getPlugin(Lorax.class).getServer().getScheduler();
            scheduler.runTask(Lorax.getPlugin(Lorax.class), () -> {
                Entity as = world.getWorld().spawnEntity(loc, EntityType.AREA_EFFECT_CLOUD);
                NBTEntity ent = new NBTEntity(as);
                ent.mergeCompound(new NBTContainer("{Duration:10,Radius:0,Tags:[\"sky_planted\",\"sky_template" + treeType + "\",\"sky_small\"]}"));
            });
        }
    }

    private void doBiome(WorldImproved world, Random random, int x, int z, Chunk source) {
        List<Biome> biomesToTransform = world.getBiome(okMats, source);
        for (int i = 0; i < biomesToTransform.size(); i++) {
            switch (biomesToTransform.get(i).translationKey()) {
                case "biome.minecraft.birch_forest":
                case "biome.minecraft.old_growth_birch_forest":
                    doBirch(world, random, x, z, source);
                    break;
                case "biome.minecraft.forest":
                case "biome.minecraft.flower_forest":
                    doOak(world, random, x, z, source);
                    break;
                case "biome.minecraft.taiga":
                case "biome.minecraft.grove":
                case "biome.minecraft.snowy_taiga":
                    doSpruce(world, random, x, z, source);
                    break;
                case "biome.minecraft.savanna":
                case "biome.minecraft.savanna_plateau":
                    doAcacia(world, random, x, z, source);
                    break;
                case "biome.minecraft.dark_forest":
                    doDarkOak(world, random, x, z, source);
            }
        }
    }

    private Vector3 getRandLocation(WorldImproved world, Random random, int x, int z, Chunk source) {
        int X = random.nextInt(15);
        int rawX = x << 4 + X;
        int Z = random.nextInt(15);
        int rawZ = z << 4 + Z;
        int Y;
        for (Y = world.getWorld().getMaxHeight()-1; okMats.contains(source.getBlock(X, Y, Z).getType()) && Y > 0; Y--);
        if (notOkMats.contains(source.getBlock(X, Y, Z).getType()))
            return null;
        if (Y < 1) return null;
        return new Vector3(rawX, Y, rawZ);
    }

    private void doBirch(WorldImproved world, Random random, int x, int z, Chunk source) {
        int amount = random.nextInt(4)+1;
        for (int i = 0; i < amount; i++) {
            Vector3 v3 = getRandLocation(world, random, x, z, source);
            if (v3 == null) continue;
            int birchType = random.nextInt(3);
            int birchValue = 19;
            if (birchType == 1) {
                birchValue = 20;
            } else if (birchType == 2) {
                birchValue = 39;
            }
            spawnTree(world, v3, birchValue);
        }
    }

    private void doOak(WorldImproved world, Random random, int x, int z, Chunk source) {
        int amount = random.nextInt(4)+1;
        for (int i = 0; i < amount; i++) {
            Vector3 v3 = getRandLocation(world, random, x, z, source);
            if (v3 == null) continue;
            int birchType = random.nextInt(9);
            int birchValue = 19;
            if (birchType == 1) {
                birchValue = 20;
            } else if (birchType == 2) {
                birchValue = 39;
            } else if (birchType == 3) {
                birchValue = 42;
            } else if (birchType == 4) {
                birchValue = 10;
            } else if (birchType == 5) {
                birchValue = 72;
            } else if (birchType == 7) {
                birchValue = 19;
            } else if (birchType == 8) {
                birchValue = 12;
            }
            spawnTree(world, v3, birchValue);
        }
    }

    private void doSpruce(WorldImproved world, Random random, int x, int z, Chunk source) {
        int amount = random.nextInt(4)+1;
        for (int i = 0; i < amount; i++) {
            Vector3 v3 = getRandLocation(world, random, x, z, source);
            if (v3 == null) continue;
            int birchType = random.nextInt(3);
            int birchValue = 49;
            if (birchType == 1) {
                birchValue = 51;
            } else if (birchType == 2) {
                birchValue = 7;
            }
            spawnTree(world, v3, birchValue);
        }
    }

    private void doAcacia(WorldImproved world, Random random, int x, int z, Chunk source) {
        if (random.nextBoolean()) {
            Vector3 v3 = getRandLocation(world, random, x, z, source);
            if (v3 == null) return;
            int birchType = random.nextInt(4);
            int birchValue = 60;
            if (birchType == 1) {
                birchValue = 81;
            } else if (birchType == 2) {
                birchValue = 85;
            } else if (birchType == 3) {
                birchValue = 84;
            }
            spawnTree(world, v3, birchValue);
        }
    }

    private void doDarkOak(WorldImproved world, Random random, int x, int z, Chunk source) {
        int amount = random.nextInt(4)+1;
        for (int i = 0; i < amount; i++) {
            Vector3 v3 = getRandLocation(world, random, x, z, source);
            if (v3 == null) continue;
            int birchType = random.nextInt(5);
            int birchValue = 41;
            if (birchType == 1) {
                birchValue = 43;
            } else if (birchType == 2) {
                birchValue = 44;
            } else if (birchType == 3) {
                birchValue = 63;
            } else if (birchType == 4) {
                birchValue = 36;
            }
            spawnTree(world, v3, birchValue);
        }
    }
}
