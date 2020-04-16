// 
// Decompiled by Procyon v0.5.30
// 

package pl.best241.ccdrop.manager;

import pl.best241.ccdrop.DiggingPoints;
import org.bukkit.Material;
import org.bukkit.ChatColor;
import pl.best241.ccchat.api.ChatStateAPI;
import pl.best241.ccchat.api.ChatMessageType;
import org.bukkit.entity.Player;

public class MessageManager
{
    public static void deniedBlockMessage(final Player player) {
        if (ChatStateAPI.isChatEnabled(player.getUniqueId(), ChatMessageType.DROP_MESSAGE)) {
            player.sendMessage(ChatColor.DARK_GRAY + " »" + ChatColor.RED + "Drop wylaczony! Wpisz /drop aby uzyskac informacje!");
        }
    }
    
    public static void sendDiamondMsg(final Player player, final int amount) {
        if (!ChatStateAPI.isChatEnabled(player.getUniqueId(), ChatMessageType.DROP_MESSAGE)) {
            return;
        }
        final int earned = DiggingPoints.getPointsPerMaterial(Material.DIAMOND, amount);
        if (amount > 1) {
            player.sendMessage(ChatColor.DARK_GRAY + " »" + ChatColor.AQUA + "Znalazles kilka diamentow! " + ChatColor.DARK_GRAY + "[" + ChatColor.RED + "+" + earned + ChatColor.DARK_GRAY + "]");
        }
        else {
            player.sendMessage(ChatColor.DARK_GRAY + " »" + ChatColor.AQUA + "Znalazles diament! " + ChatColor.DARK_GRAY + "[" + ChatColor.RED + "+" + earned + ChatColor.DARK_GRAY + "]");
        }
    }
    
    public static void sendEmeraldMsg(final Player player, final int amount) {
        if (!ChatStateAPI.isChatEnabled(player.getUniqueId(), ChatMessageType.DROP_MESSAGE)) {
            return;
        }
        final int earned = DiggingPoints.getPointsPerMaterial(Material.EMERALD, amount);
        if (amount > 1) {
            player.sendMessage(ChatColor.DARK_GRAY + " »" + ChatColor.DARK_GREEN + "Znalazles kilka szmaragdow! " + ChatColor.DARK_GRAY + "[" + ChatColor.RED + "+" + earned + ChatColor.DARK_GRAY + "]");
        }
        else {
            player.sendMessage(ChatColor.DARK_GRAY + " »" + ChatColor.DARK_GREEN + "Znalazles szmaragd! " + ChatColor.DARK_GRAY + "[" + ChatColor.RED + "+" + earned + ChatColor.DARK_GRAY + "]");
        }
    }
    
    public static void sendGoldMsg(final Player player, final int amount) {
        if (!ChatStateAPI.isChatEnabled(player.getUniqueId(), ChatMessageType.DROP_MESSAGE)) {
            return;
        }
        final int earned = DiggingPoints.getPointsPerMaterial(Material.GOLD_ORE, amount);
        if (amount > 1) {
            player.sendMessage(ChatColor.DARK_GRAY + " »" + ChatColor.GOLD + "Znalazles kilka sztuk rudy zlota! " + ChatColor.DARK_GRAY + "[" + ChatColor.RED + "+" + earned + ChatColor.DARK_GRAY + "]");
        }
        else {
            player.sendMessage(ChatColor.DARK_GRAY + " »" + ChatColor.GOLD + "Znalazles rude zlota! " + ChatColor.DARK_GRAY + "[" + ChatColor.RED + "+" + earned + ChatColor.DARK_GRAY + "]");
        }
    }
    
    public static void sendIronMsg(final Player player, final int amount) {
        if (!ChatStateAPI.isChatEnabled(player.getUniqueId(), ChatMessageType.DROP_MESSAGE)) {
            return;
        }
        final int earned = DiggingPoints.getPointsPerMaterial(Material.IRON_ORE, amount);
        if (amount > 1) {
            player.sendMessage(ChatColor.DARK_GRAY + " »" + ChatColor.GRAY + "Znalazles kilka sztuk rudy zelaza! " + ChatColor.DARK_GRAY + "[" + ChatColor.RED + "+" + earned + ChatColor.DARK_GRAY + "]");
        }
        else {
            player.sendMessage(ChatColor.DARK_GRAY + " »" + ChatColor.GRAY + "Znalazles rude zelaza! " + ChatColor.DARK_GRAY + "[" + ChatColor.RED + "+" + earned + ChatColor.DARK_GRAY + "]");
        }
    }
    
