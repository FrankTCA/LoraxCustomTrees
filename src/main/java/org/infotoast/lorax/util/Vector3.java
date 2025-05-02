package org.infotoast.lorax.util;

import org.bukkit.Location;
import org.infotoast.lorax.WorldImproved;

public class Vector3 {
    int x;
    int y;
    int z;
    public Vector3(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

    public Location getAsLocation(WorldImproved world) {
        return new Location(world.getWorld(), x, y, z);
    }
}