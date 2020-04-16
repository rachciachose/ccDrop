// 
// Decompiled by Procyon v0.5.30
// 

package pl.best241.ccdrop.listeners;

import org.bukkit.event.inventory.InventoryInteractEvent;
import pl.best241.ccdrop.data.PlayerData;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import java.util.UUID;
import pl.best241.ccdrop.manager.DataManager;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.EventHandler;
import pl.best241.ccdrop.guis.DropGuiManager;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.Listener;

public class InventoryListener implements Listener
{
    @EventHandler
    public static void onInventoryClose(final InventoryCloseEvent event) {
        if (DropGuiManager.openedGuids.contains(event.getPlayer().getUniqueId())) {
            DropGuiManager.openedGuids.remove(event.getPlayer().getUniqueId());
        }
    }
    
    @EventHandler
    public static void onInventoryClick(final InventoryClickEvent event) {
        final UUID uuid = event.getWhoClicked().getUniqueId();
        if (event.getClickedInventory() != null && event.getView().getTopInventory().getTitle().equals(DropGuiManager.magicChestsDrop)) {
            event.setCancelled(true);
            event.getWhoClicked().setItemOnCursor((ItemStack)null);
            event.setResult(Event.Result.DENY);
        }
        if (event.getClickedInventory() != null && event.getView().getTopInventory().getTitle().equals(DropGuiManager.headsInventoryName)) {
            event.setCancelled(true);
            event.getWhoClicked().setItemOnCursor((ItemStack)null);
            event.setResult(Event.Result.DENY);
        }
        if (DropGuiManager.openedGuids.contains(event.getWhoClicked().getUniqueId())) {
            event.setCancelled(true);
            event.getWhoClicked().setItemOnCursor((ItemStack)null);
            event.setResult(Event.Result.DENY);
            final InventoryView invView = event.getView();
            final Inventory clickedInv = event.getClickedInventory();
            if (clickedInv == null) {
                return;
            }
            if (invView.getTopInventory().equals(clickedInv) && (event.getAction() == InventoryAction.PICKUP_ALL || event.getAction() == InventoryAction.PICKUP_HALF || event.getClick() == ClickType.LEFT || event.getClick() == ClickType.RIGHT)) {
                final ItemStack cursor = event.getClickedInventory().getItem(event.getSlot());
                if (cursor == null) {
                    return;
                }
                final int slot = event.getSlot();
                if (slot == 35) {
                    DropGuiManager.openHeads((Player)event.getWhoClicked());
                }
                else if (slot == 34) {
                    DropGuiManager.openMagicChestsDrop((Player)event.getWhoClicked());
                }
                else {
                    final Material material = cursor.getType();
                    final PlayerData data = DataManager.getPlayerData(uuid);
                    final boolean enabled = DropGuiManager.getEnabled(uuid, material);
                    data.getDropsEnabled().put(material, !enabled);
                    data.setNeedSave(true);
                    DataManager.setPlayerData(uuid, data);
                    DropGuiManager.rerenderGui((Player)event.getWhoClicked(), clickedInv);
                }
                ((Player)event.getWhoClicked()).updateInventory();
            }
        }
    }
    
    @EventHandler
    public static void onInventoryDrag(final InventoryInteractEvent event) {
        final UUID uuid = event.getWhoClicked().getUniqueId();
        if (DropGuiManager.openedGuids.contains(event.getWhoClicked().getUniqueId())) {
            final InventoryView invView = event.getView();
            final Inventory clickedInv = event.getInventory();
            if (clickedInv == null) {
                return;
            }
            if (invView.getTopInventory().equals(clickedInv)) {
                event.setCancelled(true);
                event.getWhoClicked().setItemOnCursor((ItemStack)null);
                event.setResult(Event.Result.DENY);
                ((Player)event.getWhoClicked()).updateInventory();
            }
        }
        else if (event.getInventory() != null && event.getView().getTopInventory().getTitle().equals(DropGuiManager.headsInventoryName)) {
            event.setCancelled(true);
            event.getWhoClicked().setItemOnCursor((ItemStack)null);
            event.setResult(Event.Result.DENY);
        }
        if (event.getInventory() != null && event.getView().getTopInventory().getTitle().equals(DropGuiManager.magicChestsDrop)) {
            event.setCancelled(true);
            event.getWhoClicked().setItemOnCursor((ItemStack)null);
            event.setResult(Event.Result.DENY);
        }
    }
}
