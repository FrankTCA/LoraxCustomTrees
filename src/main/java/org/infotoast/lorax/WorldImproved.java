package org.infotoast.lorax;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.generator.LimitedRegion;
import org.bukkit.generator.WorldInfo;

import java.util.*;

public class WorldImproved {
    public static final HashMap<String, WorldImproved> WORLDS = new HashMap<>();
    private final String worldName;
    private final long seed;

    private WorldImproved(String name, long seed) {
        this.worldName = name;
        this.seed = seed;
    }

    private WorldImproved(World world) {
        worldName = world.getName();
        seed = world.getSeed();
    }

    private WorldImproved(WorldInfo world) {
        worldName = world.getName();
        seed = world.getSeed();
    }

    public static WorldImproved get(World world) {
        return WORLDS.computeIfAbsent(world.getName(), (k) -> new WorldImproved(world));
    }

    public static WorldImproved get(WorldInfo world) {
        return WORLDS.computeIfAbsent(world.getName(), (k) -> new WorldImproved(world));
    }

    public static WorldImproved get(String name, long seed) {
        return WORLDS.computeIfAbsent(name, (k) -> new WorldImproved(name, seed));
    }

    public long getSeed() {
        return seed;
    }

    public Random getRand(long d) {
        return new Random(seed * d);
    }

    public Random getHashedRand(long x, int y, int z) {
        return new Random(Objects.hash(seed, x, y, z));
    }

    public World getWorld() {
        return Bukkit.getWorld(worldName);
    }

    public List<Biome> getBiome(int chunkX, int chunkZ, List<Material> okMats, LimitedRegion source) {
        int rawX;
        int rawZ;
        int Y;
        List biomes = new ArrayList<>();
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                rawX = chunkX * 16 + x;
                rawZ = chunkZ * 16 + z;
                for (Y = this.getWorld().getMaxHeight() - 1; okMats.contains(source.getType(rawX, Y, rawZ)) && Y > -1; Y--);
                if (Y < 1) continue;
                if (!biomes.contains(source.getBiome(rawX, Y, rawZ)))
                    biomes.add(source.getBiome(rawX,Y,rawZ));
            }
        }
        return biomes;
    }

    public List<Biome> getBiome(List<Material> okMats, Chunk source) {
        int Y;
        List biomes = new ArrayList<>();
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                for (Y = this.getWorld().getMaxHeight() - 1; okMats.contains(source.getBlock(x, Y, z).getType()) && Y > -1; Y--);
                if (Y < 1) continue;
                if (!biomes.contains(source.getBlock(x, Y, z).getBiome()))
                    biomes.add(source.getBlock(x,Y,z).getBiome());
            }
        }
        return biomes;
    }
}