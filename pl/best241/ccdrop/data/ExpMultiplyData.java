// 
// Decompiled by Procyon v0.5.30
// 

package pl.best241.ccdrop.data;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public class ExpMultiplyData
{
    private final long timeTo;
    private final int multiply;
    private final int distnace;
    private final Location loc;
    
    public ExpMultiplyData(final long timeTo, final int multiply, final int distance, final String world, final int x, final int y, final int z) {
        this.timeTo = timeTo;
        this.multiply = multiply;
        this.distnace = distance;
        this.loc = new Location(Bukkit.getWorld(world), (double)x, (double)y, (double)z);
    }
    
    public long getTimeTo() {
        return this.timeTo;
    }
    
    public int getMultiply() {
        return this.multiply;
    }
    
    public int getDistance() {
        return this.distnace;
    }
    
    public Location getLocation() {
        return this.loc;
    }
}
