package org.infotoast.lorax.util;

import org.bukkit.Material;

public class RandomMaterial {
    Material mat;
    int firstChance;
    int lastChance;
    public RandomMaterial(Material mat, int firstChance, int lastChance) {
        this.mat = mat;
        this.firstChance = firstChance;
        this.lastChance = lastChance;
    }

    public Material getMaterial() {
        return mat;
    }

    public boolean isYes(int n) {
        return (n >= firstChance && n <= lastChance);
    }
}