    public static void sendRedstoneMsg(final Player player, final int amount) {
        if (!ChatStateAPI.isChatEnabled(player.getUniqueId(), ChatMessageType.DROP_MESSAGE)) {
            return;
        }
        final int earned = DiggingPoints.getPointsPerMaterial(Material.REDSTONE, amount);
        if (amount > 1) {
            player.sendMessage(ChatColor.DARK_GRAY + " »" + ChatColor.RED + "Znalazles kilka sztuk proszku czerwonego kamienia! " + ChatColor.DARK_GRAY + "[" + ChatColor.RED + "+" + earned + ChatColor.DARK_GRAY + "]");
        }
        else {
            player.sendMessage(ChatColor.DARK_GRAY + " »" + ChatColor.RED + "Znalazles proszek czerwonego kamienia! " + ChatColor.DARK_GRAY + "[" + ChatColor.RED + "+" + earned + ChatColor.DARK_GRAY + "]");
        }
    }
    
    public static void sendCoalMsg(final Player player, final int amount) {
        if (!ChatStateAPI.isChatEnabled(player.getUniqueId(), ChatMessageType.DROP_MESSAGE)) {
            return;
        }
        final int earned = DiggingPoints.getPointsPerMaterial(Material.COAL, amount);
        if (amount > 1) {
            player.sendMessage(ChatColor.DARK_GRAY + " »" + ChatColor.DARK_GRAY + "Znalazles kilka sztuk wegla! " + ChatColor.DARK_GRAY + "[" + ChatColor.RED + "+" + earned + ChatColor.DARK_GRAY + "]");
        }
        else {
            player.sendMessage(ChatColor.DARK_GRAY + " »" + ChatColor.DARK_GRAY + "Znalazles wegiel! " + ChatColor.DARK_GRAY + "[" + ChatColor.RED + "+" + earned + ChatColor.DARK_GRAY + "]");
        }
    }
    
    public static void sendSandMsg(final Player player, final int amount) {
        if (!ChatStateAPI.isChatEnabled(player.getUniqueId(), ChatMessageType.DROP_MESSAGE)) {
            return;
        }
        final int earned = DiggingPoints.getPointsPerMaterial(Material.SAND, amount);
        if (amount > 1) {
            player.sendMessage(ChatColor.DARK_GRAY + " »" + ChatColor.YELLOW + "Znalazles kilka sztuk piasku! " + ChatColor.DARK_GRAY + "[" + ChatColor.RED + "+" + earned + ChatColor.DARK_GRAY + "]");
        }
        else {
            player.sendMessage(ChatColor.DARK_GRAY + " »" + ChatColor.YELLOW + "Znalazles piasek! " + ChatColor.DARK_GRAY + "[" + ChatColor.RED + "+" + earned + ChatColor.DARK_GRAY + "]");
        }
    }
    
    public static void sendGunpowderMsg(final Player player, final int amount) {
        if (!ChatStateAPI.isChatEnabled(player.getUniqueId(), ChatMessageType.DROP_MESSAGE)) {
            return;
        }
        final int earned = DiggingPoints.getPointsPerMaterial(Material.SULPHUR, amount);
        if (amount > 1) {
            player.sendMessage(ChatColor.DARK_GRAY + " »" + ChatColor.DARK_GRAY + "Znalazles kilka sztuk prochu! " + ChatColor.DARK_GRAY + "[" + ChatColor.RED + "+" + earned + ChatColor.DARK_GRAY + "]");
        }
        else {
            player.sendMessage(ChatColor.DARK_GRAY + " »" + ChatColor.DARK_GRAY + "Znalazles proch! " + ChatColor.DARK_GRAY + "[" + ChatColor.RED + "+" + earned + ChatColor.DARK_GRAY + "]");
        }
    }
    
    public static void sendCocobeansMsg(final Player player, final int amount) {
        if (!ChatStateAPI.isChatEnabled(player.getUniqueId(), ChatMessageType.DROP_MESSAGE)) {
            return;
        }
        final int earned = DiggingPoints.getPointsPerMaterial(Material.COCOA, amount);
        if (amount > 1) {
            player.sendMessage(ChatColor.DARK_GRAY + " »" + ChatColor.DARK_GRAY + "Znalazles kilka sztuk nasion kakao! " + ChatColor.DARK_GRAY + "[" + ChatColor.RED + "+" + earned + ChatColor.DARK_GRAY + "]");
        }
        else {
            player.sendMessage(ChatColor.DARK_GRAY + " »" + ChatColor.DARK_GRAY + "Znalazles nasienie kakao! " + ChatColor.DARK_GRAY + "[" + ChatColor.RED + "+" + earned + ChatColor.DARK_GRAY + "]");
        }
    }
    
