package org.infotoast.lorax.customobject.datatype;

import org.bukkit.generator.LimitedRegion;
import org.infotoast.lorax.WorldImproved;
import org.infotoast.lorax.customobject.exception.CheckMissedException;

public abstract class CustomObjectCheck implements CustomObjectItem {
    protected final ObjectLocation location;
    private boolean checkCache;
    protected boolean checkCacheSet;

    public CustomObjectCheck(ObjectLocation location) {
        this.location = location;
    }

    @Override
    public abstract String getName();

    @Override
    public ObjectLocation getLocation() {
        return location;
    }

    protected void setCheckCache(boolean checkCache) {
        this.checkCache = checkCache;
        this.checkCacheSet = true;
    }

    protected boolean getCheckCache() {
        return checkCache;
    }

    public abstract boolean check(ObjectLocation center, WorldImproved world, LimitedRegion reg);

    public boolean check(ObjectLocation center, WorldImproved world) {
        // Always return true because we always want to have all checks return true with /lorax spawn
        return true;
    }

    public void place(ObjectLocation center, int rotation, WorldImproved world, LimitedRegion reg) {
        if (!check(center, world, reg)) {
            throw new CheckMissedException(getName() + " check was not run and the game is attempting to load invalid data. Stopping server.");
        }
    }

    public void place(ObjectLocation center, int rotation, WorldImproved world) {
        if (!check(center, world)) {
            throw new CheckMissedException(getName() + " check was not run and the game is attempting to load invalid data. Stopping server.");
        }
    }
}
