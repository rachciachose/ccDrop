// 
// Decompiled by Procyon v0.5.30
// 

package pl.best241.ccdrop.manager;

import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.enchantments.Enchantment;
import java.util.List;
import java.util.ArrayList;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class DoubleManager
{
    public static ItemStack getDoubleExpItem() {
        final ItemStack item = new ItemStack(Material.PAPER);
        final ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.RED + "Podwojny exp!");
        final ArrayList<String> lore = new ArrayList<String>();
        lore.add(ChatColor.BLUE + "Uzywajac tego itemu w promieniu 300");
        lore.add(ChatColor.BLUE + "kratek rozkopujac kamien bedziesz");
        lore.add(ChatColor.BLUE + "dostawal podwojna ilosc expa!");
        meta.setLore((List)lore);
        item.setItemMeta(meta);
        item.addUnsafeEnchantment(Enchantment.LUCK, 10);
        return item;
    }
    
    public static ItemStack getDoubleDropItem() {
        final ItemStack item = new ItemStack(Material.PAPER);
        final ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.RED + "Podwojny drop!");
        final ArrayList<String> lore = new ArrayList<String>();
        lore.add(ChatColor.BLUE + "Uzywajac tego itemu w promieniu 300");
        lore.add(ChatColor.BLUE + "kratek rozkopujac kamien bedziesz");
        lore.add(ChatColor.BLUE + "dostawal podwojna ilosc przedmiotow!");
        meta.setLore((List)lore);
        item.setItemMeta(meta);
        item.addUnsafeEnchantment(Enchantment.LOOT_BONUS_BLOCKS, 10);
        return item;
    }
}
