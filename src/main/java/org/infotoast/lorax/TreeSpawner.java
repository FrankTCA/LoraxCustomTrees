package org.infotoast.lorax;


import org.infotoast.lorax.customobject.CustomObject;
import org.infotoast.lorax.customobject.ObjectPlacer;
import org.infotoast.lorax.customobject.PlacedObject;
import org.infotoast.lorax.customobject.datatype.ObjectLocation;
import org.infotoast.lorax.util.Utility;
import org.infotoast.lorax.util.Vector3;

import java.util.ArrayList;
import java.util.Random;

public class TreeSpawner {
    private Random rand;
    private WorldImproved world;
    private int chunkX;
    private int chunkZ;
    ArrayList<PlacedObject> objectsInChunk = new ArrayList<PlacedObject>();
    public TreeSpawner(WorldImproved world, int chunkX, int chunkZ) {
        long seed = world.getSeed();
        this.rand = world.getHashedRand(seed, chunkX, chunkZ);
        this.world = world;
        this.chunkX = chunkX;
        this.chunkZ = chunkZ;
    }

    private Vector3 getRandLocation() {
        int X = rand.nextInt(15);
        int rawX = chunkX << 4 + X;
        int Z = rand.nextInt(15);
        int rawZ = chunkZ << 4 + Z;
        int Y = Utility.getHeightAt(world, rawX, rawZ);
        if (Y < 1) return null;
        return new Vector3(rawX, Y, rawZ);
    }

    public void doTreePlacement(TreeType type) {
        int amount = rand.nextInt(4)+1;
        for (int i = 0; i < amount; i++) {
            Vector3 v3 = getRandLocation();
            if (v3 == null) continue;
            ObjectLocation loc = new ObjectLocation(v3.getX(), v3.getY(), v3.getZ());
            CustomObject tree = TreeHolder.getTree(rand, type);
            if (tree == null) continue;
            PlacedObject po = tree.getPlacedObject(loc, rand.nextInt(4), world);
            objectsInChunk.add(po);
        }
    }

    public void finalizeObjects() {
        ObjectPlacer.registerObjectsInChunk(world, objectsInChunk);
        ObjectPlacer.
    }
}
