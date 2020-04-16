// 
// Decompiled by Procyon v0.5.30
// 

package pl.best241.ccdrop.manager;

import org.bukkit.Sound;
import java.util.List;
import org.bukkit.material.Leaves;
import org.bukkit.material.Tree;
import java.util.Collection;
import pl.best241.ccdrop.data.PlayerData;
import java.util.UUID;
import org.bukkit.ChatColor;
import pl.best241.ccmagicchests.manager.MagicItemManager;
import pl.best241.cccrafttrack.managers.CraftTrackManager;
import pl.best241.ccdrop.DiggingPoints;
import java.util.Arrays;
import pl.best241.ccdrop.guis.DropGuiManager;
import java.util.ArrayList;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import java.util.Iterator;
import pl.best241.ccdrop.CcDrop;
import org.bukkit.Location;
import java.util.HashMap;
import java.util.Random;
import pl.best241.ccdrop.data.ExpMultiplyData;
import pl.best241.ccdrop.data.DropMultiplyData;
import java.util.concurrent.ConcurrentLinkedQueue;
import org.bukkit.Material;

public class DropManager
{
    private static final Material[] ignoreOnFullInventory;
    private static final ConcurrentLinkedQueue<DropMultiplyData> multiplyDropLocs;
    private static final ConcurrentLinkedQueue<ExpMultiplyData> multiplyExpLocs;
    private static final Material[] deniedBlocks;
    private static final Random random;
    private static final Material[] neededToolsForDiamonds;
    private static final Material[] neededToolsForEmeralds;
    private static final Material[] neededToolsForGold;
    private static final Material[] neededToolsForIron;
    private static final Material[] neededToolsForRedstone;
    private static final Material[] neededToolsForCoal;
    private static final Material[] neededToolsForGunpowder;
    private static final Material[] durabilityFixAppliedItems;
    private static final Material[] durabilityFixSwords;
    private static final HashMap<Material, Material> fixedSilkToutch;
    
    public static void addMultiplyDrop(final DropMultiplyData data) {
        DropManager.multiplyDropLocs.add(data);
    }
    
    public static void addMultiplyExp(final ExpMultiplyData data) {
        DropManager.multiplyExpLocs.add(data);
    }
    
    public static int getExpMultiplier(final Location loc) {
        if (DropManager.multiplyExpLocs.isEmpty()) {
            return CcDrop.getServerMultiplyExp();
        }
        for (final ExpMultiplyData expMultiply : DropManager.multiplyExpLocs) {
            if (expMultiply.getTimeTo() >= System.currentTimeMillis()) {
                if (expMultiply.getLocation() != null && expMultiply.getLocation().getWorld().getEnvironment() == loc.getWorld().getEnvironment() && expMultiply.getLocation().distance(loc) <= expMultiply.getDistance()) {
                    return expMultiply.getMultiply() * CcDrop.getServerMultiplyExp();
                }
                continue;
            }
            else {
                DropManager.multiplyExpLocs.remove(expMultiply);
            }
        }
        return CcDrop.getServerMultiplyExp();
    }
    
    public static int getDropMultiplier(final Location loc) {
        if (DropManager.multiplyDropLocs.isEmpty()) {
            return CcDrop.getServerMultiplyDrop();
        }
        for (final DropMultiplyData dropMultiply : DropManager.multiplyDropLocs) {
            if (dropMultiply.getTimeTo() >= System.currentTimeMillis()) {
                if (dropMultiply.getLocation() != null && dropMultiply.getLocation().getWorld().getEnvironment() == loc.getWorld().getEnvironment() && dropMultiply.getLocation().distance(loc) <= dropMultiply.getDistance()) {
                    return dropMultiply.getMultiply() * CcDrop.getServerMultiplyDrop();
                }
                continue;
            }
            else {
                DropManager.multiplyDropLocs.remove(dropMultiply);
            }
        }
        return CcDrop.getServerMultiplyDrop();
    }
    
    public static int getExpMultiplierWithoutServer(final Location loc) {
        if (DropManager.multiplyExpLocs.isEmpty()) {
            return 1;
        }
        for (final ExpMultiplyData expMultiply : DropManager.multiplyExpLocs) {
            if (expMultiply.getTimeTo() >= System.currentTimeMillis()) {
                if (expMultiply.getLocation() != null && expMultiply.getLocation().getWorld().getEnvironment() == loc.getWorld().getEnvironment() && expMultiply.getLocation().distance(loc) <= expMultiply.getDistance()) {
                    return expMultiply.getMultiply();
                }
                continue;
            }
            else {
                DropManager.multiplyExpLocs.remove(expMultiply);
            }
        }
        return 1;
    }
    
