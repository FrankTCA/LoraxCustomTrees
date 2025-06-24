package org.infotoast.lorax.customobject;

import org.infotoast.lorax.customobject.exception.CustomObjectLoadingFailedException;
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
        }
    }

    public CustomObject getCustomObject(String name) {
        return customObjects.get(name);
    }

    public void loadObjects() throws CustomObjectLoadingFailedException {
        loadCustomObject("smallOak1", "smallOak1.bo5");
        loadCustomObject("smallOak2", "smallOak2.bo5");
        loadCustomObject("smallOak3", "smallOak3.bo5");
        loadCustomObject("mediumOak1", "mediumOak1.bo5");
        loadCustomObject("tallOak1", "tallOak1.bo5");
        loadCustomObject("tallOak2", "tallOak2.bo5");
        loadCustomObject("greatOak1", "greatOak1.bo5");
        loadCustomObject("greatOak2", "greatOak2.bo5");
        loadCustomObject("greatOak3", "greatOak3.bo5");

        loadCustomObject("greatBirch1", "greatBirch1.bo5");
        loadCustomObject("greatBirch2", "greatBirch2.bo5");
        loadCustomObject("smallBirch1", "smallBirch1.bo5");
        loadCustomObject("smallBirch2", "smallBirch2.bo5");
        loadCustomObject("tallBirch1", "tallBirch1.bo5");
        loadCustomObject("tallBirch2", "tallBirch2.bo5");

        loadCustomObject("smallSpruce1",  "smallSpruce1.bo5");
        loadCustomObject("tallSpruce1",  "tallSpruce1.bo5");
        loadCustomObject("tallSpruce2", "tallSpruce2.bo5");
        loadCustomObject("tallSpruce3", "tallSpruce3.bo5");
        loadCustomObject("tallSpruce4", "tallSpruce4.bo5");
        loadCustomObject("tallSpruce5", "tallSpruce5.bo5");

        loadCustomObject("greatAcacia1", "greatAcacia1.bo5");
        loadCustomObject("greatAcacia2", "greatAcacia2.bo5");
        loadCustomObject("mediumAcacia1", "mediumAcacia1.bo5");
        loadCustomObject("mediumAcacia2", "mediumAcacia2.bo5");
        loadCustomObject("mediumAcacia3", "mediumAcacia3.bo5");
        loadCustomObject("mediumAcacia4", "mediumAcacia4.bo5");
        loadCustomObject("tallAcacia1", "tallAcacia1.bo5");
    }
}
