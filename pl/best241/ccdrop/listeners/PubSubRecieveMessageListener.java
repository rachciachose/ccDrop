// 
// Decompiled by Procyon v0.5.30
// 

package pl.best241.ccdrop.listeners;

import org.bukkit.event.EventHandler;
import pl.best241.rdbplugin.pubsub.PubSub;
import org.bukkit.plugin.Plugin;
import pl.best241.ccdrop.messages.MessagesData;
import pl.best241.ccdrop.CcDrop;
import pl.best241.rdbplugin.events.PubSubRecieveMessageEvent;
import org.bukkit.event.Listener;

public class PubSubRecieveMessageListener implements Listener
{
    @EventHandler
    public static void pubSubRecieveMessageListener(final PubSubRecieveMessageEvent event) {
        if (event.getChannel().equals("reloadAllMessagesRequest")) {
            MessagesData.loadMessages((Plugin)CcDrop.getPlugin());
            PubSub.broadcast("reloadAllMessagesResponse", CcDrop.getPlugin().getName());
        }
        else if (event.getChannel().equals("ccDrop.dropbonus")) {
            final Integer dropBonus = Integer.parseInt(event.getMessage());
            CcDrop.setServerMultiplyDrop(dropBonus);
        }
        else if (event.getChannel().equals("ccDrop.expbonus")) {
            final Integer expBonus = Integer.parseInt(event.getMessage());
            CcDrop.setServerMultiplyExp(expBonus);
        }
    }
}
