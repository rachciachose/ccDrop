// 
// Decompiled by Procyon v0.5.30
// 

package pl.best241.ccdrop.events;

import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.entity.Entity;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import pl.best241.ccmagicchests.listeners.PlayerListener;
import org.bukkit.Effect;
import org.bukkit.entity.EntityType;
import java.util.Random;
import org.bukkit.event.player.PlayerEggThrowEvent;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.enchantments.Enchantment;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import java.util.UUID;
import java.util.ArrayList;
import org.bukkit.Material;
import org.bukkit.event.Listener;

public class MagicEgg implements Listener
{
    private static Material[] randomItems;
    private static final ArrayList<UUID> eggToThrow;
    
    public static ItemStack getMagicEgg() {
        final ItemStack item = new ItemStack(Material.EGG);
        final ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.BLUE + "Jajko wielkanocne!");
        final ArrayList<String> lore = new ArrayList<String>();
        lore.add(ChatColor.YELLOW + "Aby uzyc, rozbij je!");
        meta.setLore((List)lore);
        item.setItemMeta(meta);
        item.addUnsafeEnchantment(Enchantment.LOOT_BONUS_BLOCKS, 10);
        return item;
    }
    
    @EventHandler
    public static void onThrowEvent(final PlayerEggThrowEvent event) {
        final Player player = event.getPlayer();
        if (MagicEgg.eggToThrow.contains(player.getUniqueId())) {
            MagicEgg.eggToThrow.remove(player.getUniqueId());
            final Location location = event.getEgg().getLocation();
            final int random = new Random().nextInt(3);
            EntityType type;
            if (random == 0) {
                type = EntityType.PIG;
            }
            else if (random == 1) {
                type = EntityType.CHICKEN;
            }
            else if (random == 2) {
                type = EntityType.SHEEP;
            }
            else {
                type = EntityType.COW;
            }
            final Entity entity = location.getWorld().spawnEntity(location, type);
            location.getWorld().dropItemNaturally(location, new ItemStack(MagicEgg.randomItems[new Random().nextInt(MagicEgg.randomItems.length)]));
            for (int i = 0; i < 9; ++i) {
                player.getWorld().playEffect(location.add(Math.random(), 1.0 + Math.random(), Math.random()).add(-Math.random(), 0.0, -Math.random()).add(0.5, 0.0, 0.5), Effect.HAPPY_VILLAGER, 30, 30);
                PlayerListener.firewark(location.add(Math.random(), 1.0 + Math.random(), Math.random()).add(-Math.random(), 0.0, -Math.random()).add(0.5, 0.0, 0.5));
            }
            player.sendMessage(ChatColor.GREEN + "Wesolych i radosnych swiat zyczy craftcore!");
        }
    }
    
    @EventHandler
    public static void onInteract(final PlayerInteractEvent event) {
        final Player player = event.getPlayer();
        if (player.getItemInHand().getType() == Material.EGG && player.getItemInHand().getEnchantmentLevel(Enchantment.LOOT_BONUS_BLOCKS) == 10) {
            MagicEgg.eggToThrow.add(player.getUniqueId());
        }
    }
    
    static {
        MagicEgg.randomItems = new Material[] { Material.SANDSTONE, Material.EMERALD, Material.DIAMOND, Material.BUCKET, Material.GOLDEN_APPLE, Material.COAL, Material.OBSIDIAN, Material.REDSTONE_BLOCK, Material.BOOKSHELF };
        eggToThrow = new ArrayList<UUID>();
    }
}