    public static int getDropMultiplierWithoutServer(final Location loc) {
        if (DropManager.multiplyDropLocs.isEmpty()) {
            return 1;
        }
        for (final DropMultiplyData dropMultiply : DropManager.multiplyDropLocs) {
            if (dropMultiply.getTimeTo() >= System.currentTimeMillis()) {
                if (dropMultiply.getLocation() != null && dropMultiply.getLocation().getWorld().getEnvironment() == loc.getWorld().getEnvironment() && dropMultiply.getLocation().distance(loc) <= dropMultiply.getDistance()) {
                    return dropMultiply.getMultiply();
                }
                continue;
            }
            else {
                DropManager.multiplyDropLocs.remove(dropMultiply);
            }
        }
        return 1;
    }
    
    public static boolean hasSilkToutch(final ItemStack item) {
        return item.getEnchantmentLevel(Enchantment.SILK_TOUCH) >= 1;
    }
    
    public static ArrayList<ItemStack> getStoneDrop(final Player player, final Block block) {
        final UUID uuid = player.getUniqueId();
        final Location loc = player.getLocation();
        ItemStack itemInHand = player.getItemInHand();
        if (itemInHand == null) {
            itemInHand = new ItemStack(Material.AIR);
        }
        final ArrayList<ItemStack> drop = new ArrayList<ItemStack>();
        final int lootingBlocks = itemInHand.getEnchantmentLevel(Enchantment.LOOT_BONUS_BLOCKS);
        if (block.getType() == Material.STONE) {
            final int y = block.getY();
            if (y <= 25 && DropGuiManager.getEnabled(uuid, Material.DIAMOND) && Arrays.asList(DropManager.neededToolsForDiamonds).contains(itemInHand.getType())) {
                int diamonds = 0;
                final double diamondRandom = Math.random() * 100.0;
                if (diamondRandom <= DropGuiManager.getDropBonusWithChance(2.0, player) * getDropMultiplier(loc) + 11.5 * lootingBlocks / 100.0 * DropGuiManager.getDropBonusWithChance(2.0, player) * getDropMultiplier(loc)) {
                    ++diamonds;
                    if (lootingBlocks >= 1 && diamondRandom <= 1.0 * getDropMultiplier(loc)) {
                        diamonds += DropManager.random.nextInt(lootingBlocks);
                    }
                    MessageManager.sendDiamondMsg(player, diamonds);
                    ItemStack diamondsItem;
                    if (hasSilkToutch(itemInHand)) {
                        diamondsItem = new ItemStack(Material.DIAMOND_ORE, diamonds);
                    }
                    else {
                        diamondsItem = new ItemStack(Material.DIAMOND, diamonds);
                    }
                    drop.add(diamondsItem);
                    final PlayerData data = DataManager.getPlayerData(uuid);
                    data.addPoints(DiggingPoints.getPointsPerMaterial(Material.DIAMOND, diamonds));
                    data.setNeedSave(true);
                    DataManager.setPlayerData(uuid, data);
                    CraftTrackManager.updateCraftTrackPickaxe(player.getItemInHand(), 0, 0, 1, 0, 1);
                }
            }
            if (y <= 30 && DropGuiManager.getEnabled(uuid, Material.EMERALD) && Arrays.asList(DropManager.neededToolsForEmeralds).contains(itemInHand.getType())) {
                int emeralds = 0;
                final double emeraldRandom = Math.random() * 100.0;
                if (emeraldRandom <= DropGuiManager.getDropBonusWithChance(2.9, player) * getDropMultiplier(loc) + 11.5 * lootingBlocks / 100.0 * DropGuiManager.getDropBonusWithChance(2.9, player) * getDropMultiplier(loc)) {
                    ++emeralds;
                    if (lootingBlocks >= 1 && emeraldRandom <= 1.45 * getDropMultiplier(loc)) {
                        emeralds += DropManager.random.nextInt(lootingBlocks);
                    }
                    MessageManager.sendEmeraldMsg(player, emeralds);
                    ItemStack emeraldItem;
                    if (hasSilkToutch(itemInHand)) {
                        emeraldItem = new ItemStack(Material.EMERALD_ORE, emeralds);
                    }
                    else {
                        emeraldItem = new ItemStack(Material.EMERALD, emeralds);
                    }
                    drop.add(emeraldItem);
                    final PlayerData data = DataManager.getPlayerData(uuid);
                    data.addPoints(DiggingPoints.getPointsPerMaterial(Material.EMERALD, emeralds));
                    data.setNeedSave(true);
                    DataManager.setPlayerData(uuid, data);
                }
            }
            if (y <= 64 && DropGuiManager.getEnabled(uuid, Material.GOLD_INGOT) && Arrays.asList(DropManager.neededToolsForGold).contains(itemInHand.getType())) {
                int golds = 0;
                final double goldRandom = Math.random() * 100.0;
                if (goldRandom <= DropGuiManager.getDropBonusWithChance(1.9, player) * getDropMultiplier(loc) + 11.5 * lootingBlocks / 100.0 * DropGuiManager.getDropBonusWithChance(1.9, player) * getDropMultiplier(loc)) {
                    ++golds;
                    if (lootingBlocks >= 1 && goldRandom <= 0.95 * getDropMultiplier(loc)) {
                        golds += DropManager.random.nextInt(lootingBlocks);
                    }
                    MessageManager.sendGoldMsg(player, golds);
                    final ItemStack goldItem = new ItemStack(Material.GOLD_ORE, golds);
                    drop.add(goldItem);
                    final PlayerData data = DataManager.getPlayerData(uuid);
                    data.addPoints(DiggingPoints.getPointsPerMaterial(Material.GOLD_ORE, golds));
                    data.setNeedSave(true);
                    DataManager.setPlayerData(uuid, data);
                    if (CraftTrackManager.isCraftTrackPickAxe(player.getItemInHand())) {
                        CraftTrackManager.updateCraftTrackPickaxe(player.getItemInHand(), 0, 0, 0, 1, 1);
                    }
                }
            }
            if (y <= 90 && DropGuiManager.getEnabled(uuid, Material.IRON_INGOT) && Arrays.asList(DropManager.neededToolsForIron).contains(itemInHand.getType())) {
                int irons = 0;
                final double ironRandom = Math.random() * 100.0;
                if (ironRandom <= DropGuiManager.getDropBonusWithChance(3.9, player) * getDropMultiplier(loc) + 11.5 * lootingBlocks / 100.0 * DropGuiManager.getDropBonusWithChance(3.9, player) * getDropMultiplier(loc)) {
                    ++irons;
                    if (lootingBlocks >= 1 && ironRandom <= 1.95 * getDropMultiplier(loc)) {
                        irons += DropManager.random.nextInt(lootingBlocks);
                    }
                    MessageManager.sendIronMsg(player, irons);
                    final ItemStack ironItem = new ItemStack(Material.IRON_ORE, irons);
                    drop.add(ironItem);
                    final PlayerData data = DataManager.getPlayerData(uuid);
                    data.addPoints(DiggingPoints.getPointsPerMaterial(Material.IRON_ORE, irons));
                    data.setNeedSave(true);
                    DataManager.setPlayerData(uuid, data);
                }
            }
            if (y <= 90 && DropGuiManager.getEnabled(uuid, Material.REDSTONE) && Arrays.asList(DropManager.neededToolsForRedstone).contains(itemInHand.getType())) {
                int redstone = 0;
                final double redstoneRandom = Math.random() * 100.0;
                if (redstoneRandom <= DropGuiManager.getDropBonusWithChance(3.1, player) * getDropMultiplier(loc) + (11.5 + lootingBlocks / 100 * DropGuiManager.getDropBonusWithChance(3.1, player) * getDropMultiplier(loc))) {
                    redstone = redstone + 3 + DropManager.random.nextInt(1);
                    if (lootingBlocks >= 1 && redstoneRandom <= 1.55 * getDropMultiplier(loc)) {
                        redstone += DropManager.random.nextInt((int)Math.round(lootingBlocks * 1.5));
                    }
                    MessageManager.sendRedstoneMsg(player, redstone);
                    final ItemStack redstoneItem = new ItemStack(Material.REDSTONE, redstone);
                    drop.add(redstoneItem);
                    final PlayerData data = DataManager.getPlayerData(uuid);
                    data.addPoints(DiggingPoints.getPointsPerMaterial(Material.REDSTONE, redstone));
                    data.setNeedSave(true);
                    DataManager.setPlayerData(uuid, data);
                }
            }
            if (y <= 70 && DropGuiManager.getEnabled(uuid, Material.COAL) && Arrays.asList(DropManager.neededToolsForCoal).contains(itemInHand.getType())) {
                int coal = 0;
                final double coalRandom = Math.random() * 100.0;
                if (coalRandom <= DropGuiManager.getDropBonusWithChance(3.0, player) * getDropMultiplier(loc) + (11.5 + lootingBlocks / 100 * DropGuiManager.getDropBonusWithChance(3.0, player) * getDropMultiplier(loc))) {
                    ++coal;
                    if (lootingBlocks >= 1 && coalRandom <= 1.5 * getDropMultiplier(loc)) {
                        coal += DropManager.random.nextInt(lootingBlocks);
                    }
                    MessageManager.sendCoalMsg(player, coal);
                    ItemStack coalItem;
                    if (hasSilkToutch(itemInHand)) {
                        coalItem = new ItemStack(Material.COAL_ORE, coal);
                    }
                    else {
                        coalItem = new ItemStack(Material.COAL, coal);
                    }
                    drop.add(coalItem);
                    final PlayerData data = DataManager.getPlayerData(uuid);
                    data.addPoints(DiggingPoints.getPointsPerMaterial(Material.COAL, coal));
                    data.setNeedSave(true);
                    DataManager.setPlayerData(uuid, data);
                }
            }
            if (y <= 120 && DropGuiManager.getEnabled(uuid, Material.SAND)) {
                int sand = 0;
                final double sandRandom = Math.random() * 100.0;
                if (sandRandom <= DropGuiManager.getDropBonusWithChance(2.4, player) * getDropMultiplier(loc)) {
                    ++sand;
                    MessageManager.sendSandMsg(player, sand);
                    final ItemStack sandItem = new ItemStack(Material.SAND, sand);
                    drop.add(sandItem);
                    final PlayerData data = DataManager.getPlayerData(uuid);
                    data.addPoints(DiggingPoints.getPointsPerMaterial(Material.SAND, sand));
                    data.setNeedSave(true);
                    DataManager.setPlayerData(uuid, data);
                }
            }
            if (y <= 25 && DropGuiManager.getEnabled(uuid, Material.SULPHUR) && containsIgnoreCase(DropManager.neededToolsForGunpowder, itemInHand.getType())) {
                int gunpowder = 0;
                final double gunpowderRandom = Math.random() * 100.0;
                if (gunpowderRandom <= DropGuiManager.getDropBonusWithChance(1.8, player) * getDropMultiplier(loc)) {
                    ++gunpowder;
                    MessageManager.sendGunpowderMsg(player, gunpowder);
                    final ItemStack gunpowderItem = new ItemStack(Material.SULPHUR, gunpowder);
                    drop.add(gunpowderItem);
                    final PlayerData data = DataManager.getPlayerData(uuid);
                    data.addPoints(DiggingPoints.getPointsPerMaterial(Material.SULPHUR, gunpowder));
                    data.setNeedSave(true);
                    DataManager.setPlayerData(uuid, data);
                }
            }
            if (y <= 64 && DropGuiManager.getEnabled(uuid, Material.INK_SACK)) {
                int lapis = 0;
                final double lapisRandom = Math.random() * 100.0;
                if (lapisRandom <= DropGuiManager.getDropBonusWithChance(1.8, player) * getDropMultiplier(loc)) {
                    lapis += 1 + DropManager.random.nextInt(4);
                    MessageManager.sendLapisMsg(player, lapis);
                    final ItemStack lapisItem = new ItemStack(Material.INK_SACK, lapis, (short)4);
                    drop.add(lapisItem);
                    final PlayerData data = DataManager.getPlayerData(uuid);
                    data.addPoints(DiggingPoints.getPointsPerMaterial(Material.LAPIS_ORE, lapis));
                    data.setNeedSave(true);
                    DataManager.setPlayerData(uuid, data);
                }
            }
            if (y <= 64 && DropGuiManager.getEnabled(uuid, Material.APPLE)) {
                int apple = 0;
                final double appleRandom = Math.random() * 100.0;
                if (appleRandom <= DropGuiManager.getDropBonusWithChance(0.9, player) * getDropMultiplier(loc)) {
                    apple += 1 + DropManager.random.nextInt(2);
                    MessageManager.sendAppleMsg(player, apple);
                    final ItemStack appleItem = new ItemStack(Material.APPLE, apple);
                    drop.add(appleItem);
                    final PlayerData data = DataManager.getPlayerData(uuid);
                    data.addPoints(DiggingPoints.getPointsPerMaterial(Material.APPLE, apple));
                    data.setNeedSave(true);
                    DataManager.setPlayerData(uuid, data);
                }
            }
            if (y <= 20 && DropGuiManager.getEnabled(uuid, Material.OBSIDIAN)) {
                int obsidian = 0;
                final double obsidianRandom = Math.random() * 100.0;
                if (obsidianRandom <= DropGuiManager.getDropBonusWithChance(2.0, player) * getDropMultiplier(loc)) {
                    obsidian += 1 + DropManager.random.nextInt(2);
                    MessageManager.sendObsidianMsg(player, obsidian);
                    final ItemStack obsidianItem = new ItemStack(Material.OBSIDIAN, obsidian);
                    drop.add(obsidianItem);
                    final PlayerData data = DataManager.getPlayerData(uuid);
                    data.addPoints(DiggingPoints.getPointsPerMaterial(Material.OBSIDIAN, obsidian));
                    data.setNeedSave(true);
                    DataManager.setPlayerData(uuid, data);
                }
            }
            if (y <= 24 && DropGuiManager.getEnabled(uuid, Material.BOOK)) {
                int book = 0;
                final double bookRandom = Math.random() * 100.0;
                if (bookRandom <= DropGuiManager.getDropBonusWithChance(0.9, player) * getDropMultiplier(loc)) {
                    ++book;
                    MessageManager.sendBookMsg(player, book);
                    final ItemStack bookItem = new ItemStack(Material.BOOK, book);
                    drop.add(bookItem);
                    final PlayerData data = DataManager.getPlayerData(uuid);
                    data.addPoints(DiggingPoints.getPointsPerMaterial(Material.BOOK));
                    data.setNeedSave(true);
                    DataManager.setPlayerData(uuid, data);
                }
            }
            if (y <= 64 && DropGuiManager.getEnabled(uuid, Material.QUARTZ)) {
                int quartz = 0;
                final double quartzRandom = Math.random() * 100.0;
                if (quartzRandom <= DropGuiManager.getDropBonusWithChance(1.4, player) * getDropMultiplier(loc)) {
                    quartz += 1 + DropManager.random.nextInt(3);
                    MessageManager.sendQuartzMsg(player, quartz);
                    final ItemStack quartzItem = new ItemStack(Material.QUARTZ, quartz);
                    drop.add(quartzItem);
                    final PlayerData data = DataManager.getPlayerData(uuid);
                    data.addPoints(DiggingPoints.getPointsPerMaterial(Material.QUARTZ));
                    data.setNeedSave(true);
                    DataManager.setPlayerData(uuid, data);
                }
            }
            if (Math.random() * 100.0 < 0.8 && DropGuiManager.getEnabled(uuid, Material.CHEST)) {
                drop.add(MagicItemManager.getChestItem());
                player.sendMessage(ChatColor.DARK_GRAY + " »" + ChatColor.ITALIC + "Znalazes magiczna skrzynke!");
            }
            if (drop.isEmpty()) {
                if (hasSilkToutch(itemInHand)) {
                    drop.add(new ItemStack(Material.STONE));
                }
                else if (DropGuiManager.getEnabled(uuid, Material.COBBLESTONE)) {
                    drop.add(new ItemStack(Material.COBBLESTONE));
                }
            }
        }
        return drop;
    }
    
