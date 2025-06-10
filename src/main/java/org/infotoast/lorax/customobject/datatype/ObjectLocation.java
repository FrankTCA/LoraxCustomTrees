package org.infotoast.lorax.customobject.datatype;

public class ObjectLocation {
    private int x;
    private int y;
    private int z;
    public ObjectLocation(int x, int y, int z) {
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

    public int getDistanceFromCenterX(ObjectLocation center) {
        return Math.abs(x - center.getX());
    }

    public int getDistanceFromCenterY(ObjectLocation center) {
        return Math.abs(y - center.getY());
    }

    public int getDistanceFromCenterZ(ObjectLocation center) {
        return Math.abs(z - center.getZ());
    }

    public ObjectLocation getLocationRotated(int rotation) {
        switch (rotation) {
            case 0:
                return this;
            case 1:
                return new ObjectLocation(-x, y, z);
            case 2:
                return new ObjectLocation(-x, y, -z);
            case 3:
                return new ObjectLocation(x, y, -z);
            default:
                throw new IllegalArgumentException("Rotation must be between 0 and 3");
        }
    }

    public ObjectLocation getObjectLocationFromCenter(ObjectLocation center) {
        return new ObjectLocation(center.getX() + x, center.getY() + y, center.getZ() + z);
    }

    @Override
    public String toString() {
        return x + "," + y + "," + z;
    }
}
