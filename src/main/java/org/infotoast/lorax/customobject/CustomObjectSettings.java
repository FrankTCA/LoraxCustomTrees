package org.infotoast.lorax.customobject;

public class CustomObjectSettings {
    private boolean rotateRandomly = true;
    private int minHeight = 63;
    private int maxHeight = 318;
    private String author = "Lorax Object Builder";
    private String description = "A BO5 Object";
    private int version = 1;
    public CustomObjectSettings() {
    }

    public void setRotateRandomly(boolean rotateRandomly) {
        this.rotateRandomly = rotateRandomly;
    }

    public void setMinHeight(int minHeight) {
        this.minHeight = minHeight;
    }

    public void setMaxHeight(int maxHeight) {
        this.maxHeight = maxHeight;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public boolean isRotateRandomly() {
        return rotateRandomly;
    }

    public int getMinHeight() {
        return minHeight;
    }

    public int getMaxHeight() {
        return maxHeight;
    }

    public String getAuthor() {
        return author;
    }

    public String getDescription() {
        return description;
    }

    public int getVersion() {
        return version;
    }

    @Override
    public String toString() {
        String str = "Author: " + author + "\n";
        str += "Version: " + version + "\n";
        str += "Description: " + description + "\n";
        str += "MinHeight: " + minHeight + "\n";
        str += "MaxHeight: " + maxHeight + "\n";
        str += "RotateRandomly: " + rotateRandomly + "\n";
        return str;
    }
}
