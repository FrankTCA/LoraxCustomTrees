package org.infotoast.lorax.customobject;

import org.infotoast.lorax.customobject.exception.CustomObjectLoadingFailedException;
import org.infotoast.lorax.customobject.exception.InvalidObjectCommandException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

public class CustomObjectLoader {
    private final Map<String, CustomObject> customObjects = new HashMap<>();

    public CustomObjectLoader() {
        try {
            loadObjects();
        } catch (CustomObjectLoadingFailedException e) {
            e.printStackTrace();
        }
    }

    public void loadCustomObject(String name, String fileName) throws CustomObjectLoadingFailedException {
        try {
            ObjectFile customObjectFile = new ObjectFile(name, fileName);
            customObjects.put(name, CustomObjectReader.read(customObjectFile));
        } catch (URISyntaxException e) {
            e.printStackTrace();
            throw new CustomObjectLoadingFailedException("Failed to load custom object.");
        } catch (InvalidObjectCommandException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public CustomObject getCustomObject(String name) {
        return customObjects.get(name);
    }

    public void loadObjects() throws CustomObjectLoadingFailedException {
    }
}
