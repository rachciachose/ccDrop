// 
// Decompiled by Procyon v0.5.30
// 

package pl.best241.ccdrop.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.Location;
import org.bukkit.inventory.meta.ItemMeta;
import pl.best241.ccdrop.eggs.EggDropData;
import org.bukkit.entity.Player;
import pl.best241.ccdrop.manager.DropManager;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import java.util.Collection;
import org.bukkit.entity.EntityType;
import org.bukkit.material.MaterialData;
import java.util.List;
import java.util.Arrays;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.SpawnEgg;
import pl.best241.ccdrop.eggs.EggDropManager;
import org.bukkit.event.entity.EntityDeathEvent;
import java.util.Random;
import org.bukkit.event.Listener;

public class EntityDeathListener implements Listener
{
    private static Random random;
    
    @EventHandler
    public static void entityDeathListener(final EntityDeathEvent event) {
        final Player player = event.getEntity().getKiller();
        if (player != null) {
            final EntityType entityKilled = event.getEntityType();
            final EggDropData eggDropData = EggDropManager.getEggDropDataFromEntityType(entityKilled);
            if (eggDropData != null && Math.random() * 100.0 <= eggDropData.getDropChance()) {
                final SpawnEgg egg = new SpawnEgg(entityKilled);
                final ItemStack item = new ItemStack(egg.getItemType(), 1);
                final ItemMeta meta = item.getItemMeta();
                meta.setDisplayName(eggDropData.getEggName());
                meta.setLore((List)Arrays.asList(eggDropData.getEggLore()));
                item.setItemMeta(meta);
                item.setData((MaterialData)egg);
                final Location spawnLoc = event.getEntity().getLocation();
                spawnLoc.getWorld().dropItemNaturally(spawnLoc, item);
            }
            if (event.getEntityType() == EntityType.CREEPER) {
                final List<ItemStack> drops = (List<ItemStack>)event.getDrops();
                for (int i = 0; i < 2; ++i) {
                    event.getDrops().addAll(drops);
                }
            }
            if (event.getEntityType() == EntityType.ENDERMAN) {
                final List<ItemStack> drops = (List<ItemStack>)event.getDrops();
                for (int i = 0; i < 2; ++i) {
                    event.getDrops().addAll(drops);
                }
            }
            if (EntityDeathListener.random.nextBoolean()) {
                int loot = 0;
                if (player.getItemInHand() == null) {
                    loot = 0;
                }
                else {
                    loot = player.getItemInHand().getEnchantmentLevel(Enchantment.LOOT_BONUS_MOBS);
                }
                final int number = 1 + EntityDeathListener.random.nextInt(2) + loot;
                final ItemStack slime = new ItemStack(Material.SLIME_BALL, number);
                event.getDrops().add(slime);
            }
            DropManager.givePlayerDrop(event.getDrops(), event.getDroppedExp(), player, event.getEntity().getLocation());
            event.setDroppedExp(0);
            event.getDrops().clear();
        }
    }
    
    static {
        EntityDeathListener.random = new Random();
    }
}
