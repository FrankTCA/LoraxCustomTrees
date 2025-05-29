package org.infotoast.lorax.customobject;

import java.net.URISyntaxException;

public class ObjectFile {
    private final String name;
    private final String fileName;

    public ObjectFile(String name, String fileName) throws URISyntaxException {
        this.name = name;
        this.fileName = fileName;
    }

    public String getName() {
        return name;
    }

    public String getFileName() {
        return fileName;
    }
}
