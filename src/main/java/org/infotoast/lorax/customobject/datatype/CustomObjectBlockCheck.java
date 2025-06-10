package org.infotoast.lorax.customobject.datatype;

import org.bukkit.block.data.BlockData;
import org.bukkit.generator.LimitedRegion;
import org.infotoast.lorax.WorldImproved;
import org.infotoast.lorax.customobject.creator.CustomObjectWriter;

public class CustomObjectBlockCheck extends CustomObjectCheck {
    private BlockData block;
    public CustomObjectBlockCheck(ObjectLocation location, BlockData block) {
        super(location);
        this.block = block;
    }

    @Override
    public boolean check(ObjectLocation center, WorldImproved world, LimitedRegion reg) {
        if (checkCacheSet) {
            return getCheckCache();
        }
        ObjectLocation actualLoc = location.getObjectLocationFromCenter(center);
        BlockData checkBlock;
        if (reg.isInRegion(actualLoc.getX(), actualLoc.getY(), actualLoc.getZ())) {
            checkBlock = reg.getBlockData(actualLoc.getX(), actualLoc.getY(), actualLoc.getZ());
        } else {
            checkBlock = world.getMaterial(actualLoc.getX(), actualLoc.getY(), actualLoc.getZ()).createBlockData();
        }
        boolean answer = checkBlock.getMaterial().equals(block.getMaterial());
        setCheckCache(answer);
        return answer;
    }

    @Override
    public String getName() {
        return "BlockCheck";
    }

    public static String[] getKeywords() {
        return new String[]{"BlockCheck", "BC"};
    }

    public static String getRESyntax() {
        return "B.*\\(-?[0-9]+,-?[0-9]+,-?[0-9]+,[A-Z0-9,_:=\\[\\]]+\\)";
    }

    @Override
    public String toString() {
        String blockName = CustomObjectWriter.getBlockName(block);
        return "BC(" + location + "," +  blockName + ")";
    }
}