    public static int getExp(final Player player, final Block block) {
        if (block.getType() != Material.STONE) {
            return 0;
        }
        int exp = 0;
        if (block.getY() <= 255) {
            final double chance = Math.random() * 100.0;
            if (chance <= 85.0) {
                exp += 8;
            }
        }
        return exp * getExpMultiplier(block.getLocation());
    }
    
    public static ArrayList<ItemStack> getDrop(final Player player, final Block block) {
        final ArrayList<ItemStack> drop = new ArrayList<ItemStack>();
        if (containsIgnoreCase(DropManager.deniedBlocks, block.getType())) {
            MessageManager.deniedBlockMessage(player);
            return drop;
        }
        if (block.getType() == Material.STONE) {
            drop.addAll(getStoneDrop(player, block));
        }
        else {
            drop.addAll(getDrops(block, player.getItemInHand()));
        }
        return drop;
    }
    
    public static void loadFixedSilkToutch() {
        DropManager.fixedSilkToutch.put(Material.GRASS, Material.GRASS);
        DropManager.fixedSilkToutch.put(Material.BOOKSHELF, Material.BOOKSHELF);
        DropManager.fixedSilkToutch.put(Material.MELON, Material.MELON);
        DropManager.fixedSilkToutch.put(Material.MYCEL, Material.MYCEL);
        DropManager.fixedSilkToutch.put(Material.SNOW_BLOCK, Material.SNOW_BLOCK);
        DropManager.fixedSilkToutch.put(Material.GLOWSTONE, Material.GLOWSTONE);
        DropManager.fixedSilkToutch.put(Material.BOOKSHELF, Material.BOOKSHELF);
        DropManager.fixedSilkToutch.put(Material.ENDER_CHEST, Material.ENDER_CHEST);
        DropManager.fixedSilkToutch.put(Material.ICE, Material.ICE);
        DropManager.fixedSilkToutch.put(Material.PACKED_ICE, Material.PACKED_ICE);
        DropManager.fixedSilkToutch.put(Material.QUARTZ_BLOCK, Material.QUARTZ_BLOCK);
    }
    
