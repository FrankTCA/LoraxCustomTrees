package org.infotoast.lorax.customobject;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.generator.LimitedRegion;
import org.infotoast.lorax.Lorax;
import org.infotoast.lorax.WorldImproved;
import org.infotoast.lorax.util.ChunkCoordsHash;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ObjectPlacer {
    private static final ArrayList<PlacedObject> placedObjects = new ArrayList<>();
    private static final Map<ChunkCoordsHash, ArrayList<Integer>> hashMapPlacedObjects = new HashMap<>();
    public static final ArrayList<ChunkCoordsHash> alreadyLoadedChunks = new ArrayList<>();
    public static final Object LOCK = new Object();
    public static final ArrayList<PlacedObject> queueNext = new ArrayList<>();

    public static void registerObjectsInChunk(WorldImproved world, ArrayList<PlacedObject> objectsInChunk, LimitedRegion reg) {
        ArrayList<ChunkCoordsHash> alreadyLoadedChunksToPlace = new ArrayList<>();
        synchronized (hashMapPlacedObjects) {
            synchronized (placedObjects) {
                for (int i = 0; i < objectsInChunk.size(); i++) {
                    PlacedObject object = objectsInChunk.get(i);
                    if (object.runChecks(reg)) {
                        placedObjects.add(object);
                        int index = placedObjects.size() - 1;
                        int lowestChunkX = object.getLowestRawX() >> 4;
                        int lowestChunkZ = object.getLowestRawZ() >> 4;
                        int highestChunkX = object.getHighestRawX() >> 4;
                        int highestChunkZ = object.getHighestRawZ() >> 4;
                        for (int x = lowestChunkX; x <= highestChunkX; x++) {
                            for (int z = lowestChunkZ; z <= highestChunkZ; z++) {
                                ArrayList<Integer> objs = hashMapPlacedObjects.getOrDefault(new ChunkCoordsHash(x, z), new ArrayList<>());
                                objs.add(index);
                                ChunkCoordsHash chunkCoordsHash = new ChunkCoordsHash(x, z);
                                hashMapPlacedObjects.put(chunkCoordsHash, objs);
                            }
                        }
                    }
                }
            }
        }
    }

    public static void placeObjectsInChunk(int chunkX, int chunkZ, LimitedRegion reg) {
        synchronized (hashMapPlacedObjects) {
            synchronized (placedObjects) {
                ChunkCoordsHash hash = new ChunkCoordsHash(chunkX, chunkZ);
                alreadyLoadedChunks.add(hash);
                ArrayList<Integer> objectsInChunkIndex = hashMapPlacedObjects.getOrDefault(hash, new ArrayList<>());
                for (int i = 0; i < objectsInChunkIndex.size(); i++) {
                    Integer index = objectsInChunkIndex.get(i);
                    PlacedObject object = placedObjects.get(index);
                    int lowestChunkX = object.getLowestRawX() >> 4;
                    int lowestChunkZ = object.getLowestRawZ() >> 4;
                    int highestChunkX = object.getHighestRawX() >> 4;
                    int highestChunkZ = object.getHighestRawZ() >> 4;
                    boolean allChunksLoaded = true;
                    for (int x = lowestChunkX; x <= highestChunkX; x++) {
                        for (int z = lowestChunkZ; z <= highestChunkZ; z++) {
                            ChunkCoordsHash chunkCoordsHash = new ChunkCoordsHash(x, z);
                            if (!alreadyLoadedChunks.contains(chunkCoordsHash)) {
                                allChunksLoaded = false;
                            }
                        }
                    }

                    if (allChunksLoaded) {
                        Lorax.plugin.getServer().getScheduler().scheduleSyncDelayedTask(Lorax.plugin, new Runnable() {
                            @Override
                            public void run() {
                                object.placeAll(reg);
                            }
                        }, 40);
                    }
                }
            }
        }
    }
}