    public static void sendLapisMsg(final Player player, final int amount) {
        if (!ChatStateAPI.isChatEnabled(player.getUniqueId(), ChatMessageType.DROP_MESSAGE)) {
            return;
        }
        final int earned = DiggingPoints.getPointsPerMaterial(Material.LAPIS_BLOCK, amount);
        if (amount > 1) {
            player.sendMessage(ChatColor.DARK_GRAY + " »" + ChatColor.BLUE + "Znalazles kilka sztuk lazurytu! " + ChatColor.DARK_GRAY + "[" + ChatColor.RED + "+" + earned + ChatColor.DARK_GRAY + "]");
        }
        else {
            player.sendMessage(ChatColor.DARK_GRAY + " »" + ChatColor.BLUE + "Znalazles lazuryt! " + ChatColor.DARK_GRAY + "[" + ChatColor.RED + "+" + earned + ChatColor.DARK_GRAY + "]");
        }
    }
    
    public static void sendAppleMsg(final Player player, final int amount) {
        if (!ChatStateAPI.isChatEnabled(player.getUniqueId(), ChatMessageType.DROP_MESSAGE)) {
            return;
        }
        final int earned = DiggingPoints.getPointsPerMaterial(Material.APPLE, amount);
        if (amount > 1) {
            player.sendMessage(ChatColor.DARK_GRAY + " »" + ChatColor.RED + "Znalazles kilka sztuk jablek! " + ChatColor.DARK_GRAY + "[" + ChatColor.RED + "+" + earned + ChatColor.DARK_GRAY + "]");
        }
        else {
            player.sendMessage(ChatColor.DARK_GRAY + " »" + ChatColor.RED + "Znalazles jablko! " + ChatColor.DARK_GRAY + "[" + ChatColor.RED + "+" + earned + ChatColor.DARK_GRAY + "]");
        }
    }
    
    public static void sendObsidianMsg(final Player player, final int amount) {
        if (!ChatStateAPI.isChatEnabled(player.getUniqueId(), ChatMessageType.DROP_MESSAGE)) {
            return;
        }
        final int earned = DiggingPoints.getPointsPerMaterial(Material.OBSIDIAN, amount);
        if (amount > 1) {
            player.sendMessage(ChatColor.DARK_GRAY + " »" + ChatColor.DARK_AQUA + "Znalazles kilka sztuk obsidianu! " + ChatColor.DARK_GRAY + "[" + ChatColor.RED + "+" + earned + ChatColor.DARK_GRAY + "]");
        }
        else {
            player.sendMessage(ChatColor.DARK_GRAY + " »" + ChatColor.DARK_AQUA + "Znalazles obsidian! " + ChatColor.DARK_GRAY + "[" + ChatColor.RED + "+" + earned + ChatColor.DARK_GRAY + "]");
        }
    }
    
    public static void sendBookMsg(final Player player, final int amount) {
        if (!ChatStateAPI.isChatEnabled(player.getUniqueId(), ChatMessageType.DROP_MESSAGE)) {
            return;
        }
        final int earned = DiggingPoints.getPointsPerMaterial(Material.BOOK, amount);
        if (amount > 1) {
            player.sendMessage(ChatColor.DARK_GRAY + " »" + ChatColor.DARK_GRAY + "Znalazles kilka ksiazek! " + ChatColor.DARK_GRAY + "[" + ChatColor.RED + "+" + earned + ChatColor.DARK_GRAY + "]");
        }
        else {
            player.sendMessage(ChatColor.DARK_GRAY + " »" + ChatColor.DARK_GRAY + "Znalazles ksiazke! " + ChatColor.DARK_GRAY + "[" + ChatColor.RED + "+" + earned + ChatColor.DARK_GRAY + "]");
        }
    }
    
    public static void sendQuartzMsg(final Player player, final int amount) {
        if (!ChatStateAPI.isChatEnabled(player.getUniqueId(), ChatMessageType.DROP_MESSAGE)) {
            return;
        }
        final int earned = DiggingPoints.getPointsPerMaterial(Material.QUARTZ, amount);
        if (amount > 1) {
            player.sendMessage(ChatColor.DARK_GRAY + " »" + ChatColor.GRAY + "Znalazles kilka sztuk kwarcu! " + ChatColor.DARK_GRAY + "[" + ChatColor.RED + "+" + earned + ChatColor.DARK_GRAY + "]");
        }
        else {
            player.sendMessage(ChatColor.DARK_GRAY + " »" + ChatColor.GRAY + "Znalazles kwarc! " + ChatColor.DARK_GRAY + "[" + ChatColor.RED + "+" + earned + ChatColor.DARK_GRAY + "]");
        }
    }
}