    public static ArrayList<ItemStack> getDrops(final Block block, final ItemStack itemInHand) {
        if (DropManager.fixedSilkToutch.isEmpty()) {
            loadFixedSilkToutch();
        }
        final ArrayList<ItemStack> drop = new ArrayList<ItemStack>();
        if (hasSilkToutch(itemInHand) && (block.getType() == Material.GLASS || block.getType() == Material.STAINED_GLASS_PANE)) {
            drop.add(block.getState().getData().toItemStack());
        }
        else if ((hasSilkToutch(itemInHand) || itemInHand.getType() == Material.SHEARS) && block.getType() == Material.LEAVES) {
            final Tree tree = (Tree)block.getState().getData();
            drop.add(new Leaves(tree.getSpecies()).toItemStack(1));
        }
        else if (hasSilkToutch(itemInHand) && block.getType() == Material.DIRT) {
            final ItemStack dirtItem = new ItemStack(block.getType(), 1);
            dirtItem.setData(block.getState().getData());
            drop.add(dirtItem);
        }
        else if (hasSilkToutch(itemInHand) && DropManager.fixedSilkToutch.containsKey(block.getType())) {
            drop.add(new ItemStack((Material)DropManager.fixedSilkToutch.get(block.getType())));
        }
        else if (block.getType() == Material.ENDER_CHEST) {
            drop.add(new ItemStack(Material.ENDER_CHEST));
        }
        else if (block.getType() == Material.CARROT && block.getState().getData().toItemStack().getDurability() == 7) {
            drop.add(new ItemStack(Material.CARROT_ITEM, 2 + DropManager.random.nextInt(3)));
        }
        else if (block.getType() == Material.CROPS && block.getState().getData().toItemStack().getDurability() == 7) {
            drop.add(new ItemStack(Material.WHEAT));
            drop.add(new ItemStack(Material.SEEDS, 2 + DropManager.random.nextInt(3)));
        }
        else if (block.getType() == Material.POTATO && block.getState().getData().toItemStack().getDurability() == 7) {
            drop.add(new ItemStack(Material.POTATO_ITEM, 2 + DropManager.random.nextInt(3)));
        }
        else if (block.getType().getId() == 115) {
            final short state = block.getState().getData().toItemStack().getDurability();
            if (state == 0 || state == 1 || state == 2) {
                final ItemStack itemStack = new ItemStack(372);
                drop.add(itemStack);
            }
            else if (state == 3) {
                final int number = 2 + DropManager.random.nextInt(3);
                final ItemStack itemStack2 = new ItemStack(372, number);
                drop.add(itemStack2);
            }
        }
        else {
            drop.addAll(block.getDrops(itemInHand));
        }
        return drop;
    }
    
