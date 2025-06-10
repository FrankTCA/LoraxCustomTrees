package org.infotoast.lorax.customobject;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;
import org.infotoast.lorax.Lorax;
import org.infotoast.lorax.customobject.datatype.*;
import org.infotoast.lorax.customobject.exception.InvalidMaterialException;
import org.infotoast.lorax.customobject.exception.InvalidObjectCommandException;
import org.infotoast.lorax.customobject.exception.InvalidObjectLocationException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class CustomObjectReader {
    private static ObjectLocation readLocation(String xS, String yS, String zS) {
        try {
            int x = Integer.parseInt(xS);
            int y = Integer.parseInt(yS);
            int z = Integer.parseInt(zS);
            return new ObjectLocation(x, y, z);
        } catch (InvalidObjectLocationException e) {
            throw new InvalidObjectLocationException("Invalid object file! Must be valid integer for the coordinates location.");
        }
    }

    private static BlockData readMaterial(String name) throws InvalidMaterialException {
        Pattern simpleMaterial = Pattern.compile("[A-Z_:]+", Pattern.CASE_INSENSITIVE);
        BlockData block = null;
        if (simpleMaterial.matcher(name).matches()) {
            Material mat = Material.getMaterial(name);
            if (mat == null) {
                throw new InvalidMaterialException(name);
            }
            block = mat.createBlockData();
        } else {
            block = Bukkit.createBlockData(name);
        }
        if (block == null) {
            throw new InvalidMaterialException("Material mentioned in a object file is not valid!");
        }
        return block;
    }

    public static CustomObject read(ObjectFile file) {
        ArrayList<CustomObjectItem> items = new ArrayList<CustomObjectItem>();
        ArrayList<CustomObjectCheck> checks = new ArrayList<CustomObjectCheck>();
        CustomObjectSettings settings = new CustomObjectSettings();
        String objectName = file.getName();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(Lorax.plugin.getResource(file.getFileName())))) {
            for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                // First, skip commented
                if (line.startsWith("#")) {
                    continue;
                };

                // Then, check for settings
                if (line.startsWith("Author:")) {
                    String author = line.substring(line.indexOf(":") + 2);
                    settings.setAuthor(author);
                    continue;
                }

                if (line.startsWith("Version:")) {
                    String version = line.substring(line.indexOf(":") + 2);
                    settings.setVersion(Integer.parseInt(version));
                    continue;
                }

                if (line.startsWith("Description:")) {
                    String description = line.substring(line.indexOf(":") + 2);
                    settings.setDescription(description);
                    continue;
                }

                if (line.startsWith("RotateRandomy:")) {
                    String rotateBool =  line.substring(line.indexOf(":") + 2);
                    settings.setRotateRandomly(Boolean.parseBoolean(rotateBool));
                    continue;
                }

                if (line.startsWith("MinHeight:")) {
                    String minHeight = line.substring(line.indexOf(":") + 2);
                    settings.setMinHeight(Integer.parseInt(minHeight));
                    continue;
                }

                if (line.startsWith("MaxHeight:")) {
                    String maxHeight = line.substring(line.indexOf(":") + 2);
                    settings.setMaxHeight(Integer.parseInt(maxHeight));
                    continue;
                }
                String command = line.split("\\(")[0];
                // BLOCK() keyword
                for (String keyword : CustomObjectBlock.getKeywords()) {
                    if (keyword.equalsIgnoreCase(command)) {
                        Pattern pattern = Pattern.compile(CustomObjectBlock.getRESyntax(), Pattern.CASE_INSENSITIVE);
                        if (pattern.matcher(line).matches()) {
                            String params = line.substring(line.indexOf("(") + 1, line.indexOf(")"));
                            String[] args = params.split(",(?![^\\[\\]]*])");
                            if (args.length < 4) {
                                throw new InvalidObjectCommandException("Block() must include three location args, and 1 block name arg. Usage: Block(x, y, z, material)");
                            }
                            ObjectLocation loc = readLocation(args[0], args[1], args[2]);
                            BlockData material = readMaterial(args[3]);
                            items.add(new CustomObjectBlock(loc, material));
                        } else {
                            throw new InvalidObjectCommandException("Block() syntax error. Please use Block(x, y, z, material)");
                        }
                        break;
                    }
                }

                // GROUNDCHECK() Keyword
                for (String keyword : CustomObjectGroundCheck.getKeywords()) {
                    if (keyword.equalsIgnoreCase(command)) {
                        Pattern pattern = Pattern.compile(CustomObjectGroundCheck.getRESyntax(), Pattern.CASE_INSENSITIVE);
                        if (pattern.matcher(line).matches()) {
                            String params = line.substring(line.indexOf("(") + 1, line.indexOf(")"));
                            String[] args = params.split(",");
                            if (args.length < 3) {
                                throw new InvalidObjectCommandException("GroundCheck() must include three location args.");
                            }
                            ObjectLocation loc = readLocation(args[0], args[1], args[2]);
                            CustomObjectGroundCheck check = new CustomObjectGroundCheck(loc);
                            checks.add(check);
                            items.add(check);
                        } else {
                            throw new InvalidObjectCommandException("GroundCheck() syntax error. Please use GroundCheck(x, y, z)");
                        }
                    }
                }

                //RANDOMBLOCK() Keyword
                for (String keyword : CustomObjectRandomBlock.getKeywords()) {
                    if (keyword.equalsIgnoreCase(command)) {
                        Pattern pattern = Pattern.compile(CustomObjectRandomBlock.getRESyntax(), Pattern.CASE_INSENSITIVE);
                        if (pattern.matcher(line).matches()) {
                            String params = line.substring(line.indexOf("(") + 1, line.indexOf(")"));
                            String[] args = params.split(",");
                            if (args.length < 5) {
                                throw new InvalidObjectCommandException("RandomBlock() must include three location args.");
                            }

                            ObjectLocation loc = readLocation(args[0], args[1], args[2]);
                            ArrayList<BlockData> blocks = new ArrayList<>();
                            ArrayList<Integer> chances = new ArrayList<>();
                            for (int i = 3; i < args.length-1; i += 2) {
                                blocks.add(readMaterial(args[i]));
                                chances.add(Integer.parseInt(args[i+1]));
                            }

                            BlockData[] bd = new BlockData[blocks.size()];
                            bd = blocks.toArray(bd);
                            int[] ch = chances.stream().mapToInt(Integer::intValue).toArray();
                            CustomObjectRandomBlock rb = new CustomObjectRandomBlock(loc, bd, ch);
                            items.add(rb);
                        } else {
                            throw new InvalidObjectCommandException("RandomBlock() syntax error. Please use RandomBlock(x, y, z, material, chance [, material, chance [, material, chance...]])");
                        }
                    }
                }

                // BLOCKCHECK() keyword
                for (String keyword : CustomObjectBlockCheck.getKeywords()) {
                    if (keyword.equalsIgnoreCase(command)) {
                        Pattern pattern = Pattern.compile(CustomObjectBlockCheck.getRESyntax(), Pattern.CASE_INSENSITIVE);
                        if (pattern.matcher(line).matches()) {
                            String params = line.substring(line.indexOf("(") + 1, line.indexOf(")"));
                            String[] args = params.split(",");
                            if (args.length < 4) {
                                throw new InvalidObjectCommandException("BlockCheck() must include three location args.");
                            }
                            ObjectLocation loc = readLocation(args[0], args[1], args[2]);
                            BlockData material = readMaterial(args[3]);
                            CustomObjectBlockCheck check = new CustomObjectBlockCheck(loc, material);
                            checks.add(check);
                            items.add(check);
                        } else {
                            throw new InvalidObjectCommandException("BlockCheck() syntax error. Please use BlockCheck(x, y, z, material)");
                        }
                    }
                }
            }
            return new CustomObject(objectName, items, checks, settings);
        } catch (IOException e) {
            e.printStackTrace();
            Lorax.plugin.getServer().getPluginManager().disablePlugin(Lorax.plugin);
            return null;
        } catch (InvalidObjectCommandException e) {
            e.printStackTrace();
            return null;
        }
    }
}
