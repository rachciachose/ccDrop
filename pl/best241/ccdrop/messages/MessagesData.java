// 
// Decompiled by Procyon v0.5.30
// 

package pl.best241.ccdrop.messages;

import java.util.Iterator;
import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;
import java.util.ArrayList;
import java.util.HashMap;

public class MessagesData
{
    public static String commandOnlyForPlayers;
    public static String levelCommand;
    private static MessagesConfig config;
    private static final HashMap<String, String> pathMessage;
    private static final HashMap<String, ArrayList<String>> pathMessageList;
    
    public static void loadMessages(final Plugin plugin) {
        (MessagesData.config = new MessagesConfig(plugin, "messages.yml")).saveDefaultConfig();
        MessagesData.config.reloadCustomConfig();
        MessagesData.commandOnlyForPlayers = MessagesData.config.getString("commandOnlyForPlayers");
        MessagesData.levelCommand = MessagesData.config.getString("levelCommand");
    }
    
    public static String getMessage(final String path) {
        if (MessagesData.pathMessage.containsKey(path)) {
            return MessagesData.pathMessage.get(path);
        }
        final String message = MessagesData.config.getString(path);
        MessagesData.pathMessage.put(path, message);
        return message;
    }
    
    public static ArrayList<String> getMessageList(final String path) {
        if (MessagesData.pathMessageList.containsKey(path)) {
            return MessagesData.pathMessageList.get(path);
        }
        final ArrayList<String> list = (ArrayList<String>)MessagesData.config.getCustomConfig().getList(path);
        final ArrayList<String> coloredList = new ArrayList<String>();
        for (final String message : list) {
            coloredList.add(ChatColor.translateAlternateColorCodes('&', message));
        }
        MessagesData.pathMessageList.put(path, coloredList);
        return coloredList;
    }
    
    static {
        pathMessage = new HashMap<String, String>();
        pathMessageList = new HashMap<String, ArrayList<String>>();
    }
}
