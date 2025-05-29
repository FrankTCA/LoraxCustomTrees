package org.infotoast.lorax;

import org.infotoast.lorax.customobject.CustomObject;

import java.util.Random;

public class TreeHolder {
    private static final CustomObject[] birch = {

    };
    private static final CustomObject[] oak = {

    };

    private static final CustomObject[] spruce = {

    };

    private static final CustomObject[] jungle = {

    };

    private static final CustomObject[] acacia = {

    };

    private static final CustomObject[] darkOak = {

    };

    static CustomObject getTree(Random rand, TreeType type) {
        switch (type) {
            case BIRCH:
                return birch[rand.nextInt(birch.length)];
            case OAK:
                return oak[rand.nextInt(oak.length)];
            case SPRUCE:
                return spruce[rand.nextInt(spruce.length)];
            case JUNGLE:
                return jungle[rand.nextInt(jungle.length)];
            case ACACIA:
                return acacia[rand.nextInt(acacia.length)];
            case DARK_OAK:
                return darkOak[rand.nextInt(darkOak.length)];
            default:
                return null;
        }
    }
}
