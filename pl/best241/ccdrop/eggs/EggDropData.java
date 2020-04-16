// 
// Decompiled by Procyon v0.5.30
// 

package pl.best241.ccdrop.eggs;

import java.util.Iterator;
import java.util.Collection;
import java.util.ArrayList;
import org.bukkit.inventory.ItemStack;
import java.util.HashMap;
import org.bukkit.entity.EntityType;

public class EggDropData
{
    private EntityType entityType;
    private String eggName;
    private String[] eggLore;
    private double dropChance;
    private HashMap<ItemStack, Double> drops;
    
    public EggDropData(final EntityType entityType, final String eggName, final String[] eggLore, final double dropChance, final HashMap<ItemStack, Double> drops) {
        this.entityType = entityType;
        this.eggName = eggName;
        this.eggLore = eggLore;
        this.dropChance = dropChance;
        this.drops = drops;
    }
    
    public EntityType getEntityType() {
        return this.entityType;
    }
    
    public double getDropChance() {
        return this.dropChance;
    }
    
    public String getEggName() {
        return this.eggName;
    }
    
    public String[] getEggLore() {
        return this.eggLore;
    }
    
    public ItemStack randomDropItem() {
        final ArrayList<ItemStack> keys = new ArrayList<ItemStack>(this.drops.keySet());
        double totalWeight = 0.0;
        for (final ItemStack item : keys) {
            totalWeight += this.drops.get(item);
        }
        int randomIndex = -1;
        double random = Math.random() * totalWeight;
        for (int i = 0; i < keys.size(); ++i) {
            random -= this.drops.get(keys.get(i));
            if (random <= 0.0) {
                randomIndex = i;
                break;
            }
        }
        return keys.get(randomIndex);
    }
}
