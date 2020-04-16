// 
// Decompiled by Procyon v0.5.30
// 

package pl.best241.ccdrop.pubsub;

import org.bukkit.plugin.Plugin;
import java.util.Random;
import pl.best241.ccdrop.CcDrop;
import org.bukkit.Bukkit;
import pl.best241.ccdrop.data.DropMultiplyData;
import pl.best241.ccdrop.data.ExpMultiplyData;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;
import pl.best241.rdbplugin.JedisFactory;

public class PubSub
{
    private static final PubSubListener psl;
    private static final String SEP = ";";
    
    public static void listen() {
        final Jedis jedis = JedisFactory.getInstance().getNewUnpooledJedis();
        jedis.clientSetname("ccDrop_subscriber");
        final Thread listenThread = new Thread(() -> jedis.subscribe((JedisPubSub)PubSub.psl, new String[] { "ccDrop", "ccDrop.broadcast" }));
        listenThread.start();
    }
    
    public static void stopListen() {
        PubSub.psl.unsubscribe();
    }
    
    public static void publishDoubleExp(final ExpMultiplyData data) {
        if (data == null) {
            System.out.println("data is null");
            return;
        }
        final String worldName = data.getLocation().getWorld().getName();
        final String pubsubData = "shopMultiplyExp;" + data.getTimeTo() + ";" + data.getMultiply() + ";" + data.getDistance() + ";" + data.getLocation().getWorld().getName() + ";" + data.getLocation().getBlockX() + ";" + data.getLocation().getBlockY() + ";" + data.getLocation().getBlockZ();
        final Jedis jedis = JedisFactory.getInstance().getJedis();
        jedis.publish("ccDrop", pubsubData);
        JedisFactory.getInstance().returnJedis(jedis);
    }
    
    public static void publishDoubleDrop(final DropMultiplyData data) {
        if (data == null) {
            System.out.println("data is null");
            return;
        }
        final String worldName = data.getLocation().getWorld().getName();
        final String pubsubData = "shopMultiplyDrop;" + data.getTimeTo() + ";" + data.getMultiply() + ";" + data.getDistance() + ";" + data.getLocation().getWorld().getName() + ";" + data.getLocation().getBlockX() + ";" + data.getLocation().getBlockY() + ";" + data.getLocation().getBlockZ();
        final Jedis jedis = JedisFactory.getInstance().getJedis();
        jedis.publish("ccDrop", pubsubData);
        JedisFactory.getInstance().returnJedis(jedis);
    }
    
    public static void pingTicker() {
        Bukkit.getScheduler().runTaskTimer((Plugin)CcDrop.getPlugin(), (Runnable)new Runnable() {
            @Override
            public void run() {
                final Jedis jedis = JedisFactory.getInstance().getJedis();
                jedis.publish("ccDrop", "ping");
                JedisFactory.getInstance().returnJedis(jedis);
            }
        }, (long)((new Random().nextInt(200) + 1) * 20), (long)((new Random().nextInt(200) + 1) * 20));
    }
    
    public static void publishReloadEggs(final String name) {
        final Jedis jedis = JedisFactory.getInstance().getJedis();
        jedis.publish("ccDrop.reloadEggs", name);
        JedisFactory.getInstance().returnJedis(jedis);
    }
    
    public static void publishServerDropMultiply(final int bonus) {
        final Jedis jedis = JedisFactory.getInstance().getJedis();
        jedis.publish("ccDrop", "serverMultiplyDrop;" + bonus);
        JedisFactory.getInstance().returnJedis(jedis);
    }
    
    public static void publishServerExpMultiply(final int bonus) {
        final Jedis jedis = JedisFactory.getInstance().getJedis();
        jedis.publish("ccDrop", "serverMultiplyExp;" + bonus);
        JedisFactory.getInstance().returnJedis(jedis);
    }
    
    static {
        psl = new PubSubListener();
    }
}
