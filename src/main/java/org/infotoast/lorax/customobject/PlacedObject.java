package org.infotoast.lorax.customobject;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.infotoast.lorax.WorldImproved;
import org.infotoast.lorax.customobject.datatype.CustomObjectCheck;
import org.infotoast.lorax.customobject.datatype.CustomObjectItem;
import org.infotoast.lorax.customobject.datatype.ObjectLocation;
import org.infotoast.lorax.util.ChunkCoordsHash;
import org.infotoast.lorax.util.Utility;

import java.util.ArrayList;

public class PlacedObject extends CustomObject {
    private final ObjectLocation center;
    private final int rotation;
    private boolean placed;
    private WorldImproved world;
    private ArrayList<ChunkCoordsHash> alreadyPlaced = new ArrayList<>();
    public PlacedObject(String objectName, ArrayList<CustomObjectItem> items, ArrayList<CustomObjectCheck> checks, ObjectLocation centerLocation, int rotation, WorldImproved world) {
        super(objectName, items, checks);
        this.center = centerLocation;
        this.rotation = rotation;
        this.world = world;
        this.placed = false;
    }

    public void placeAll() {
        for (CustomObjectItem item : items) {
            item.place(center, rotation, world);
        }
        placed = true;
    }

    public void placeByChunk(int chunkX, int chunkZ) {
        for (CustomObjectItem item : items) {
            ObjectLocation loc = item.getLocation().getObjectLocationFromCenter(center);
            if (Utility.isLocationInChunk(loc.getX(), loc.getZ(), chunkX, chunkZ)) {
                item.place(center, rotation, world);
            }
        }
        alreadyPlaced.add(new ChunkCoordsHash(chunkX, chunkZ));

        Chunk lowestChunk = new Location(world.getWorld(), getLowestRawX(), 1, getLowestRawZ()).getChunk();
        Chunk highestChunk = new Location(world.getWorld(), getHighestRawX(), 1, getHighestRawZ()).getChunk();
        for (int x = lowestChunk.getX(); x <= highestChunk.getX(); x++) {
            for (int z = lowestChunk.getZ(); z <= highestChunk.getZ(); z++) {
                ChunkCoordsHash chunkCoordsHash = new ChunkCoordsHash(x, z);
                if (ObjectPlacer.alreadyLoadedChunks.contains(chunkCoordsHash) && !alreadyPlaced.contains(chunkCoordsHash)) {
                    placeByChunk(x, z);
                }
            }
        }
    }

    public boolean runChecks() {
        for (CustomObjectCheck check : checks) {
            if (!check.check(center, world)) {
                return false;
            }
        }
        return true;
    }

    public int getHighestX() {
        int highestX = 0;
        for (CustomObjectItem item : items) {
            ObjectLocation loc = item.getLocation();
            int x = loc.getX();
            if (x > highestX) {
                highestX = x;
            }
        }
        return highestX;
    }

    public int getLowestX() {
        int lowestX = 0;
        for (CustomObjectItem item : items) {
            ObjectLocation loc = item.getLocation();
            int x = loc.getX();
            if (x < lowestX) {
                lowestX = x;
            }
        }
        return lowestX;
    }

    public int getHighestZ() {
        int highestZ = 0;
        for (CustomObjectItem item : items) {
            ObjectLocation loc = item.getLocation();
            int z = loc.getZ();
            if (z > highestZ) {
                highestZ = z;
            }
        }
        return highestZ;
    }

    public int getLowestZ() {
        int lowestZ = 0;
        for (CustomObjectItem item : items) {
            ObjectLocation loc = item.getLocation();
            int z = loc.getZ();
            if (z < lowestZ) {
                lowestZ = z;
            }
        }
        return lowestZ;
    }

    public int getDiameterX() {
        int highestX = 0;
        int lowestX = 0;
        for (CustomObjectItem item : items) {
            int itemX = item.getLocation().getX();
            if (itemX > highestX) {
                highestX = itemX;
            } else if (itemX < lowestX) {
                lowestX = itemX;
            }
        }
        return highestX - lowestX;
    }

    public int getDiameterZ() {
        int highestZ = 0;
        int lowestZ = 0;
        for (CustomObjectItem item : items) {
            int itemZ = item.getLocation().getZ();
            if (itemZ > highestZ) {
                highestZ = itemZ;
            } else if (itemZ < lowestZ) {
                lowestZ = itemZ;
            }
        }
        return highestZ - lowestZ;
    }

    public int getHighestRawX() {
        return center.getX() + Math.abs(getHighestX());
    }

    public int getLowestRawX() {
        return center.getX() - Math.abs(getLowestX());
    }

    public int getHighestRawZ() {
        return center.getZ() + Math.abs(getHighestZ());
    }

    public int getLowestRawZ() {
        return center.getZ() - Math.abs(getLowestZ());
    }

    public ObjectLocation getLocation() {
        return center;
    }

    public int getRotation() {
        return rotation;
    }

    public boolean isPlaced() {
        return placed;
    }

    @Override
    public String toString() {
        return "PlacedObject: " + getObjectName() + " at " + center;
    }
}
