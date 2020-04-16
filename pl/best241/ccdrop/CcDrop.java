// 
// Decompiled by Procyon v0.5.30
// 

package pl.best241.ccdrop;

import org.bukkit.ChatColor;
import net.minecraft.util.org.apache.commons.lang3.StringUtils;
import pl.best241.ccdrop.commands.Commands;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import pl.best241.ccdrop.data.PlayerData;
import java.util.UUID;
import org.bukkit.entity.Player;
import org.bukkit.Bukkit;
import pl.best241.ccdrop.eggs.EggDropManager;
import pl.best241.ccdrop.manager.DropManager;
import pl.best241.ccdrop.listeners.EntityDeathListener;
import pl.best241.ccdrop.events.MagicEgg;
import pl.best241.ccdrop.listeners.InventoryListener;
import pl.best241.ccdrop.listeners.PlayerInteractListener;
import pl.best241.ccdrop.listeners.StenghtPotionListener;
import pl.best241.ccdrop.listeners.BlockBreakListener;
import pl.best241.ccdrop.listeners.PlayerQuitListener;
import pl.best241.ccdrop.listeners.PlayerKickListener;
import org.bukkit.event.Listener;
import pl.best241.ccdrop.listeners.PlayerJoinListener;
import pl.best241.ccdrop.manager.DataManager;
import pl.best241.ccdrop.pubsub.PubSub;
import org.bukkit.plugin.Plugin;
import pl.best241.ccdrop.messages.MessagesData;
import org.bukkit.plugin.java.JavaPlugin;

public class CcDrop extends JavaPlugin
{
    private static int SERVER_MULTIPLY_DROP;
    private static int SERVER_MULTIPLY_EXP;
    private static CcDrop plugin;
    
    public void onEnable() {
        MessagesData.loadMessages((Plugin)(CcDrop.plugin = this));
        DiggingPoints.loadLevels();
        PubSub.listen();
        DataManager.saveTicker();
        this.getServer().getPluginManager().registerEvents((Listener)new PlayerJoinListener(), (Plugin)this);
        this.getServer().getPluginManager().registerEvents((Listener)new PlayerKickListener(), (Plugin)this);
        this.getServer().getPluginManager().registerEvents((Listener)new PlayerQuitListener(), (Plugin)this);
        this.getServer().getPluginManager().registerEvents((Listener)new BlockBreakListener(), (Plugin)this);
        this.getServer().getPluginManager().registerEvents((Listener)new StenghtPotionListener(), (Plugin)this);
        this.getServer().getPluginManager().registerEvents((Listener)new PlayerInteractListener(), (Plugin)this);
        this.getServer().getPluginManager().registerEvents((Listener)new InventoryListener(), (Plugin)this);
        this.getServer().getPluginManager().registerEvents((Listener)new MagicEgg(), (Plugin)this);
        this.getServer().getPluginManager().registerEvents((Listener)new EntityDeathListener(), (Plugin)this);
        DropManager.loadFixedSilkToutch();
        PubSub.pingTicker();
        EggDropManager.loadAll();
        for (final Player player : Bukkit.getOnlinePlayers()) {
            final UUID uuid = player.getUniqueId();
            DataManager.loadPlayerData(uuid);
        }
    }
    
    public void onDisable() {
        PubSub.stopListen();
    }
    
    public static CcDrop getPlugin() {
        return CcDrop.plugin;
    }
    
    public static void setServerMultiplyDrop(final int newMultiply) {
        CcDrop.SERVER_MULTIPLY_DROP = newMultiply;
    }
    
    public static void setServerMultiplyExp(final int newMultiply) {
        CcDrop.SERVER_MULTIPLY_EXP = newMultiply;
    }
    
    public static int getServerMultiplyDrop() {
        return CcDrop.SERVER_MULTIPLY_DROP;
    }
    
    public static int getServerMultiplyExp() {
        return CcDrop.SERVER_MULTIPLY_EXP;
    }
    
    public static PlayerData getPlayerData(final UUID uuid) {
        return DataManager.getPlayerData(uuid);
    }
    
    public boolean onCommand(final CommandSender sender, final Command cmd, final String lable, final String[] args) {
        if (sender instanceof Player) {
            final Player player = (Player)sender;
            if (cmd.getName().equalsIgnoreCase("drop")) {
                Commands.handleDrop(player);
                return true;
            }
            if (cmd.getName().equalsIgnoreCase("poziom")) {
                Commands.handlePoziom(player);
                return true;
            }
            if (cmd.getName().equalsIgnoreCase("expbonus")) {
                if (args.length == 1 && StringUtils.isNumeric((CharSequence)args[0])) {
                    final Integer exp = Integer.parseInt(args[0]);
                    Commands.handleExpBonus(player, exp);
                }
                else {
                    player.sendMessage(ChatColor.DARK_GRAY + "»" + ChatColor.RED + "Uzycie: /expbonus <BONUS>");
                }
                return true;
            }
            if (cmd.getName().equalsIgnoreCase("dropbonus")) {
                if (args.length == 1 && StringUtils.isNumeric((CharSequence)args[0])) {
                    final Integer bonus = Integer.parseInt(args[0]);
                    Commands.handleDropBonus(player, bonus);
                }
                else {
                    player.sendMessage(ChatColor.DARK_GRAY + "»" + ChatColor.RED + "Uzycie: /dropbonus <BONUS>");
                }
                return true;
            }
        }
        else {
            sender.sendMessage(MessagesData.commandOnlyForPlayers);
        }
        if (cmd.getName().equalsIgnoreCase("reloadeggs") && sender.hasPermission("ccDrop.reloadEggs")) {
            PubSub.publishReloadEggs(sender.getName());
        }
        return false;
    }
    
    static {
        CcDrop.SERVER_MULTIPLY_DROP = 1;
        CcDrop.SERVER_MULTIPLY_EXP = 1;
    }
}
