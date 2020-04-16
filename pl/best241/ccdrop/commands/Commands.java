// 
// Decompiled by Procyon v0.5.30
// 

package pl.best241.ccdrop.commands;

import org.bukkit.ChatColor;
import pl.best241.ccdrop.pubsub.PubSub;
import pl.best241.ccdrop.data.PlayerData;
import pl.best241.ccdrop.messages.MessagesData;
import pl.best241.ccdrop.DiggingPoints;
import pl.best241.ccdrop.manager.DataManager;
import java.util.UUID;
import pl.best241.ccdrop.guis.DropGuiManager;
import org.bukkit.entity.Player;

public class Commands
{
    public static void handleDrop(final Player player) {
        DropGuiManager.openGui(player);
    }
    
    public static void handlePoziom(final Player player) {
        handlePoziom(player, player.getUniqueId());
    }
    
    public static void handlePoziom(final Player player, final UUID uuid) {
        final PlayerData playerData = DataManager.getPlayerData(uuid);
        final int currentLevel = playerData.getLevel();
        final long currentExp = playerData.getPoints();
        final long toNextLevelExp = DiggingPoints.getPointsByLevel(currentLevel + 1) - currentExp;
        final String messages = MessagesData.levelCommand.replace("%level", currentLevel + "").replace("%expLeft", toNextLevelExp + "").replace("%exp", currentExp + "");
        player.sendMessage(messages);
    }
    
    public static void handleDropBonus(final Player player, final int bonus) {
        if (player.hasPermission("ccDrop.dropbonus")) {
            PubSub.publishServerDropMultiply(bonus);
            player.sendMessage(ChatColor.DARK_GRAY + "»" + ChatColor.BLUE + "Ustawiles bonus dropu: x" + bonus + "!");
        }
        else {
            player.sendMessage(ChatColor.DARK_GRAY + "»" + ChatColor.RED + "Nie posiadasz uprawnien!");
        }
    }
    
    public static void handleExpBonus(final Player player, final int bonus) {
        if (player.hasPermission("ccDrop.expbonus")) {
            PubSub.publishServerExpMultiply(bonus);
            player.sendMessage(ChatColor.DARK_GRAY + "»" + ChatColor.BLUE + "Ustawiles bonus expa: x" + bonus + "!");
        }
        else {
            player.sendMessage(ChatColor.DARK_GRAY + "»" + ChatColor.RED + "Nie posiadasz uprawnien!");
        }
    }
}
