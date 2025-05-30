package org.infotoast.lorax.customobject.datatype;

import org.bukkit.block.data.BlockData;
import org.bukkit.generator.LimitedRegion;
import org.infotoast.lorax.WorldImproved;
import org.infotoast.lorax.customobject.exception.InvalidObjectCommandException;

public class CustomObjectRandomBlock implements CustomObjectItem {
    private BlockData[] blocks;
    private int[] chances;
    private ObjectLocation location;
    public CustomObjectRandomBlock(ObjectLocation location, BlockData[] blocks, int[] chances) throws InvalidObjectCommandException {
        if (blocks.length != chances.length) {
            throw new InvalidObjectCommandException("blocks and chances must be the same length");
        }
        this.location = location;
        this.blocks = blocks;
        this.chances = chances;
    }

    @Override
    public void place(ObjectLocation center, int rotation, WorldImproved world, LimitedRegion reg) {
        ObjectLocation loc = location.getLocationRotated(rotation);
        loc = loc.getObjectLocationFromCenter(center);
        int rand = world.getHashedRand(loc.getX(), loc.getY(), loc.getZ()).nextInt(100);
        int chanceCount = 0;
        for (int i = 0; i < blocks.length; i++) {
            chanceCount += chances[i];
            if (rand < chanceCount) {
                if (reg.isInRegion(loc.getX(), loc.getY(), loc.getZ())) {
                    reg.setBlockData(loc.getX(), loc.getY(), loc.getZ(), blocks[i]);
                } else {
                    world.setMaterial(loc.getX(), loc.getY(), loc.getZ(), blocks[i]);
                }
                return;
            }
        }
    }

    @Override
    public void place(ObjectLocation center, int rotation, WorldImproved world) {
        ObjectLocation loc = location.getLocationRotated(rotation);
        loc = loc.getObjectLocationFromCenter(center);
        int rand = world.getHashedRand(loc.getX(), loc.getY(), loc.getZ()).nextInt(100);
        int chanceCount = 0;
        for (int i = 0; i < blocks.length; i++) {
            chanceCount += chances[i];
            if (rand < chanceCount) {
                world.setMaterial(loc.getX(), loc.getY(), loc.getZ(), blocks[i]);
                return;
            }
        }
    }

    @Override
    public String getName() {
        return "RandomBlock";
    }

    @Override
    public ObjectLocation getLocation() {
        return location;
    }

    public static String[] getKeywords() {
        return new String[]{"RandomBlock", "RB"};
    }

    public static String getRESyntax() {
        return "R.*\\(-?[0-9]+,-?[0-9]+,-?[0-9]+,[A-Z0-9,_:=\\[\\]]+\\)";
    }
}
