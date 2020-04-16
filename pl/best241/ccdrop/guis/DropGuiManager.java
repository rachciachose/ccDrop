// 
// Decompiled by Procyon v0.5.30
// 

package pl.best241.ccdrop.guis;

import pl.best241.ccitemshop.vouchers.VoucherManager;
import java.util.Collections;
import java.util.Collection;
import pl.best241.ccmagicchests.data.LootItemData;
import pl.best241.ccmagicchests.manager.ItemsLoader;
import java.util.Iterator;
import org.bukkit.material.MaterialData;
import java.util.Arrays;
import org.bukkit.material.SpawnEgg;
import pl.best241.ccdrop.eggs.EggDropData;
import pl.best241.ccdrop.eggs.EggDropManager;
import java.util.HashMap;
import pl.best241.ccmagicchests.manager.MagicItemManager;
import pl.best241.ccdrop.events.MagicEgg;
import pl.best241.ccdrop.DiggingPoints;
import pl.best241.ccdrop.data.PlayerData;
import pl.best241.ccdrop.manager.DataManager;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.plugin.Plugin;
import org.bukkit.Bukkit;
import org.bukkit.inventory.meta.ItemMeta;
import java.util.List;
import pl.best241.ccdrop.manager.DropManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.entity.Player;
import java.util.UUID;
import java.util.ArrayList;
import pl.best241.ccdrop.CcDrop;
import org.bukkit.event.Listener;

public class DropGuiManager implements Listener
{
    private CcDrop plugin;
    public static ArrayList<UUID> openedGuids;
    private static final String doubleDropMessageFirst;
    private static final String doubleDropMessageSecond;
    public static final String headsInventoryName;
    public static final String magicChestsDrop;
    
