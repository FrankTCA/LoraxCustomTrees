package org.infotoast.lorax.customobject;

import org.bukkit.Chunk;
import org.bukkit.Location;
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

    public static void registerObjectsInChunk(WorldImproved world, ArrayList<PlacedObject> objectsInChunk) {
        ArrayList<ChunkCoordsHash> alreadyLoadedChunksToPlace = new ArrayList<>();
        synchronized (hashMapPlacedObjects) {
            synchronized (placedObjects) {
                for (int i = 0; i < objectsInChunk.size(); i++) {
                    PlacedObject object = objectsInChunk.get(i);
                    if (object.runChecks()) {
                        placedObjects.add(object);
                        int index = placedObjects.size() - 1;
                        Chunk lowestChunk = new Location(world.getWorld(), object.getLowestRawX(), 1, object.getLowestRawZ()).getChunk();
                        Chunk highestChunk = new Location(world.getWorld(), object.getHighestRawX(), 1, object.getHighestRawZ()).getChunk();
                        for (int x = lowestChunk.getX(); x <= highestChunk.getX(); x++) {
                            for (int z = lowestChunk.getZ(); z <= highestChunk.getZ(); z++) {
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

    public static void placeObjectsInChunk(int chunkX, int chunkZ) {
        synchronized (hashMapPlacedObjects) {
            synchronized (placedObjects) {
                ChunkCoordsHash hash = new ChunkCoordsHash(chunkX, chunkZ);
                ArrayList<Integer> objectsInChunkIndex = hashMapPlacedObjects.getOrDefault(hash, new ArrayList<>());
                for (int i = 0; i < objectsInChunkIndex.size(); i++) {
                    Integer index = objectsInChunkIndex.get(i);
                    PlacedObject object = placedObjects.get(index);
                    object.placeByChunk(chunkX, chunkZ);
                }
            }
        }
    }
}
