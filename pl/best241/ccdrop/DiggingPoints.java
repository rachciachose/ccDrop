// 
// Decompiled by Procyon v0.5.30
// 

package pl.best241.ccdrop;

import org.bukkit.Material;
import java.util.HashMap;

public class DiggingPoints
{
    private static final HashMap<Integer, Long> levels;
    private static final int DIAMOND_POINTS = 6;
    private static final int EMERALD_POINTS = 7;
    private static final int GOLD_POINTS = 4;
    private static final int IRON_POINTS = 3;
    private static final int COAL_POINTS = 3;
    private static final int REDSTONE_POINTS = 1;
    private static final int SAND_POINTS = 2;
    private static final int GUNPOWDER_POINTS = 4;
    private static final int COCOA_POINTS = 5;
    private static final int LAPIS_POINTS = 4;
    private static final int OBSIDIAN_POINTS = 6;
    private static final int BOOK_POINTS = 11;
    private static final int QUARTZ_POINTS = 4;
    private static final int APPLE_POINTS = 10;
    private static final int SUGAR_POINTS = 13;
    private static final int maxLevel = 200;
    
    public static int getPointsPerMaterial(final Material material) {
        switch (material) {
            case SAND: {
                return 2;
            }
            case REDSTONE: {
                return 1;
            }
            case COAL: {
                return 3;
            }
            case IRON_ORE: {
                return 3;
            }
            case GOLD_ORE: {
                return 4;
            }
            case EMERALD: {
                return 7;
            }
            case DIAMOND: {
                return 6;
            }
            case SULPHUR: {
                return 4;
            }
            case COCOA: {
                return 5;
            }
            case LAPIS_ORE: {
                return 4;
            }
            case BOOK: {
                return 11;
            }
            case QUARTZ: {
                return 4;
            }
            case APPLE: {
                return 10;
            }
            case OBSIDIAN: {
                return 6;
            }
            case SUGAR_CANE: {
                return 13;
            }
            default: {
                return 0;
            }
        }
    }
    
    public static int getPointsPerMaterial(final Material material, final int amount) {
        return getPointsPerMaterial(material) * amount;
    }
    
    public static void loadLevels() {
        DiggingPoints.levels.put(1, 0L);
        for (int i = 2; i <= 200; ++i) {
            final long exp = Math.round(Math.pow(i * 0.8, 2.44 + i * 0.015) + 1092 * i);
            DiggingPoints.levels.put(i, exp);
        }
    }
    
    public static int getLevelByPoints(final long points) {
        if (DiggingPoints.levels.isEmpty()) {
            loadLevels();
        }
        for (int i = 1; i <= 200; ++i) {
            final long pointsForLevel = DiggingPoints.levels.get(i);
            final Long pointsForNextLevel = DiggingPoints.levels.get(i + 1);
            if (i >= 200) {
                return i;
            }
            if (pointsForLevel <= points && (pointsForNextLevel == null || pointsForNextLevel >= points)) {
                return i;
            }
        }
        return 1;
    }
    
    public static long getPointsByLevel(final int level) {
        if (DiggingPoints.levels.isEmpty()) {
            loadLevels();
        }
        if (level == 200) {
            return 0L;
        }
        return DiggingPoints.levels.get(level);
    }
    
    static {
        levels = new HashMap<Integer, Long>();
    }
}
