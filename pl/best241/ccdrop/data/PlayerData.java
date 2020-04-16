// 
// Decompiled by Procyon v0.5.30
// 

package pl.best241.ccdrop.data;

import pl.best241.ccdrop.DiggingPoints;
import org.bukkit.Material;
import java.util.HashMap;
import java.util.UUID;

public class PlayerData
{
    private final UUID uuid;
    private long points;
    private boolean needSave;
    private HashMap<Material, Boolean> enabledDrop;
    
    public PlayerData(final UUID uuid, final long points, final HashMap<Material, Boolean> enabledDrop) {
        this.uuid = uuid;
        this.points = points;
        this.needSave = false;
        this.enabledDrop = enabledDrop;
    }
    
    public UUID getUUID() {
        return this.uuid;
    }
    
    public void setPoints(final long points) {
        this.points = points;
    }
    
    public long getPoints() {
        return this.points;
    }
    
    public boolean getNeedSave() {
        return this.needSave;
    }
    
    public void setNeedSave(final boolean needSave) {
        this.needSave = needSave;
    }
    
    public void addPoints(final int pointsToAdd) {
        this.points += pointsToAdd;
    }
    
    public int getLevel() {
        return DiggingPoints.getLevelByPoints(this.getPoints());
    }
    
    public HashMap<Material, Boolean> getDropsEnabled() {
        return this.enabledDrop;
    }
}
