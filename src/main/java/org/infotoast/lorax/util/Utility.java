package org.infotoast.lorax.util;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.infotoast.lorax.WorldImproved;

import java.util.ArrayList;
import java.util.Random;

public class Utility {
    private static ArrayList<Material> groundBlocks = new ArrayList<>();
    static {
        groundBlocks.add(Material.GRASS_BLOCK);
        groundBlocks.add(Material.DIRT);
        groundBlocks.add(Material.COARSE_DIRT);
        groundBlocks.add(Material.PODZOL);
        groundBlocks.add(Material.SAND);
        groundBlocks.add(Material.RED_SAND);
        groundBlocks.add(Material.GRAVEL);
        groundBlocks.add(Material.SNOW);
        groundBlocks.add(Material.SNOW_BLOCK);
        groundBlocks.add(Material.CLAY);
        groundBlocks.add(Material.STONE);
        groundBlocks.add(Material.ANDESITE);
        groundBlocks.add(Material.DIORITE);
        groundBlocks.add(Material.GRANITE);
        groundBlocks.add(Material.TUFF);
        groundBlocks.add(Material.CALCITE);
        groundBlocks.add(Material.TERRACOTTA);
        groundBlocks.add(Material.WHITE_TERRACOTTA);
        groundBlocks.add(Material.ORANGE_TERRACOTTA);
        groundBlocks.add(Material.MAGENTA_TERRACOTTA);
        groundBlocks.add(Material.LIGHT_BLUE_TERRACOTTA);
        groundBlocks.add(Material.YELLOW_TERRACOTTA);
        groundBlocks.add(Material.LIME_TERRACOTTA);
        groundBlocks.add(Material.PINK_TERRACOTTA);
        groundBlocks.add(Material.GRAY_TERRACOTTA);
        groundBlocks.add(Material.LIGHT_GRAY_TERRACOTTA);
        groundBlocks.add(Material.CYAN_TERRACOTTA);
        groundBlocks.add(Material.PURPLE_TERRACOTTA);
        groundBlocks.add(Material.BLUE_TERRACOTTA);
        groundBlocks.add(Material.BROWN_TERRACOTTA);
        groundBlocks.add(Material.GREEN_TERRACOTTA);
        groundBlocks.add(Material.RED_TERRACOTTA);
        groundBlocks.add(Material.BLACK_TERRACOTTA);
        groundBlocks.add(Material.MYCELIUM);
        groundBlocks.add(Material.SANDSTONE);
        groundBlocks.add(Material.RED_SANDSTONE);
        groundBlocks.add(Material.DIRT_PATH);
    }

    public static boolean isGroundBlock(Material block) {
        return groundBlocks.contains(block);
    }

    public static boolean isGroundBlock(Block block) {
        return groundBlocks.contains(block.getType());
    }

    public static boolean isGroundBlock(BlockData block) {
        return groundBlocks.contains(block.getMaterial());
    }

    public static Material getRandomMaterial(RandomMaterial[] r, Random rand, Material def) {
        int randInt = rand.nextInt(1000)+1;
        for (RandomMaterial mat : r) {
            if (mat.isYes(randInt)) return mat.getMaterial();
        }
        return def;
    }

    public static int randInt(Random rand, int d, int max) {
        boolean negative = false;
        if (d < 0 && max < 0) {
            negative = true;
            d = -d;
            max = -max;
        }

        if (max < d) {
            int temp = d;
            d = max;
            max = temp;
        }

        int randomNum = rand.nextInt((max - d) + 1) + d;
        return negative ? -randomNum : randomNum;
    }

    public static boolean isLocationInChunk(int x, int z, int chunkX, int chunkZ) {
        return x >> 4 == chunkX && z >> 4 == chunkZ;
    }

    public static int getHeightAt(WorldImproved world, int x, int z) {
        int Y;
        // For performance reasons we start at 256 unless the world is amplified or something else is off
        if (world.getWorld().getEnvironment() == World.Environment.NORMAL) {
            Y = 256;
        } else {
            Y = world.getWorld().getMaxHeight();
        }
        for (; Y > 30; Y--) {
            if (isGroundBlock(world.getMaterial(x, Y, z))) {
                return Y+1;
            }
        }

        return 0;
    }
}