    public static boolean containsIgnoreCase(final Material[] board, final Material material) {
        for (final Material othermaterial : board) {
            if (material == othermaterial) {
                return true;
            }
        }
        return false;
    }
    
    public static void givePlayerDrop(final List<ItemStack> items, final int droppedExp, final Player player, final Location dropLocation) {
        if (droppedExp > 0) {
            player.giveExp(droppedExp);
            player.playSound(player.getLocation(), Sound.ORB_PICKUP, 0.5f, (float)(Math.random() * 20.0) / 10.0f);
        }
        if (items != null) {
            final ItemStack[] itemBoard = new ItemStack[items.size()];
            for (int i = 0; i < items.size(); ++i) {
                itemBoard[i] = items.get(i);
            }
            final HashMap<Integer, ItemStack> notStored = (HashMap<Integer, ItemStack>)player.getInventory().addItem(itemBoard);
            notStored.values().stream().filter(item -> !containsIgnoreCase(DropManager.ignoreOnFullInventory, item.getType()) && !item.getType().equals((Object)Material.AIR)).forEach(item -> dropLocation.getWorld().dropItemNaturally(dropLocation, item));
        }
    }
    
    public static void recalculateDurability(final Player player) {
        final ItemStack item = player.getItemInHand();
        final Integer enchantLevel = item.getEnchantmentLevel(Enchantment.DURABILITY);
        final int randomedValue = DropManager.random.nextInt(100);
        if (Arrays.asList(DropManager.durabilityFixAppliedItems).contains(item.getType()) && (enchantLevel == 0 || randomedValue <= 100 / (enchantLevel + 1))) {
            if (Arrays.asList(DropManager.durabilityFixSwords).contains(item.getType())) {
                if (item.getDurability() + 2 >= item.getType().getMaxDurability()) {
                    player.getInventory().clear(player.getInventory().getHeldItemSlot());
                    player.playSound(player.getLocation(), Sound.ITEM_BREAK, 1.0f, 1.0f);
                }
                else {
                    item.setDurability((short)(item.getDurability() + 2));
                }
            }
            else if (item.getDurability() + 1 >= item.getType().getMaxDurability()) {
                player.getInventory().clear(player.getInventory().getHeldItemSlot());
                player.playSound(player.getLocation(), Sound.ITEM_BREAK, 1.0f, 1.0f);
            }
            else {
                item.setDurability((short)(item.getDurability() + 1));
            }
            player.updateInventory();
        }
    }
    
