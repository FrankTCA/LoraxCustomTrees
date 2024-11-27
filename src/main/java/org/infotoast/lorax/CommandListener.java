package org.infotoast.lorax;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

import org.bukkit.Chunk;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;

public class CommandListener implements CommandExecutor {

	Logger log = Logger.getLogger( "Minecraft" );
	
	private Populator pop = Lorax.getPopulator();
	private AppleDropListener appleDropper;
	
    String loraxSetUsage = ChatColor.RESET +
        "[Lorax] Usage: /loraxset toggle appledrop - toggles player head apple drop off/on" + "\n" +
        "[Lorax]        /loraxset list - list up current chance settings" + "\n" +
        "[Lorax]        /loraxset apple% <0.00..100.00> - % chance of apple drop" + "\n" +
        "[Lorax]        /loraxset g_apple% <0.00..100.00> - % chance of " + ChatColor.YELLOW + "golden " + ChatColor.RESET + "apple drop" + "\n" +
        "[Lorax]        /loraxset eg_apple% <0.00..100.00> - % chance of " + ChatColor.GOLD + "enchanted golden " + ChatColor.RESET + "apple drop";

    public CommandListener( AppleDropListener appleDropper ) {

    	this.appleDropper = appleDropper;
    }

	@Override
	public boolean onCommand( CommandSender sender, Command command, String label, String[] args ) {

        List<String> types = new ArrayList<>( Arrays.asList(
            "an apple",
            "a " + ChatColor.YELLOW + "golden apple" + ChatColor.RESET,
            "an " + ChatColor.GOLD + "enchanted golden apple" + ChatColor.RESET
        ));

        if (label.equals("loraxme")) {

            if (sender.hasPermission("lorax.loraxme")) {

            	if (sender instanceof Player) {

                    Chunk chunk = ((Player) sender).getLocation().getChunk();
                    pop.populate(((Player) sender).getWorld(), new Random(), chunk);
                    sender.sendMessage("§bChunk has been loraxed!");
                    ConsoleCommandSender console = Lorax.getInstance().getServer().getConsoleSender();
                    console.sendMessage("[Lorax] §bPlayer §2" + ((Player) sender).getDisplayName() + "§b has loraxed chunk §3(" + chunk.getX() + ", " + chunk.getZ() + ")");
                    return true;
                }
            	sender.sendMessage("§4Must be run as a player.");
                return false;
            }
            sender.sendMessage("§4Permission denied.");
        }

		if ( label.equals("loraxset") ) {

			if ( sender.hasPermission("lorax.loraxset") ) {

				switch ( args.length ) {
				case 1:
					if ( args[0].equals("list") ) {
						DecimalFormat decim = new DecimalFormat(".#");
						for ( String type : types ) {
							double chance;
							if ( type.contains("enchanted") ) {
								chance = appleDropper.getEGAppleChance();
							} else if ( type.contains("golden ") ) {
								chance = appleDropper.getGAppleChance();
							} else {
								chance = appleDropper.getAppleChance();
							}
							double odds = ( 1 / ( chance / 100 ) );
							sender.sendMessage("[Lorax] Chance of " + type + " dropping is " + chance
									+ "% or odds of 1 in " + decim.format(odds));
						}
					} else {
						loraxSetUsage(sender);
					}
					break;

				case 2:
					if ( args[0].equals("toggle") && args[1].equals("appledrop") ) {
						appleDropper.toggleAppleDrop();
						sender.sendMessage("[Lorax] Apple drop is now "
								+ ( appleDropper.getDoAppleDrop() ? ChatColor.GREEN + "" + appleDropper.getDoAppleDrop()
										: ChatColor.RED + "" + appleDropper.getDoAppleDrop() ));

					} else if ( args[0].equals("apple%") || args[0].equals("g_apple%")
							|| args[0].equals("eg_apple%") ) {
						// match 0 to 100 with two decimal places
						if ( args[1].matches("^(100(\\.0{1,2})?|\\d{1,2}(\\.\\d{1,2})?)$") ) {
							String type = null;
							double chance = Double.parseDouble(args[1]);
							DecimalFormat decim = new DecimalFormat(".#");
							double odds = ( 1 / ( chance / 100 ) );
							if ( args[0].equals("apple%") ) {
								type = types.get(0);
								appleDropper.setAppleChance(chance);
							} else if ( args[0].equals("g_apple%") ) {
								type = types.get(1);
								appleDropper.setGAppleChance(chance);
							} else if ( args[0].equals("eg_apple%") ) {
								type = types.get(2);
								appleDropper.setEGAppleChance(chance);
							}
							sender.sendMessage("[Lorax] Chance of " + type + " dropping is now " + chance
									+ "% or odds of 1 in " + decim.format(odds));
							if ( !appleDropper.getDoAppleDrop() ) {
								sender.sendMessage("[Lorax] You need to toggle apple drop to get apples though!");
							}
						} else {
							sender.sendMessage("Use a number between 0 and 100 to represent the decimal percent");
							sender.sendMessage("Use two " + ChatColor.ITALIC + "only" + ChatColor.RESET
									+ " decimal places for finer control");
							sender.sendMessage(loraxSetUsage);
						}
					} else {
						loraxSetUsage(sender);
					}
					break;

				default:
					loraxSetUsage(sender);
					break;
				}
			} else {
				sender.sendMessage("§4Permission denied.");
			}
		}
		return false;
	}
        
    private void loraxSetUsage(CommandSender sender) {
        sender.sendMessage( loraxSetUsage );
    }
}
