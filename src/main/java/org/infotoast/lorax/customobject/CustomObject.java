package org.infotoast.lorax.customobject;

import org.infotoast.lorax.WorldImproved;
import org.infotoast.lorax.customobject.datatype.CustomObjectCheck;
import org.infotoast.lorax.customobject.datatype.CustomObjectItem;
import org.infotoast.lorax.customobject.datatype.ObjectLocation;

import java.util.ArrayList;

public class CustomObject {
    protected final String objectName;
    protected final ArrayList<CustomObjectItem> items;
    protected final ArrayList<CustomObjectCheck> checks;

    public CustomObject(String objectName, ArrayList<CustomObjectItem> items, ArrayList<CustomObjectCheck> checks) {
        this.objectName = objectName;
        this.items = items;
        this.checks = checks;
    }

    public String getObjectName() {
        return objectName;
    }

    public ArrayList<CustomObjectItem> getItems() {
        return items;
    }

    public PlacedObject getPlacedObject(ObjectLocation centerLocation, int rotation, WorldImproved world) {
        return new PlacedObject(objectName, items, checks, centerLocation, rotation, world);
    }
}