    private static ItemStack getEventStates(final Player player) {
        final ItemStack item = new ItemStack(Material.EXP_BOTTLE);
        final ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD + "Bonusy");
        final ArrayList<String> lore = new ArrayList<String>();
        lore.add(ChatColor.DARK_GREEN + " Bonus EXP'a(event): " + ChatColor.BLUE + " x" + CcDrop.getServerMultiplyExp());
        lore.add(ChatColor.DARK_GREEN + " Bonus DROP'u(event): " + ChatColor.BLUE + " x" + CcDrop.getServerMultiplyDrop());
        lore.add(" ");
        lore.add(ChatColor.DARK_GREEN + " Podw\u00f3jny exp z IS: " + ((DropManager.getExpMultiplierWithoutServer(player.getLocation()) == 2) ? (ChatColor.GREEN + "Wlaczony") : (ChatColor.RED + "Wylaczony")));
        lore.add(ChatColor.DARK_GREEN + " Podw\u00f3jny drop z IS: " + ((DropManager.getDropMultiplierWithoutServer(player.getLocation()) == 2) ? (ChatColor.GREEN + "Wlaczony") : (ChatColor.RED + "Wylaczony")));
        meta.setLore((List)lore);
        item.setItemMeta(meta);
        return item;
    }
    
    public DropGuiManager(final CcDrop drop) {
        this.plugin = drop;
        Bukkit.getServer().getPluginManager().registerEvents((Listener)this, (Plugin)this.plugin);
    }
    
    public static void openGui(final Player player) {
        DropGuiManager.openedGuids.add(player.getUniqueId());
        final Inventory inv = Bukkit.createInventory((InventoryHolder)player, 36, ChatColor.ITALIC.toString() + ChatColor.BOLD + "Drop CraftCore.pl");
        inv.setItem(0, getGoldChance(player));
        inv.setItem(1, getDiamondChance(player));
        inv.setItem(2, getEmeraldChance(player));
        inv.setItem(3, getIronChance(player));
        inv.setItem(4, getRedstoneChance(player));
        inv.setItem(5, getCoalChance(player));
        inv.setItem(6, getGunpowderChance(player));
        inv.setItem(7, getSandChance(player));
        inv.setItem(8, getLapisChance(player));
        inv.setItem(9, getAppleChance(player));
        inv.setItem(10, getObsidianChance(player));
        inv.setItem(11, getBookChance(player));
        inv.setItem(12, getQuartzChance(player));
        inv.setItem(16, getMagicChest(player));
        inv.setItem(17, getCobble(player));
        inv.setItem(34, getMagicChestsDrop(player));
        inv.setItem(35, getMobsDrop(player));
        player.openInventory(inv);
    }
    
    public static void rerenderGui(final Player player, final Inventory inv) {
        inv.setItem(0, getGoldChance(player));
        inv.setItem(1, getDiamondChance(player));
        inv.setItem(2, getEmeraldChance(player));
        inv.setItem(3, getIronChance(player));
        inv.setItem(4, getRedstoneChance(player));
        inv.setItem(5, getCoalChance(player));
        inv.setItem(6, getGunpowderChance(player));
        inv.setItem(7, getSandChance(player));
        inv.setItem(8, getLapisChance(player));
        inv.setItem(9, getAppleChance(player));
        inv.setItem(10, getObsidianChance(player));
        inv.setItem(11, getBookChance(player));
        inv.setItem(12, getQuartzChance(player));
        inv.setItem(16, getMagicChest(player));
        inv.setItem(17, getCobble(player));
        inv.setItem(34, getMagicChestsDrop(player));
        inv.setItem(35, getMobsDrop(player));
        player.updateInventory();
    }
    
    public static double getDropBonus(final double chance, final Player player) {
        return getDropBonusWithChance(chance, player) - chance;
    }
    
    public static String getDropBonusString(final double chance, final Player player) {
        String dropBonus = getDropBonus(chance, player) + "";
        if (dropBonus.length() > 4) {
            dropBonus = dropBonus.substring(0, 4);
        }
        return "+" + dropBonus + "%";
    }
    
    public static double getDropBonusWithChance(final double chance, final Player player) {
        final PlayerData data = DataManager.getPlayerData(player.getUniqueId());
        return chance / 100.0 * (100.0 + data.getLevel() * 1.2);
    }
    
    public static String getDropEventStatus() {
        if (CcDrop.getServerMultiplyDrop() != 1) {
            return "x" + CcDrop.getServerMultiplyDrop();
        }
        return "brak";
    }
    
    public static String getItemShopStatus(final Player player) {
        return (DropManager.getDropMultiplierWithoutServer(player.getLocation()) == 2) ? (ChatColor.GREEN + "Wlaczony") : (ChatColor.RED + "Wylaczony");
    }
    
    public static ItemStack getGoldChance(final Player player) {
        final ItemStack item = new ItemStack(Material.GOLD_INGOT);
        final ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD + "Zloto");
        final ArrayList<String> lore = new ArrayList<String>();
        lore.add(ChatColor.DARK_GREEN + " Szansa: " + ChatColor.GRAY + 1.9 + "%" + ChatColor.BLUE + " bonus: " + getDropBonusString(1.9, player));
        lore.add(ChatColor.DARK_GREEN + " Punkty kopania: " + ChatColor.GRAY + "+" + DiggingPoints.getPointsPerMaterial(Material.GOLD_INGOT));
        lore.add(ChatColor.DARK_GREEN + " Ponizej poziomu: " + ChatColor.GRAY + 64);
        lore.add(ChatColor.DARK_GREEN + " Kilof: " + ChatColor.GRAY + "Zelazny");
        lore.add(ChatColor.DARK_GREEN + " Fortune: " + ChatColor.GRAY + "TAK");
        lore.add("");
        lore.add(ChatColor.YELLOW + " Event DROP: " + ChatColor.RED + getDropEventStatus());
        lore.add(ChatColor.YELLOW + " DROP z ItemShop'u: " + ChatColor.RED + getItemShopStatus(player));
        lore.add("");
        lore.add(ChatColor.GRAY + "" + ChatColor.BOLD + "Drop: " + getEnabledText(getEnabled(player.getUniqueId(), Material.GOLD_INGOT)));
        lore.add(DropGuiManager.doubleDropMessageFirst);
        lore.add(DropGuiManager.doubleDropMessageSecond);
        meta.setLore((List)lore);
        item.setItemMeta(meta);
        return item;
    }
    
    public static ItemStack getDiamondChance(final Player player) {
        final ItemStack item = new ItemStack(Material.DIAMOND);
        final ItemMeta diamondMeta = item.getItemMeta();
        diamondMeta.setDisplayName(ChatColor.BLUE + "" + ChatColor.BOLD + "Diament");
        final ArrayList<String> lore = new ArrayList<String>();
        lore.add(ChatColor.DARK_GREEN + " Szansa: " + ChatColor.GRAY + 2.0 + "%" + ChatColor.BLUE + " bonus: " + getDropBonusString(2.0, player));
        lore.add(ChatColor.DARK_GREEN + " Punkty kopania: " + ChatColor.GRAY + "+" + DiggingPoints.getPointsPerMaterial(Material.DIAMOND));
        lore.add(ChatColor.DARK_GREEN + " Ponizej poziomu: " + ChatColor.GRAY + 25);
        lore.add(ChatColor.DARK_GREEN + " Kilof: " + ChatColor.GRAY + "Zelazny");
        lore.add(ChatColor.DARK_GREEN + " Fortune: " + ChatColor.GRAY + "TAK");
        lore.add("");
        lore.add(ChatColor.YELLOW + " Event DROP: " + ChatColor.RED + getDropEventStatus());
        lore.add(ChatColor.YELLOW + " DROP z ItemShop'u: " + ChatColor.RED + getItemShopStatus(player));
        lore.add("");
        lore.add(ChatColor.GRAY + "" + ChatColor.BOLD + "Drop: " + getEnabledText(getEnabled(player.getUniqueId(), Material.DIAMOND)));
        lore.add(DropGuiManager.doubleDropMessageFirst);
        lore.add(DropGuiManager.doubleDropMessageSecond);
        diamondMeta.setLore((List)lore);
        item.setItemMeta(diamondMeta);
        return item;
    }
    
    public static ItemStack getEmeraldChance(final Player player) {
        final ItemStack item = new ItemStack(Material.EMERALD);
        final ItemMeta emeraldMeta = item.getItemMeta();
        emeraldMeta.setDisplayName(ChatColor.DARK_GREEN + "" + ChatColor.BOLD + "Emerald");
        final ArrayList<String> lore = new ArrayList<String>();
        lore.add(ChatColor.DARK_GREEN + " Szansa: " + ChatColor.GRAY + 2.9 + "%" + ChatColor.BLUE + " bonus: " + getDropBonusString(2.9, player));
        lore.add(ChatColor.DARK_GREEN + " Punkty kopania: " + ChatColor.GRAY + "+" + DiggingPoints.getPointsPerMaterial(Material.EMERALD));
        lore.add(ChatColor.DARK_GREEN + " Ponizej poziomu: " + ChatColor.GRAY + 30);
        lore.add(ChatColor.DARK_GREEN + " Kilof: " + ChatColor.GRAY + "Zelazny");
        lore.add(ChatColor.DARK_GREEN + " Fortune: " + ChatColor.GRAY + "TAK");
        lore.add("");
        lore.add(ChatColor.YELLOW + " Event DROP: " + ChatColor.RED + getDropEventStatus());
        lore.add(ChatColor.YELLOW + " DROP z ItemShop'u: " + ChatColor.RED + getItemShopStatus(player));
        lore.add("");
        lore.add(ChatColor.GRAY + "" + ChatColor.BOLD + "Drop: " + getEnabledText(getEnabled(player.getUniqueId(), Material.EMERALD)));
        lore.add(DropGuiManager.doubleDropMessageFirst);
        lore.add(DropGuiManager.doubleDropMessageSecond);
        emeraldMeta.setLore((List)lore);
        item.setItemMeta(emeraldMeta);
        return item;
    }
    
    public static ItemStack getIronChance(final Player player) {
        final ItemStack item = new ItemStack(Material.IRON_INGOT);
        final ItemMeta emeraldMeta = item.getItemMeta();
        emeraldMeta.setDisplayName(ChatColor.GRAY + "" + ChatColor.BOLD + "Zelazo");
        final ArrayList<String> lore = new ArrayList<String>();
        lore.add(ChatColor.DARK_GREEN + " Szansa: " + ChatColor.GRAY + 3.9 + "%" + ChatColor.BLUE + " bonus: " + getDropBonusString(3.9, player));
        lore.add(ChatColor.DARK_GREEN + " Punkty kopania: " + ChatColor.GRAY + "+" + DiggingPoints.getPointsPerMaterial(Material.IRON_INGOT));
        lore.add(ChatColor.DARK_GREEN + " Ponizej poziomu: " + ChatColor.GRAY + 90);
        lore.add(ChatColor.DARK_GREEN + " Kilof: " + ChatColor.GRAY + "Kamienny");
        lore.add(ChatColor.DARK_GREEN + " Fortune: " + ChatColor.GRAY + "NIE");
        lore.add("");
        lore.add(ChatColor.YELLOW + " Event DROP: " + ChatColor.RED + getDropEventStatus());
        lore.add(ChatColor.YELLOW + " DROP z ItemShop'u: " + ChatColor.RED + getItemShopStatus(player));
        lore.add("");
        lore.add(ChatColor.GRAY + "" + ChatColor.BOLD + "Drop: " + getEnabledText(getEnabled(player.getUniqueId(), Material.IRON_INGOT)));
        lore.add(DropGuiManager.doubleDropMessageFirst);
        lore.add(DropGuiManager.doubleDropMessageSecond);
        emeraldMeta.setLore((List)lore);
        item.setItemMeta(emeraldMeta);
        return item;
    }
    
    public static ItemStack getRedstoneChance(final Player player) {
        final ItemStack item = new ItemStack(Material.REDSTONE);
        final ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.RED + "" + ChatColor.BOLD + "Redstone");
        final ArrayList<String> lore = new ArrayList<String>();
        lore.add(ChatColor.DARK_GREEN + " Szansa: " + ChatColor.GRAY + 3.1 + "%" + ChatColor.BLUE + " bonus: " + getDropBonusString(3.1, player));
        lore.add(ChatColor.DARK_GREEN + " Punkty kopania: " + ChatColor.GRAY + "+" + DiggingPoints.getPointsPerMaterial(Material.REDSTONE));
        lore.add(ChatColor.DARK_GREEN + " Ponizej poziomu: " + ChatColor.GRAY + 90);
        lore.add(ChatColor.DARK_GREEN + " Kilof: " + ChatColor.GRAY + "Zelazny");
        lore.add(ChatColor.DARK_GREEN + " Fortune: " + ChatColor.GRAY + "TAK");
        lore.add("");
        lore.add(ChatColor.YELLOW + " Event DROP: " + ChatColor.RED + getDropEventStatus());
        lore.add(ChatColor.YELLOW + " DROP z ItemShop'u: " + ChatColor.RED + getItemShopStatus(player));
        lore.add("");
        lore.add(ChatColor.GRAY + "" + ChatColor.BOLD + "Drop: " + getEnabledText(getEnabled(player.getUniqueId(), Material.REDSTONE)));
        lore.add(DropGuiManager.doubleDropMessageFirst);
        lore.add(DropGuiManager.doubleDropMessageSecond);
        meta.setLore((List)lore);
        item.setItemMeta(meta);
        return item;
    }
    
    public static ItemStack getCoalChance(final Player player) {
        final ItemStack item = new ItemStack(Material.COAL);
        final ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "Wegiel");
        final ArrayList<String> lore = new ArrayList<String>();
        lore.add(ChatColor.DARK_GREEN + " Szansa: " + ChatColor.GRAY + 3.0 + "%" + ChatColor.BLUE + " bonus: " + getDropBonusString(3.0, player));
        lore.add(ChatColor.DARK_GREEN + " Punkty kopania: " + ChatColor.GRAY + "+" + DiggingPoints.getPointsPerMaterial(Material.COAL));
        lore.add(ChatColor.DARK_GREEN + " Ponizej poziomu: " + ChatColor.GRAY + 70);
        lore.add(ChatColor.DARK_GREEN + " Kilof: " + ChatColor.GRAY + "Kamienny");
        lore.add(ChatColor.DARK_GREEN + " Fortune: " + ChatColor.GRAY + "NIE");
        lore.add("");
        lore.add(ChatColor.YELLOW + " Event DROP: " + ChatColor.RED + getDropEventStatus());
        lore.add(ChatColor.YELLOW + " DROP z ItemShop'u: " + ChatColor.RED + getItemShopStatus(player));
        lore.add("");
        lore.add(ChatColor.GRAY + "" + ChatColor.BOLD + "Drop: " + getEnabledText(getEnabled(player.getUniqueId(), Material.COAL)));
        lore.add(DropGuiManager.doubleDropMessageFirst);
        lore.add(DropGuiManager.doubleDropMessageSecond);
        meta.setLore((List)lore);
        item.setItemMeta(meta);
        return item;
    }
    
    public static ItemStack getSandChance(final Player player) {
        final ItemStack item = new ItemStack(Material.SAND);
        final ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD + "Piasek");
        final ArrayList<String> lore = new ArrayList<String>();
        lore.add(ChatColor.DARK_GREEN + " Szansa: " + ChatColor.GRAY + 2.4 + "%" + ChatColor.BLUE + " bonus: " + getDropBonusString(2.4, player));
        lore.add(ChatColor.DARK_GREEN + " Punkty kopania: " + ChatColor.GRAY + "+" + DiggingPoints.getPointsPerMaterial(Material.SAND));
        lore.add(ChatColor.DARK_GREEN + " Ponizej poziomu: " + ChatColor.GRAY + 120);
        lore.add(ChatColor.DARK_GREEN + " Kilof: " + ChatColor.GRAY + "Brak");
        lore.add(ChatColor.DARK_GREEN + " Fortune: " + ChatColor.GRAY + "NIE");
        lore.add("");
        lore.add(ChatColor.YELLOW + " Event DROP: " + ChatColor.RED + getDropEventStatus());
        lore.add(ChatColor.YELLOW + " DROP z ItemShop'u: " + ChatColor.RED + getItemShopStatus(player));
        lore.add("");
        lore.add(ChatColor.GRAY + "" + ChatColor.BOLD + "Drop: " + getEnabledText(getEnabled(player.getUniqueId(), Material.SAND)));
        lore.add(DropGuiManager.doubleDropMessageFirst);
        lore.add(DropGuiManager.doubleDropMessageSecond);
        meta.setLore((List)lore);
        item.setItemMeta(meta);
        return item;
    }
    
    public static ItemStack getGunpowderChance(final Player player) {
        final ItemStack item = new ItemStack(Material.SULPHUR);
        final ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "Proch");
        final ArrayList<String> lore = new ArrayList<String>();
        lore.add(ChatColor.DARK_GREEN + " Szansa: " + ChatColor.GRAY + 1.8 + "%" + ChatColor.BLUE + " bonus: " + getDropBonusString(1.8, player));
        lore.add(ChatColor.DARK_GREEN + " Punkty kopania: " + ChatColor.GRAY + "+" + DiggingPoints.getPointsPerMaterial(Material.SULPHUR));
        lore.add(ChatColor.DARK_GREEN + " Ponizej poziomu: " + ChatColor.GRAY + 25);
        lore.add(ChatColor.DARK_GREEN + " Kilof: " + ChatColor.GRAY + "Brak");
        lore.add(ChatColor.DARK_GREEN + " Fortune: " + ChatColor.GRAY + "NIE");
        lore.add("");
        lore.add(ChatColor.YELLOW + " Event DROP: " + ChatColor.RED + getDropEventStatus());
        lore.add(ChatColor.YELLOW + " DROP z ItemShop'u: " + ChatColor.RED + getItemShopStatus(player));
        lore.add("");
        lore.add(ChatColor.GRAY + "" + ChatColor.BOLD + "Drop: " + getEnabledText(getEnabled(player.getUniqueId(), Material.SULPHUR)));
        lore.add(DropGuiManager.doubleDropMessageFirst);
        lore.add(DropGuiManager.doubleDropMessageSecond);
        meta.setLore((List)lore);
        item.setItemMeta(meta);
        return item;
    }
    
    public static ItemStack getLapisChance(final Player player) {
        final ItemStack item = new ItemStack(Material.INK_SACK, 1, (short)4);
        final ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.BLUE + "" + ChatColor.BOLD + "Lazuryt");
        final ArrayList<String> lore = new ArrayList<String>();
        lore.add(ChatColor.DARK_GREEN + " Szansa: " + ChatColor.GRAY + 1.8 + "%" + ChatColor.BLUE + " bonus: " + getDropBonusString(1.8, player));
        lore.add(ChatColor.DARK_GREEN + " Punkty kopania: " + ChatColor.GRAY + "+" + DiggingPoints.getPointsPerMaterial(Material.INK_SACK));
        lore.add(ChatColor.DARK_GREEN + " Ponizej poziomu: " + ChatColor.GRAY + 64);
        lore.add(ChatColor.DARK_GREEN + " Kilof: " + ChatColor.GRAY + "Brak");
        lore.add(ChatColor.DARK_GREEN + " Fortune: " + ChatColor.GRAY + "NIE");
        lore.add("");
        lore.add(ChatColor.YELLOW + " Event DROP: " + ChatColor.RED + getDropEventStatus());
        lore.add(ChatColor.YELLOW + " DROP z ItemShop'u: " + ChatColor.RED + getItemShopStatus(player));
        lore.add("");
        lore.add(ChatColor.GRAY + "" + ChatColor.BOLD + "Drop: " + getEnabledText(getEnabled(player.getUniqueId(), Material.INK_SACK)));
        lore.add(DropGuiManager.doubleDropMessageFirst);
        lore.add(DropGuiManager.doubleDropMessageSecond);
        meta.setLore((List)lore);
        item.setItemMeta(meta);
        return item;
    }
    
    public static ItemStack getAppleChance(final Player player) {
        final ItemStack item = new ItemStack(Material.APPLE);
        final ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.RED + "" + ChatColor.BOLD + "Jablko");
        final ArrayList<String> lore = new ArrayList<String>();
        lore.add(ChatColor.DARK_GREEN + " Szansa: " + ChatColor.GRAY + 0.9 + "%" + ChatColor.BLUE + " bonus: " + getDropBonusString(0.9, player));
        lore.add(ChatColor.DARK_GREEN + " Punkty kopania: " + ChatColor.GRAY + "+" + DiggingPoints.getPointsPerMaterial(Material.APPLE));
        lore.add(ChatColor.DARK_GREEN + " Ponizej poziomu: " + ChatColor.GRAY + 64);
        lore.add(ChatColor.DARK_GREEN + " Kilof: " + ChatColor.GRAY + "Brak");
        lore.add(ChatColor.DARK_GREEN + " Fortune: " + ChatColor.GRAY + "NIE");
        lore.add("");
        lore.add(ChatColor.YELLOW + " Event DROP: " + ChatColor.RED + getDropEventStatus());
        lore.add(ChatColor.YELLOW + " DROP z ItemShop'u: " + ChatColor.RED + getItemShopStatus(player));
        lore.add("");
        lore.add(ChatColor.GRAY + "" + ChatColor.BOLD + "Drop: " + getEnabledText(getEnabled(player.getUniqueId(), Material.APPLE)));
        lore.add(DropGuiManager.doubleDropMessageFirst);
        lore.add(DropGuiManager.doubleDropMessageSecond);
        meta.setLore((List)lore);
        item.setItemMeta(meta);
        return item;
    }
    
    public static ItemStack getObsidianChance(final Player player) {
        final ItemStack item = new ItemStack(Material.OBSIDIAN);
        final ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.BLUE + "" + ChatColor.BOLD + "Obsidian");
        final ArrayList<String> lore = new ArrayList<String>();
        lore.add(ChatColor.DARK_GREEN + " Szansa: " + ChatColor.GRAY + 2.0 + "%" + ChatColor.BLUE + " bonus: " + getDropBonusString(2.0, player));
        lore.add(ChatColor.DARK_GREEN + " Punkty kopania: " + ChatColor.GRAY + "+" + DiggingPoints.getPointsPerMaterial(Material.OBSIDIAN));
        lore.add(ChatColor.DARK_GREEN + " Ponizej poziomu: " + ChatColor.GRAY + 20);
        lore.add(ChatColor.DARK_GREEN + " Kilof: " + ChatColor.GRAY + "Brak");
        lore.add(ChatColor.DARK_GREEN + " Fortune: " + ChatColor.GRAY + "NIE");
        lore.add("");
        lore.add(ChatColor.YELLOW + " Event DROP: " + ChatColor.RED + getDropEventStatus());
        lore.add(ChatColor.YELLOW + " DROP z ItemShop'u: " + ChatColor.RED + getItemShopStatus(player));
        lore.add("");
        lore.add(ChatColor.GRAY + "" + ChatColor.BOLD + "Drop: " + getEnabledText(getEnabled(player.getUniqueId(), Material.OBSIDIAN)));
        lore.add(DropGuiManager.doubleDropMessageFirst);
        lore.add(DropGuiManager.doubleDropMessageSecond);
        meta.setLore((List)lore);
        item.setItemMeta(meta);
        return item;
    }
    
    public static ItemStack getBookChance(final Player player) {
        final ItemStack item = new ItemStack(Material.BOOK);
        final ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "Ksiazka");
        final ArrayList<String> lore = new ArrayList<String>();
        lore.add(ChatColor.DARK_GREEN + " Szansa: " + ChatColor.GRAY + 0.9 + "%" + ChatColor.BLUE + " bonus: " + getDropBonusString(0.9, player));
        lore.add(ChatColor.DARK_GREEN + " Punkty kopania: " + ChatColor.GRAY + "+" + DiggingPoints.getPointsPerMaterial(Material.BOOK));
        lore.add(ChatColor.DARK_GREEN + " Ponizej poziomu: " + ChatColor.GRAY + 24);
        lore.add(ChatColor.DARK_GREEN + " Kilof: " + ChatColor.GRAY + "Brak");
        lore.add(ChatColor.DARK_GREEN + " Fortune: " + ChatColor.GRAY + "NIE");
        lore.add("");
        lore.add(ChatColor.YELLOW + " Event DROP: " + ChatColor.RED + getDropEventStatus());
        lore.add(ChatColor.YELLOW + " DROP z ItemShop'u: " + ChatColor.RED + getItemShopStatus(player));
        lore.add("");
        lore.add(ChatColor.GRAY + "" + ChatColor.BOLD + "Drop: " + getEnabledText(getEnabled(player.getUniqueId(), Material.BOOK)));
        lore.add(DropGuiManager.doubleDropMessageFirst);
        lore.add(DropGuiManager.doubleDropMessageSecond);
        meta.setLore((List)lore);
        item.setItemMeta(meta);
        return item;
    }
    
    public static ItemStack getQuartzChance(final Player player) {
        final ItemStack item = new ItemStack(Material.QUARTZ);
        final ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.GRAY + "" + ChatColor.BOLD + "Kwarc");
        final ArrayList<String> lore = new ArrayList<String>();
        lore.add(ChatColor.DARK_GREEN + " Szansa: " + ChatColor.GRAY + 1.4 + "%" + ChatColor.BLUE + " bonus: " + getDropBonusString(1.4, player));
        lore.add(ChatColor.DARK_GREEN + " Punkty kopania: " + ChatColor.GRAY + "+" + DiggingPoints.getPointsPerMaterial(Material.QUARTZ));
        lore.add(ChatColor.DARK_GREEN + " Ponizej poziomu: " + ChatColor.GRAY + 64);
        lore.add(ChatColor.DARK_GREEN + " Kilof: " + ChatColor.GRAY + "Brak");
        lore.add(ChatColor.DARK_GREEN + " Fortune: " + ChatColor.GRAY + "NIE");
        lore.add("");
        lore.add(ChatColor.YELLOW + " Event DROP: " + ChatColor.RED + getDropEventStatus());
        lore.add(ChatColor.YELLOW + " DROP z ItemShop'u: " + ChatColor.RED + getItemShopStatus(player));
        lore.add("");
        lore.add(ChatColor.GRAY + "" + ChatColor.BOLD + "Drop: " + getEnabledText(getEnabled(player.getUniqueId(), Material.QUARTZ)));
        lore.add(DropGuiManager.doubleDropMessageFirst);
        lore.add(DropGuiManager.doubleDropMessageSecond);
        meta.setLore((List)lore);
        item.setItemMeta(meta);
        return item;
    }
    
    public static ItemStack getCobble(final Player player) {
        final ItemStack item = new ItemStack(Material.COBBLESTONE);
        final ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.GRAY + "" + ChatColor.BOLD + "Cobblestone");
        final ArrayList<String> lore = new ArrayList<String>();
        lore.add(ChatColor.GRAY + "" + ChatColor.BOLD + "Drop: " + getEnabledText(getEnabled(player.getUniqueId(), Material.COBBLESTONE)));
        meta.setLore((List)lore);
        item.setItemMeta(meta);
        return item;
    }
    
    public static ItemStack getMagicEggs(final Player player) {
        final ItemStack item = MagicEgg.getMagicEgg();
        final ItemMeta meta = item.getItemMeta();
        final ArrayList<String> lore = new ArrayList<String>();
        lore.add(ChatColor.GRAY + "" + ChatColor.BOLD + "Drop: " + getEnabledText(getEnabled(player.getUniqueId(), Material.EGG)));
        meta.setLore((List)lore);
        item.setItemMeta(meta);
        return item;
    }
    
    public static ItemStack getMagicChest(final Player player) {
        final ItemStack item = MagicItemManager.getChestItem();
        final ItemMeta meta = item.getItemMeta();
        final ArrayList<String> lore = new ArrayList<String>();
        lore.add(ChatColor.GRAY + "" + ChatColor.BOLD + "Drop: " + getEnabledText(getEnabled(player.getUniqueId(), Material.CHEST)));
        meta.setLore((List)lore);
        item.setItemMeta(meta);
        return item;
    }
    
    public static ItemStack getMobsDrop(final Player player) {
        final ItemStack item = new ItemStack(Material.SKULL_ITEM);
        final ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.BLUE + "" + ChatColor.BOLD + "DROP Z MOBOW!");
        final ArrayList<String> lore = new ArrayList<String>();
        lore.add(" ");
        lore.add(ChatColor.RED + "" + ChatColor.BOLD + "KLIKNIJ ABY ZOBACZYC!");
        meta.setLore((List)lore);
        item.setItemMeta(meta);
        return item;
    }
    
    public static ItemStack getMagicChestsDrop(final Player player) {
        final ItemStack item = new ItemStack(Material.CHEST);
        final ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.BLUE + "" + ChatColor.BOLD + "DROP Z MAGICZNYCH SKRZYNEK!");
        final ArrayList<String> lore = new ArrayList<String>();
        lore.add(" ");
        lore.add(ChatColor.RED + "" + ChatColor.BOLD + "KLIKNIJ ABY ZOBACZYC!");
        meta.setLore((List)lore);
        item.setItemMeta(meta);
        return item;
    }
    
    public static boolean getEnabled(final UUID uuid, final Material material) {
        final HashMap<Material, Boolean> status = DataManager.getPlayerData(uuid).getDropsEnabled();
        if (!status.containsKey(material)) {
            final PlayerData data = DataManager.getPlayerData(uuid);
            data.getDropsEnabled().put(material, Boolean.TRUE);
            DataManager.setPlayerData(uuid, data);
            return true;
        }
        final boolean isEnabled = status.get(material);
        return isEnabled;
    }
    
    public static void setEnabled(final UUID uuid, final Material material, final boolean value) {
        DataManager.getPlayerData(uuid).getDropsEnabled().put(material, value);
    }
    
    public static String getEnabledText(final boolean status) {
        if (status) {
            return ChatColor.DARK_GREEN + "" + ChatColor.BOLD + "Wlaczony";
        }
        return ChatColor.RED + "" + ChatColor.BOLD + "Wylaczony";
    }
    
    public static void openHeads(final Player player) {
        final Inventory inventory = Bukkit.createInventory((InventoryHolder)player, 18, DropGuiManager.headsInventoryName);
        for (final EggDropData eggDropData : EggDropManager.getAllEggDrops()) {
            final SpawnEgg egg = new SpawnEgg(eggDropData.getEntityType());
            final ItemStack item = new ItemStack(egg.getItemType(), 1);
            final ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(eggDropData.getEggName());
            meta.setLore((List)Arrays.asList(eggDropData.getEggLore()));
            item.setItemMeta(meta);
            item.setData((MaterialData)egg);
            inventory.addItem(new ItemStack[] { item });
        }
        player.openInventory(inventory);
    }
    
    public static void openMagicChestsDrop(final Player player) {
        final ArrayList<LootItemData> lootItems = new ArrayList<LootItemData>(ItemsLoader.items);
        final ArrayList<LootItemData> selectedItems = new ArrayList<LootItemData>();
        for (final LootItemData lootItem : lootItems) {
            if (lootItem.getWorth() >= 100) {
                selectedItems.add(lootItem);
            }
        }
        ItemsLoader.sort((ArrayList)selectedItems);
        Collections.reverse(selectedItems);
        final int invSize = (int)Math.ceil(selectedItems.size() / 9.0);
        final Inventory inventory = Bukkit.createInventory((InventoryHolder)player, 9 * invSize, DropGuiManager.magicChestsDrop);
        for (final LootItemData lootItem2 : selectedItems) {
            ItemStack item = null;
            if (lootItem2.getIsItem() != null) {
                item = lootItem2.getIsItem();
            }
            else if (lootItem2.getItemData() != null) {
                item = lootItem2.getItemData().getItemStack();
            }
            else if (lootItem2.getVoucherType() != null) {
                item = VoucherManager.getVoucherItem(VoucherManager.getVoucherData(lootItem2.getVoucherType()));
            }
            inventory.addItem(new ItemStack[] { item });
        }
        player.openInventory(inventory);
    }
    
    static {
        DropGuiManager.openedGuids = new ArrayList<UUID>();
        doubleDropMessageFirst = ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "Drop " + ChatColor.GOLD + "" + ChatColor.BOLD + "2x" + ChatColor.DARK_AQUA + "" + ChatColor.BOLD + " do kupienia na";
        doubleDropMessageSecond = ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "www.craftcore.pl";
        headsInventoryName = ChatColor.RED + "" + ChatColor.BOLD + "Drop z mobow!";
        magicChestsDrop = ChatColor.RED + "" + ChatColor.BOLD + "Drop z magicznych skrzynek!";
    }
}
