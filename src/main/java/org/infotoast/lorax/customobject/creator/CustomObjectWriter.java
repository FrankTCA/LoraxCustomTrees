package org.infotoast.lorax.customobject.creator;

import org.infotoast.lorax.Lorax;
import org.infotoast.lorax.customobject.CustomObject;
import org.infotoast.lorax.customobject.datatype.CustomObjectBlock;
import org.infotoast.lorax.customobject.datatype.CustomObjectItem;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class CustomObjectWriter {
    public static boolean writeToFile(CustomObject object) throws IOException {
        String fileName = Lorax.plugin.getDataFolder() + "/" + object.getObjectName() + ".bo5";
        File file = new File(fileName);
        if (file.exists()) {
            file.delete();
        }
        file.createNewFile();

        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        ArrayList<CustomObjectItem> items = object.getItems();
        for (CustomObjectItem item : items) {
            if (item.getName().equals("Block")) {
                String materialString = ((CustomObjectBlock)item).getMaterial().getAsString();
                // Replace unneeded block tags
                materialString = materialString.replace("minecraft:", "");
                materialString = materialString.replace(",waterlogged=false", "");
                materialString = materialString.replace("[]", "");
                if (!materialString.contains("[")) {
                    materialString = materialString.toUpperCase();
                }
                writer.write("B(" + item.getLocation().getX() + "," + item.getLocation().getY() + "," + item.getLocation().getZ() + "," + materialString + ")\n");
            }
        }
        writer.close();
        return true;
    }
}
