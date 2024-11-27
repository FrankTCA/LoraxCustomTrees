package org.infotoast.lorax;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDropItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.md_5.bungee.api.ChatColor;

public class AppleDropListener implements Listener {

	private final ItemStack apple = new ItemStack( Material.APPLE, 1 );
    private final ItemStack g_apple = new ItemStack( Material.GOLDEN_APPLE, 1 );
    private final ItemStack eg_apple = new ItemStack( Material.ENCHANTED_GOLDEN_APPLE, 1 );

    // apple drop related. default values:
    // regular apple 80% chance of drop, golden apple 1% chance, and 1 in 500 chance of enchanted golden apple
    private boolean doAppleDrop = false;
    private double appleChance = 80;
    private double gappleChance = 1;
    private double egappleChance = 0.2;
    
    public final static String appleLore = "A healthy apple!";
    public final static String gappleLore = "A golden delicious!";
    public final static String egappleLore = "knobbed russet";

    AppleDropListener() {

        // set up apples
        ItemMeta appleMeta = this.apple.getItemMeta();
        appleMeta.setDisplayName( ChatColor.WHITE + "" + appleLore );
        apple.setItemMeta( appleMeta );
        
        ItemMeta g_appleMeta = this.g_apple.getItemMeta();
        g_appleMeta.setDisplayName( ChatColor.YELLOW + gappleLore );
        this.g_apple.setItemMeta( g_appleMeta );
        
        ItemMeta eg_appleMeta = this.eg_apple.getItemMeta();
        eg_appleMeta.setDisplayName( ChatColor.GOLD + "A " + ChatColor.ITALIC + "rare " + ChatColor.RESET + "" + ChatColor.GOLD + egappleLore + "!" );
        this.eg_apple.setItemMeta( eg_appleMeta );
    }

    // catch block break event and drop apples instead based on chance
    @EventHandler(priority= EventPriority.LOW)
    public void onBlockBreak(BlockBreakEvent event) {
    	
        Block block = event.getBlock();
        Player player = event.getPlayer();

        if ( doAppleDrop &&
             ( block.getType().equals( Material.PLAYER_HEAD ) || block.getType().equals( Material.PLAYER_WALL_HEAD ) &&
             player.getGameMode().equals( GameMode.SURVIVAL) ) ) {

        	event.setDropItems( false );
            event.setCancelled( true );
            block.setType( Material.AIR );

            double d = Math.random();
            if ( d <  ( appleChance  / 100 ) ) {
                block.getWorld().dropItemNaturally( event.getBlock().getLocation(), apple );
            }
            if ( d < ( gappleChance / 100 ) ) {
                block.getWorld().dropItemNaturally( event.getBlock().getLocation(), g_apple );
            }
            if ( d < ( egappleChance / 100 ) ) {
                block.getWorld().dropItemNaturally( event.getBlock().getLocation(), eg_apple );
            }
        }
    }

    // break block event should capture and prevent drops, but as a backup also check here and prevent any drops
    @EventHandler
    public void onBlockBreakDrop( BlockDropItemEvent event ) {

        Block block = event.getBlock();
        Player player = event.getPlayer();

        if ( doAppleDrop
             && ( block.getType().equals( Material.PLAYER_HEAD ) || block.getType().equals( Material.PLAYER_WALL_HEAD )
             && player.getGameMode().equals( GameMode.SURVIVAL) ) ) {
            event.setCancelled( true );
        }
    }
    
    public double getAppleChance() {
    	return this.appleChance;
    }
	public void setAppleChance( double chance ) {
        this.appleChance = chance;		
	}
	public double getGAppleChance() {
    	return this.gappleChance;
    }
	public void setGAppleChance( double chance ) {
        this.gappleChance = chance;		
	}
    public double getEGAppleChance() {
    	return this.egappleChance;
    }
	public void setEGAppleChance( double chance ) {
        this.egappleChance = chance;		
	}
    public boolean getDoAppleDrop() {
    	return this.doAppleDrop;
    }
    public void setDoAppleDrop( boolean dodrop ) {
    	this.doAppleDrop = dodrop;
    }
    public void toggleAppleDrop() {
    	this.doAppleDrop = !doAppleDrop;
    }
}
