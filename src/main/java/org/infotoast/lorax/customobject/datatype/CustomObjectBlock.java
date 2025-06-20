package org.infotoast.lorax.customobject.datatype;

import org.bukkit.block.data.BlockData;
import org.bukkit.generator.LimitedRegion;
import org.infotoast.lorax.WorldImproved;
import org.infotoast.lorax.customobject.creator.CustomObjectWriter;

public class CustomObjectBlock implements CustomObjectItem {
    private ObjectLocation location;
    private BlockData material;

    public CustomObjectBlock(ObjectLocation location, BlockData material) {
        this.location = location;
        this.material = material;
    }

    @Override
    public ObjectLocation getLocation() {
        return location;
    }

    public BlockData getMaterial() {
        return material;
    }

    @Override
    public void place(ObjectLocation center, int rotation, WorldImproved world, LimitedRegion reg) {
        ObjectLocation loc = location.getLocationRotated(rotation);
        loc = loc.getObjectLocationFromCenter(center);
        world.setMaterial(loc.getX(), loc.getY(), loc.getZ(), material);
        if (reg.isInRegion(loc.getX(), loc.getY(), loc.getZ())) {
            reg.setBlockData(loc.getX(), loc.getY(), loc.getZ(), material);
        } else {
            world.setMaterial(loc.getX(), loc.getY(), loc.getZ(), material);
        }
    }

    @Override
    public void place(ObjectLocation center, int rotation, WorldImproved world) {
        ObjectLocation loc = location.getLocationRotated(rotation);
        loc = loc.getObjectLocationFromCenter(center);
        world.setMaterial(loc.getX(), loc.getY(), loc.getZ(), material);
    }

    @Override
    public String getName() {
        return "Block";
    }

    public static String[] getKeywords() {
        return new String[]{"Block", "B"};
    }

    public static String getRESyntax() {
        return "B.*\\(-?[0-9]+,-?[0-9]+,-?[0-9]+,[A-Z0-9,_:=\\[\\]]+\\)";
    }

    @Override
    public String toString() {
        String materialString = CustomObjectWriter.getBlockName(material);
        return "B(" + getLocation() + "," + materialString + ")\n";
    }
}
