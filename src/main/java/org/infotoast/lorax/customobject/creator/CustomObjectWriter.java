package org.infotoast.lorax.customobject.creator;

import org.infotoast.lorax.Lorax;
import org.infotoast.lorax.customobject.CustomObject;
import org.infotoast.lorax.customobject.CustomObjectSettings;
import org.infotoast.lorax.customobject.datatype.CustomObjectBlock;
import org.infotoast.lorax.customobject.datatype.CustomObjectItem;

import org.bukkit.block.data.BlockData;

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
        writer.write(object.toString());
        writer.close();
        return true;
    }

    public static String getBlockName(BlockData blockData) {
        String materialString = blockData.getAsString();
        // Replace unneeded block tags
        materialString = materialString.replace("minecraft:", "");
        materialString = materialString.replace(",waterlogged=false", "");
        materialString = materialString.replace("[]", "");
        if (!materialString.contains("[")) {
            materialString = materialString.toUpperCase();
        }

        return materialString;
    }
}
