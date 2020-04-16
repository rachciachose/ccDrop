// 
// Decompiled by Procyon v0.5.30
// 

package pl.best241.ccdrop.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.Location;
import pl.best241.ccdrop.eggs.EggDropData;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import pl.best241.ccmagicchests.listeners.PlayerListener;
import org.bukkit.Effect;
import pl.best241.ccdrop.eggs.EggDropManager;
import org.bukkit.material.SpawnEgg;
import org.bukkit.Material;
import org.bukkit.event.block.Action;
import pl.best241.ccdrop.data.DropMultiplyData;
import org.bukkit.inventory.ItemStack;
import pl.best241.ccdrop.pubsub.PubSub;
import pl.best241.ccdrop.manager.DropManager;
import pl.best241.ccdrop.data.ExpMultiplyData;
import pl.best241.ccdrop.manager.DoubleManager;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.Listener;

public class PlayerInteractListener implements Listener
{
    @EventHandler
    public void playerInteractListener(final PlayerInteractEvent event) {
        final Player player = event.getPlayer();
        if (DoubleManager.getDoubleExpItem().isSimilar(player.getItemInHand())) {
            final ExpMultiplyData data = new ExpMultiplyData(System.currentTimeMillis() + 108000000L, 2, 300, player.getLocation().getWorld().getName(), player.getLocation().getBlockX(), player.getLocation().getBlockY(), player.getLocation().getBlockZ());
            DropManager.addMultiplyExp(data);
            PubSub.publishDoubleExp(data);
            final ItemStack item = player.getItemInHand().clone();
            item.setAmount(1);
            player.getInventory().removeItem(new ItemStack[] { item });
        }
        else if (DoubleManager.getDoubleDropItem().isSimilar(player.getItemInHand())) {
            final DropMultiplyData data2 = new DropMultiplyData(System.currentTimeMillis() + 108000000L, 2, 300, player.getLocation().getWorld().getName(), player.getLocation().getBlockX(), player.getLocation().getBlockY(), player.getLocation().getBlockZ());
            DropManager.addMultiplyDrop(data2);
            PubSub.publishDoubleDrop(data2);
            final ItemStack item = player.getItemInHand().clone();
            item.setAmount(1);
            player.getInventory().removeItem(new ItemStack[] { item });
        }
        else if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            final ItemStack item2 = player.getItemInHand();
            if (item2 != null && item2.getType() == Material.MONSTER_EGG && item2.hasItemMeta() && item2.getData() instanceof SpawnEgg) {
                final SpawnEgg egg = (SpawnEgg)item2.getData();
                final EntityType spawnedType = egg.getSpawnedType();
                final EggDropData eggDropData = EggDropManager.getEggDropDataFromName(item2.getItemMeta().getDisplayName());
                if (eggDropData != null) {
                    final ItemStack randomDropItem = eggDropData.randomDropItem();
                    event.setCancelled(true);
                    final ItemStack itemToRemove = item2.clone();
                    itemToRemove.setAmount(1);
                    player.getInventory().removeItem(new ItemStack[] { itemToRemove });
                    player.updateInventory();
                    final Location loc = event.getClickedBlock().getLocation();
                    loc.getWorld().dropItemNaturally(loc, randomDropItem);
                    for (int i = 0; i < 9; ++i) {
                        player.getWorld().playEffect(loc.add(Math.random(), 1.0 + Math.random(), Math.random()).add(-Math.random(), 0.0, -Math.random()).add(0.5, 0.0, 0.5), Effect.HAPPY_VILLAGER, 30, 30);
                        PlayerListener.firewark(loc.add(Math.random(), 1.0 + Math.random(), Math.random()).add(-Math.random(), 0.0, -Math.random()).add(0.5, 0.0, 0.5));
                    }
                }
            }
        }
    }
}
