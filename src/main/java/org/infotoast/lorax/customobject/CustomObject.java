package org.infotoast.lorax.customobject;

import org.infotoast.lorax.WorldImproved;
import org.infotoast.lorax.customobject.datatype.CustomObjectBlock;
import org.infotoast.lorax.customobject.datatype.CustomObjectCheck;
import org.infotoast.lorax.customobject.datatype.CustomObjectItem;
import org.infotoast.lorax.customobject.datatype.ObjectLocation;

import java.util.ArrayList;

public class CustomObject {
    protected final String objectName;
    protected final ArrayList<CustomObjectItem> items;
    protected final ArrayList<CustomObjectCheck> checks;
    protected final CustomObjectSettings settings;

    public CustomObject(String objectName, ArrayList<CustomObjectItem> items, ArrayList<CustomObjectCheck> checks, CustomObjectSettings settings) {
        this.objectName = objectName;
        this.items = items;
        this.checks = checks;
        this.settings = settings;
    }

    public String getObjectName() {
        return objectName;
    }

    public ArrayList<CustomObjectItem> getItems() {
        return items;
    }

    public CustomObjectSettings getSettings() {
        return settings;
    }

    public PlacedObject getPlacedObject(ObjectLocation centerLocation, int rotation, WorldImproved world) {
        return new PlacedObject(objectName, settings, items, checks, centerLocation, rotation, world);
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder(settings + "\n\n");
        for (CustomObjectItem item : items) {
            str.append(item).append("\n");
        }
        return str.toString();
    }
}
