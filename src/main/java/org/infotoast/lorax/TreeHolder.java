package org.infotoast.lorax;

import org.infotoast.lorax.customobject.CustomObject;

import java.util.Random;

public class TreeHolder {
    private static final CustomObject[] birch = {

    };
    private static final CustomObject[] oak = {
            Lorax.loader.getCustomObject("smallOak1"),
            Lorax.loader.getCustomObject("smallOak2"),
            Lorax.loader.getCustomObject("smallOak3"),
            Lorax.loader.getCustomObject("tallOak1"),
            Lorax.loader.getCustomObject("tallOak2"),
            Lorax.loader.getCustomObject("greatOak1"),
            Lorax.loader.getCustomObject("greatOak2"),
            Lorax.loader.getCustomObject("greatOak3"),
            Lorax.loader.getCustomObject("mediumOak1")
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
                if (birch.length == 0) {
                    return null;
                }
                return birch[rand.nextInt(birch.length)];
            case OAK:
                if (oak.length == 0) {
                    return null;
                }
                return oak[rand.nextInt(oak.length)];
            case SPRUCE:
                if (spruce.length == 0) {
                    return null;
                }
                return spruce[rand.nextInt(spruce.length)];
            case JUNGLE:
                if (jungle.length == 0) {
                    return null;
                }
                return jungle[rand.nextInt(jungle.length)];
            case ACACIA:
                if (acacia.length == 0) {
                    return null;
                }
                return acacia[rand.nextInt(acacia.length)];
            case DARK_OAK:
                if (darkOak.length == 0) {
                    return null;
                }
                return darkOak[rand.nextInt(darkOak.length)];
            default:
                return null;
        }
    }
}
