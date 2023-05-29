package dungeonmania.entities.logicalrules;

import dungeonmania.entities.Entity;
import dungeonmania.entities.Switch;
import dungeonmania.entities.Wire;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public interface LogicalRuleStrategy {
    public static boolean isActivated(Entity entity) {
        if (entity instanceof Wire) {
            return ((Wire) entity).isActivated();
        } else if (entity instanceof Switch) {
            return ((Switch) entity).isActivated();
        }
        return false;
    }

    public static int tickActivated(Entity entity) {
        if (entity instanceof Wire) {
            return ((Wire) entity).getActivatedTick();
        } else if (entity instanceof Switch) {
            return ((Switch) entity).getActivatedTick();
        }
        return -1;
    }

    public boolean isLogicallyActive(GameMap gameMap, Position currentPosition);
}

