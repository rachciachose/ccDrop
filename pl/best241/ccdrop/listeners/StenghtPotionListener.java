// 
// Decompiled by Procyon v0.5.30
// 

package pl.best241.ccdrop.listeners;

import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.ChatColor;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.Listener;

public class StenghtPotionListener implements Listener
{
    @EventHandler
    public void onPlayerInteract(final PlayerInteractEvent event) {
        if (event.getPlayer().getItemInHand().getTypeId() == 373 && event.getPlayer().getItemInHand().getData().getData() == 41) {
            event.setCancelled(true);
            event.getPlayer().sendMessage(ChatColor.RED + "Nie mozesz uzywac potek Strenght II!");
        }
    }
    
    @EventHandler
    public void onBlockDispense(final BlockDispenseEvent event) {
        if (event.getItem().getTypeId() == 373 && event.getItem().getData().getData() == 41) {
            event.setCancelled(true);
        }
    }
}
