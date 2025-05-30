package org.infotoast.lorax.customobject.creator;

import com.sk89q.worldedit.math.Vector3;
import com.sk89q.worldedit.regions.CuboidRegion;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.data.BlockData;
import org.infotoast.lorax.customobject.CustomObject;
import org.infotoast.lorax.customobject.datatype.CustomObjectBlock;
import org.infotoast.lorax.customobject.datatype.CustomObjectItem;
import org.infotoast.lorax.customobject.datatype.ObjectLocation;

import java.util.ArrayList;

public class CustomObjectCreator {
    int minX;
    int maxX;
    int minY;
    int maxY;
    int minZ;
    int maxZ;
    int centerX;
    int centerY;
    int centerZ;
    public CustomObjectCreator(CuboidRegion boundingBox, Vector3 center) {
        this.minX = boundingBox.getMinimumPoint().x();
        this.minZ = boundingBox.getMinimumPoint().z();
        this.minY = boundingBox.getMinimumY();
        this.maxX = boundingBox.getMaximumPoint().x();
        this.maxZ = boundingBox.getMaximumPoint().z();
        this.maxY = boundingBox.getMaximumY();
        this.centerX = (int)Math.floor(center.x());
        this.centerZ = (int)Math.floor(center.z());
        this.centerY = boundingBox.getMinimumY();
    }

    private ArrayList<CustomObjectBlock> readBlocks(World world) {
        ArrayList<CustomObjectBlock> blocks = new ArrayList<>();
        for (int x = minX; x <= maxX; x++) {
            for (int z = minZ; z <= maxZ; z++) {
                for (int y = minY; y <= maxY; y++) {
                    BlockData block = world.getBlockAt(x, y, z).getBlockData();
                    if (block.getMaterial() != Material.AIR) {
                        int objectX = x - centerX;
                        int objectZ = z - centerZ;
                        int objectY = y - centerY;
                        blocks.add(new CustomObjectBlock(new ObjectLocation(objectX, objectY, objectZ), block));
                    }
                }
            }
        }
        return blocks;
    }

    public CustomObject create(String name, World world) {
        ArrayList<CustomObjectItem> items = new ArrayList<>();
        items.addAll(readBlocks(world));
        return new CustomObject(name, items, new ArrayList<>());
    }
}
