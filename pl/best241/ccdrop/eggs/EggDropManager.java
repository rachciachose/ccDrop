// 
// Decompiled by Procyon v0.5.30
// 

package pl.best241.ccdrop.eggs;

import org.bukkit.plugin.Plugin;
import pl.best241.ccdrop.CcDrop;
import org.bukkit.inventory.meta.ItemMeta;
import java.util.Iterator;
import java.util.Set;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import java.util.List;
import java.util.Arrays;
import java.util.Map;
import org.bukkit.inventory.ItemStack;
import java.util.HashMap;
import org.bukkit.ChatColor;
import org.bukkit.entity.EntityType;
import java.util.ArrayList;

public class EggDropManager
{
    private static ArrayList<EggDropData> eggDrops;
    private static EggsConfig eggsConfig;
    
    public static void loadAll() {
        EggDropManager.eggsConfig.saveDefaultConfig();
        EggDropManager.eggsConfig.reloadCustomConfig();
        EggDropManager.eggDrops.clear();
        final ConfigurationSection entitiesSection = EggDropManager.eggsConfig.getCustomConfig().getConfigurationSection("entities");
        final Set<String> entities = (Set<String>)entitiesSection.getKeys(false);
        for (final String entity : entities) {
            final ConfigurationSection entitySection = entitiesSection.getConfigurationSection(entity);
            final EntityType entityType = EntityType.valueOf(entity);
            if (entityType == null) {
                System.out.println(entity + " is not a correct entity");
            }
            else {
                final ConfigurationSection eggSection = entitySection.getConfigurationSection("egg");
                final String eggItemname = ChatColor.translateAlternateColorCodes('&', eggSection.getString("name"));
                final double dropChance = eggSection.getDouble("dropChance");
                final String[] eggLore = ChatColor.translateAlternateColorCodes('&', eggSection.getString("eggLore")).split("\n");
                final HashMap<ItemStack, Double> drops = new HashMap<ItemStack, Double>();
                final List<Map<?, ?>> mapList = (List<Map<?, ?>>)entitySection.getMapList("items");
                for (final Map<?, ?> item : mapList) {
                    final Integer id = (Integer)item.get("id");
                    final Integer amount = (Integer)item.get("amount");
                    final String name = ChatColor.translateAlternateColorCodes('&', (String)item.get("name"));
                    final Integer data = (Integer)item.get("data");
                    final Double weight = (Double)item.get("weight");
                    final String[] lore = ChatColor.translateAlternateColorCodes('&', (String)item.get("lore")).split("\n");
                    final ItemStack itemStack = new ItemStack((int)id, (int)amount, (short)(Object)data);
                    final ItemMeta itemMeta = itemStack.getItemMeta();
                    itemMeta.setDisplayName(name);
                    itemMeta.setLore((List)Arrays.asList(lore));
                    itemStack.setItemMeta(itemMeta);
                    final ArrayList<Map<String, Object>> enchantments = (ArrayList<Map<String, Object>>)item.get("enchantments");
                    for (final Map<String, Object> enchantmentData : enchantments) {
                        final Enchantment enchant = Enchantment.getByName((String)enchantmentData.get("type"));
                        if (enchant == null) {
                            System.out.println(enchantmentData.get("type") + " doesn't exists");
                        }
                        else {
                            itemStack.addUnsafeEnchantment(enchant, (int)enchantmentData.get("level"));
                        }
                    }
                    drops.put(itemStack, weight);
                }
                final EggDropData eggDropData = new EggDropData(entityType, eggItemname, eggLore, dropChance, drops);
                EggDropManager.eggDrops.add(eggDropData);
            }
        }
    }
    
    public static EggDropData getEggDropDataFromEntityType(final EntityType type) {
        for (final EggDropData eggDropData : EggDropManager.eggDrops) {
            if (eggDropData.getEntityType() == type) {
                return eggDropData;
            }
        }
        return null;
    }
    
    public static EggDropData getEggDropDataFromName(final String displayName) {
        for (final EggDropData eggDropData : EggDropManager.eggDrops) {
            if (eggDropData.getEggName().equals(displayName)) {
                return eggDropData;
            }
        }
        return null;
    }
    
    public static ArrayList<EggDropData> getAllEggDrops() {
        return EggDropManager.eggDrops;
    }
    
    static {
        EggDropManager.eggDrops = new ArrayList<EggDropData>();
        EggDropManager.eggsConfig = new EggsConfig((Plugin)CcDrop.getPlugin(), "config.yml");
    }
}
