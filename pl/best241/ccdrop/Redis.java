// 
// Decompiled by Procyon v0.5.30
// 

package pl.best241.ccdrop;

import java.util.Iterator;
import pl.best241.rdbplugin.JedisFactory;
import org.bukkit.Material;
import java.util.HashMap;
import redis.clients.jedis.Jedis;
import java.util.UUID;

public class Redis
{
    public static void savePlayerDiggingPoints(final UUID uuid, final long points, final Jedis jedis) {
        jedis.zadd("ccDrop.playerDiggingPoints", (double)points, uuid.toString());
    }
    
    public static long getPlayerDiggingPoints(final UUID uuid, final Jedis jedis) {
        final Double value = jedis.zscore("ccDrop.playerDiggingPoints", uuid.toString());
        if (value == null) {
            return 0L;
        }
        return (long)(Object)value;
    }
    
    public static HashMap<Material, Boolean> getPlayerDropsEnabled(final UUID uuid) {
        final Jedis jedis = JedisFactory.getInstance().getJedis();
        final String hget = jedis.hget("ccDrop.dropsEnabled", uuid.toString());
        JedisFactory.getInstance().returnJedis(jedis);
        if (hget == null) {
            return new HashMap<Material, Boolean>();
        }
        final HashMap<Material, Boolean> drops = new HashMap<Material, Boolean>();
        final String[] split;
        final String[] fields = split = hget.split(";");
        for (final String field : split) {
            final String[] parts = field.split(":");
            final Material material = Material.valueOf(parts[0]);
            final Boolean value = Boolean.valueOf(parts[1]);
            drops.put(material, value);
        }
        return drops;
    }
    
    public static void setPlayerDropsEnabled(final UUID uuid, final HashMap<Material, Boolean> drops) {
        String parsed = "";
        for (final Material materialKey : drops.keySet()) {
            final Boolean value = drops.get(materialKey);
            final String fieldData = materialKey.toString() + ":" + value;
            parsed = parsed + ";" + fieldData;
        }
        parsed = parsed.substring(1);
        final Jedis jedis = JedisFactory.getInstance().getJedis();
        jedis.hset("ccDrop.dropsEnabled", uuid.toString(), parsed);
        JedisFactory.getInstance().returnJedis(jedis);
    }
}
