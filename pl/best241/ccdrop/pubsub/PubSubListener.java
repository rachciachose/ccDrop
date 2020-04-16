// 
// Decompiled by Procyon v0.5.30
// 

package pl.best241.ccdrop.pubsub;

import pl.best241.ccdrop.eggs.EggDropManager;
import org.bukkit.Bukkit;
import pl.best241.ccdrop.data.ExpMultiplyData;
import pl.best241.ccdrop.manager.DropManager;
import pl.best241.ccdrop.data.DropMultiplyData;
import pl.best241.ccdrop.CcDrop;
import redis.clients.jedis.JedisPubSub;

public class PubSubListener extends JedisPubSub
{
    public void onMessage(final String channel, final String message) {
        if (channel.equalsIgnoreCase("ccDrop")) {
            if (message.equals("ping")) {
                return;
            }
            final String[] parts = message.split(";");
            if (parts[0].equalsIgnoreCase("serverMultiplyDrop")) {
                final int multiply = Integer.valueOf(parts[1]);
                CcDrop.setServerMultiplyDrop(multiply);
            }
            else if (parts[0].equalsIgnoreCase("serverMultiplyExp")) {
                final int multiply = Integer.valueOf(parts[1]);
                CcDrop.setServerMultiplyExp(multiply);
            }
            else if (parts[0].equalsIgnoreCase("shopMultiplyDrop")) {
                final long endTime = Long.valueOf(parts[1]);
                final int multiply2 = Integer.valueOf(parts[2]);
                final int distance = Integer.valueOf(parts[3]);
                final String world = parts[4];
                final int x = Integer.valueOf(parts[5]);
                final int y = Integer.valueOf(parts[6]);
                final int z = Integer.valueOf(parts[7]);
                final DropMultiplyData data = new DropMultiplyData(endTime, multiply2, distance, world, x, y, z);
                DropManager.addMultiplyDrop(data);
            }
            else if (parts[0].equalsIgnoreCase("shopMultiplyExp")) {
                final long endTime = Long.valueOf(parts[1]);
                final int multiply2 = Integer.valueOf(parts[2]);
                final int distance = Integer.valueOf(parts[3]);
                final String world = parts[4];
                final int x = Integer.valueOf(parts[5]);
                final int y = Integer.valueOf(parts[6]);
                final int z = Integer.valueOf(parts[7]);
                final ExpMultiplyData exp = new ExpMultiplyData(endTime, multiply2, distance, world, x, y, z);
                DropManager.addMultiplyExp(exp);
            }
        }
        else if (channel.equalsIgnoreCase("ccDrop.broadcast")) {
            Bukkit.broadcastMessage(message);
        }
        else if (channel.equals("ccDrop.reloadEggs")) {
            EggDropManager.loadAll();
        }
    }
    
    public void onPMessage(final String pattern, final String channel, final String message) {
    }
    
    public void onSubscribe(final String channel, final int subscribedChannels) {
    }
    
    public void onUnsubscribe(final String channel, final int subscribedChannels) {
    }
    
    public void onPUnsubscribe(final String pattern, final int subscribedChannels) {
    }
    
    public void onPSubscribe(final String pattern, final int subscribedChannels) {
    }
}
