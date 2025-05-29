package org.infotoast.lorax.util;

public class ChunkCoordsHash {
    private int x;
    private int z;
    public ChunkCoordsHash(int x, int z) {
        this.x = x;
        this.z = z;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ChunkCoordsHash)) return false;
        ChunkCoordsHash that = (ChunkCoordsHash) o;
        return x == that.x && z == that.z;
    }

    @Override
    public int hashCode() {
        int hash = 31 * x + z;
        return hash;
    }

    public int getX() {
        return x;
    }

    public int getZ() {
        return z;
    }

    @Override
    public String toString() {
        return "X: " + x + ", Z: " + z;
    }
}
