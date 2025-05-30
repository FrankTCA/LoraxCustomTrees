package org.infotoast.lorax.customobject.datatype;

import org.bukkit.generator.LimitedRegion;
import org.infotoast.lorax.WorldImproved;

public interface CustomObjectItem {
    public String getName();
    public ObjectLocation getLocation();
    public void place(ObjectLocation center, int rotation, WorldImproved world, LimitedRegion reg);
    public void place(ObjectLocation center, int rotation, WorldImproved world);
}
