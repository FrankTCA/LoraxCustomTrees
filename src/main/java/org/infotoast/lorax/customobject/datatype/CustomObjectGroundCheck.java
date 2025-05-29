package org.infotoast.lorax.customobject.datatype;

import org.infotoast.lorax.WorldImproved;
import org.infotoast.lorax.util.Utility;

public class CustomObjectGroundCheck extends CustomObjectCheck {
    public CustomObjectGroundCheck(ObjectLocation location) {
        super(location);
    }

    @Override
    public boolean check(ObjectLocation center, WorldImproved world) {
        ObjectLocation actualLoc = location.getObjectLocationFromCenter(center);
        int y = Utility.getHeightAt(world, actualLoc.getX(), actualLoc.getZ());
        int checkY = actualLoc.getY();

        return checkY <= y;
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
}
