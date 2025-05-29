package org.infotoast.lorax;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.LimitedRegion;
import org.bukkit.generator.WorldInfo;
import org.infotoast.lorax.customobject.ObjectPlacer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Populator extends BlockPopulator {
    private List<Material> okMats = new ArrayList<>();
    private List<Material> notOkMats = new ArrayList<>();
    public Populator() {
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
    @Override
    public void populate(WorldInfo world, Random random, int x, int z, LimitedRegion source) {
        WorldImproved worldi = WorldImproved.get(world);
        if (world.getEnvironment().equals(World.Environment.NORMAL)) doBiome(worldi, random, x, z, source);
        ObjectPlacer.placeObjectsInChunk(x, z);
    }

    private void doBiome(WorldImproved world, Random random, int x, int z, LimitedRegion chunk) {
        List<Biome> biomesToTransform = world.getBiome(x, z, chunk);
        TreeSpawner spawner = new TreeSpawner(world, x, z);
        for (int i = 0; i < biomesToTransform.size(); i++) {
            switch (biomesToTransform.get(i).translationKey()) {
                case "biome.minecraft.birch_forest":
                case "biome.minecraft.old_growth_birch_forest":
                    spawner.doTreePlacement(TreeType.BIRCH);
                    break;
                case "biome.minecraft.forest":
                case "biome.minecraft.flower_forest":
                    spawner.doTreePlacement(TreeType.OAK);
                    break;
                case "biome.minecraft.taiga":
                case "biome.minecraft.grove":
                case "biome.minecraft.snowy_taiga":
                    spawner.doTreePlacement(TreeType.SPRUCE);
                    break;
                case "biome.minecraft.savanna":
                case "biome.minecraft.savanna_plateau":
                    spawner.doTreePlacement(TreeType.ACACIA);
                    break;
                case "biome.minecraft.dark_forest":
                    spawner.doTreePlacement(TreeType.DARK_OAK);
            }
        }

        spawner.finalizeObjects();
    }
}