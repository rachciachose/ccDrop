// 
// Decompiled by Procyon v0.5.30
// 

package pl.best241.ccdrop.manager;

import org.bukkit.plugin.Plugin;
import java.util.Iterator;
import pl.best241.ccdrop.CcDrop;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import java.util.HashMap;
import redis.clients.jedis.Jedis;
import pl.best241.ccdrop.Redis;
import pl.best241.rdbplugin.JedisFactory;
import pl.best241.ccdrop.data.PlayerData;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class DataManager
{
    private static final ConcurrentHashMap<UUID, PlayerData> playerData;
    
    public static void loadPlayerData(final UUID uuid) {
        final Jedis jedis = JedisFactory.getInstance().getJedis();
        final long points = Redis.getPlayerDiggingPoints(uuid, jedis);
        final HashMap<Material, Boolean> dropsEnabmed = Redis.getPlayerDropsEnabled(uuid);
        final PlayerData data = new PlayerData(uuid, points, dropsEnabmed);
        DataManager.playerData.put(uuid, data);
        JedisFactory.getInstance().returnJedis(jedis);
    }
    
    public static PlayerData getPlayerData(final UUID uuid) {
        return DataManager.playerData.get(uuid);
    }
    
    public static void setPlayerData(final UUID uuid, final PlayerData data) {
        DataManager.playerData.put(uuid, data);
    }
    
    public static void removePlayerData(final UUID uuid) {
        if (uuid == null) {
            return;
        }
        if (!DataManager.playerData.contains(uuid)) {
            return;
        }
        final PlayerData data = DataManager.playerData.get(uuid);
        if (data.getNeedSave()) {
            savePlayerDataSync(uuid, data);
        }
    }
    
    public static void savePlayerDataSync(final UUID uuid, final PlayerData data) {
        final Jedis jedis = JedisFactory.getInstance().getJedis();
        Redis.savePlayerDiggingPoints(uuid, data.getPoints(), jedis);
        Redis.setPlayerDropsEnabled(uuid, data.getDropsEnabled());
        data.setNeedSave(false);
        setPlayerData(uuid, data);
        JedisFactory.getInstance().returnJedis(jedis);
    }
    
    public static void savePlayerDataAsync(final UUID uuid, final PlayerData data) {
        final Jedis jedis = JedisFactory.getInstance().getJedis();
        Redis.savePlayerDiggingPoints(uuid, data.getPoints(), jedis);
        Redis.setPlayerDropsEnabled(uuid, data.getDropsEnabled());
        JedisFactory.getInstance().returnJedis(jedis);
        data.setNeedSave(false);
        setPlayerData(uuid, data);
    }
    
    public static void saveTicker() {
        Bukkit.getScheduler().runTaskTimer((Plugin)CcDrop.getPlugin(), (Runnable)new Runnable() {
            @Override
            public void run() {
                for (final PlayerData data : DataManager.playerData.values()) {
                    if (data.getNeedSave()) {
                        DataManager.savePlayerDataAsync(data.getUUID(), data);
                    }
                }
            }
        }, 3600L, 3600L);
    }
    
    static {
        playerData = new ConcurrentHashMap<UUID, PlayerData>();
    }
}
