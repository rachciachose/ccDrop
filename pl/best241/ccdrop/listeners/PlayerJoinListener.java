// 
// Decompiled by Procyon v0.5.30
// 

package pl.best241.ccdrop.listeners;

import org.bukkit.event.EventHandler;
import java.util.UUID;
import org.bukkit.entity.Player;
import pl.best241.ccdrop.manager.DataManager;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.Listener;

public class PlayerJoinListener implements Listener
{
    @EventHandler
    public static void playerJoinListener(final PlayerJoinEvent event) {
        final Player player = event.getPlayer();
        final UUID uuid = player.getUniqueId();
        DataManager.loadPlayerData(uuid);
    }
}
