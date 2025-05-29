package org.infotoast.lorax.customobject.datatype;

import org.infotoast.lorax.WorldImproved;
import org.infotoast.lorax.customobject.exception.CheckMissedException;

public abstract class CustomObjectCheck implements CustomObjectItem {
    protected final ObjectLocation location;

    public CustomObjectCheck(ObjectLocation location) {
        this.location = location;
    }

    @Override
    public abstract String getName();

    @Override
    public ObjectLocation getLocation() {
        return location;
    }

    public abstract boolean check(ObjectLocation center, WorldImproved world);

    public void place(ObjectLocation center, int rotation, WorldImproved world) {
        if (!check(center, world)) {
            throw new CheckMissedException(getName() + " check was not run and the game is attempting to load invalid data. Stopping server.");
        }
    }
}
