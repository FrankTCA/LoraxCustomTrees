package org.infotoast.lorax;

import org.infotoast.lorax.customobject.CustomObject;

import java.util.Random;

public class TreeHolder {
    private static final CustomObject[] birch = {
            Lorax.loader.getCustomObject("greatBirch1"),
            Lorax.loader.getCustomObject("greatBirch2"),
            Lorax.loader.getCustomObject("smallBirch1"),
            Lorax.loader.getCustomObject("smallBirch2"),
            Lorax.loader.getCustomObject("tallBirch1"),
            Lorax.loader.getCustomObject("tallBirch2")
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
            Lorax.loader.getCustomObject("smallSpruce1"),
            Lorax.loader.getCustomObject("tallSpruce1"),
            Lorax.loader.getCustomObject("tallSpruce2"),
            Lorax.loader.getCustomObject("tallSpruce3"),
            Lorax.loader.getCustomObject("tallSpruce4"),
            Lorax.loader.getCustomObject("tallSpruce5")
    };

    private static final CustomObject[] jungle = {
            Lorax.loader.getCustomObject("largeJungle1"),
            Lorax.loader.getCustomObject("smallJungle1"),
            Lorax.loader.getCustomObject("smallJungle2"),
            Lorax.loader.getCustomObject("tallJungle1"),
            Lorax.loader.getCustomObject("tallJungle2"),
            Lorax.loader.getCustomObject("tallJungle3"),
            Lorax.loader.getCustomObject("tinyJungle1"),
            Lorax.loader.getCustomObject("tinyJungle2")
    };

    private static final CustomObject[] acacia = {
            Lorax.loader.getCustomObject("greatAcacia1"),
            Lorax.loader.getCustomObject("greatAcacia2"),
            Lorax.loader.getCustomObject("mediumAcacia1"),
            Lorax.loader.getCustomObject("mediumAcacia2"),
            Lorax.loader.getCustomObject("mediumAcacia3"),
            Lorax.loader.getCustomObject("mediumAcacia4"),
            Lorax.loader.getCustomObject("tallAcacia1")
    };

    private static final CustomObject[] darkOak = {
            Lorax.loader.getCustomObject("smallDarkOak1"),
            Lorax.loader.getCustomObject("mediumDarkOak1"),
            Lorax.loader.getCustomObject("mediumDarkOak2"),
            Lorax.loader.getCustomObject("mediumDarkOak3")
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
