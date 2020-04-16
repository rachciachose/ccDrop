// 
// Decompiled by Procyon v0.5.30
// 

package pl.best241.ccdrop.listeners;

import org.bukkit.event.EventPriority;
import org.bukkit.event.EventHandler;
import java.util.ArrayList;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import java.util.List;
import pl.best241.ccdrop.manager.DropManager;
import org.bukkit.GameMode;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.Listener;

public class BlockBreakListener implements Listener
{
    @EventHandler(priority = EventPriority.HIGHEST)
    public static void blockBreakListener(final BlockBreakEvent event) {
        if (event.isCancelled()) {
            return;
        }
        final Player player = event.getPlayer();
        final Block block = event.getBlock();
        if (player.getGameMode() != GameMode.CREATIVE) {
            final ArrayList<ItemStack> drop = DropManager.getDrop(player, block);
            final int exp = DropManager.getExp(player, block);
            DropManager.recalculateDurability(player);
            DropManager.givePlayerDrop(drop, exp, player, player.getLocation());
        }
        event.setCancelled(true);
        event.getBlock().setType(Material.AIR);
    }
}
