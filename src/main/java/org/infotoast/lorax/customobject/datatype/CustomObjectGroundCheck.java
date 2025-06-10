package org.infotoast.lorax.customobject.datatype;

import org.bukkit.generator.LimitedRegion;
import org.infotoast.lorax.WorldImproved;
import org.infotoast.lorax.util.Utility;

public class CustomObjectGroundCheck extends CustomObjectCheck {
    public CustomObjectGroundCheck(ObjectLocation location) {
        super(location);
    }

    @Override
    public boolean check(ObjectLocation center, WorldImproved world, LimitedRegion reg) {
        if (checkCacheSet) {
            return getCheckCache();
        }
        ObjectLocation actualLoc = location.getObjectLocationFromCenter(center);
        int y;
        if (reg.isInRegion(actualLoc.getX(), actualLoc.getY(), actualLoc.getZ())) {
            y = Utility.getHeightAt(world, actualLoc.getX(), actualLoc.getZ(), reg);
        } else {
            y = Utility.getHeightAt(world, actualLoc.getX(), actualLoc.getZ());
        }
        int checkY = actualLoc.getY();

        boolean answer = checkY <= y;
        setCheckCache(answer);
        return answer;
    }

    @Override
    public String getName() {
        return "GroundCheck";
    }

    public static String[] getKeywords() {
        return new String[]{"GroundCheck", "GC"};
    }

    public static String getRESyntax() {
        return "G.*\\(-?[0-9]+,-?[0-9]+,-?[0-9]+\\)";
    }

    @Override
    public String toString() {
        return "GC(" + location + ")";
    }
}