    static {
        ignoreOnFullInventory = new Material[] { Material.COBBLESTONE, Material.DIRT, Material.GRAVEL };
        multiplyDropLocs = new ConcurrentLinkedQueue<DropMultiplyData>();
        multiplyExpLocs = new ConcurrentLinkedQueue<ExpMultiplyData>();
        deniedBlocks = new Material[0];
        random = new Random();
        neededToolsForDiamonds = new Material[] { Material.IRON_PICKAXE, Material.DIAMOND_PICKAXE };
        neededToolsForEmeralds = new Material[] { Material.IRON_PICKAXE, Material.DIAMOND_PICKAXE };
        neededToolsForGold = new Material[] { Material.IRON_PICKAXE, Material.DIAMOND_PICKAXE };
        neededToolsForIron = new Material[] { Material.STONE_PICKAXE, Material.IRON_PICKAXE, Material.DIAMOND_PICKAXE };
        neededToolsForRedstone = new Material[] { Material.IRON_PICKAXE, Material.DIAMOND_PICKAXE };
        neededToolsForCoal = new Material[] { Material.WOOD_PICKAXE, Material.STONE_PICKAXE, Material.IRON_PICKAXE, Material.DIAMOND_PICKAXE, Material.GOLD_PICKAXE };
        neededToolsForGunpowder = new Material[] { Material.IRON_PICKAXE, Material.DIAMOND_PICKAXE };
        durabilityFixAppliedItems = new Material[] { Material.WOOD_SWORD, Material.WOOD_PICKAXE, Material.WOOD_AXE, Material.WOOD_SPADE, Material.STONE_SWORD, Material.STONE_PICKAXE, Material.STONE_AXE, Material.STONE_SPADE, Material.IRON_SWORD, Material.IRON_PICKAXE, Material.IRON_AXE, Material.IRON_SPADE, Material.GOLD_SWORD, Material.GOLD_PICKAXE, Material.GOLD_AXE, Material.GOLD_SPADE, Material.DIAMOND_SWORD, Material.DIAMOND_PICKAXE, Material.DIAMOND_AXE, Material.DIAMOND_SPADE };
        durabilityFixSwords = new Material[] { Material.WOOD_SWORD, Material.STONE_SWORD, Material.IRON_SWORD, Material.GOLD_SWORD, Material.DIAMOND_SWORD };
        fixedSilkToutch = new HashMap<Material, Material>();
    }
}
