package dungeonmania.entities;

import dungeonmania.entities.enemies.Enemy;
import dungeonmania.entities.enemies.Mercenary;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public class SwampTile extends Entity {
    public static final int DEFAULT_MOVEMENT_FACTOR = 2;
    private int movementFactor = DEFAULT_MOVEMENT_FACTOR;

    public SwampTile(Position position, int movementFactor) {
        super(position.asLayer(Entity.FLOOR_LAYER));
        this.movementFactor = movementFactor;
    }
    @Override
    public boolean canMoveOnto(GameMap map, Entity entity) {
        return true;
    }

    @Override
    public void onOverlap(GameMap map, Entity entity) {
        if (entity instanceof Mercenary && ((Mercenary) entity).isAllied() && ((Mercenary) entity).isCardinallyAdj()) {
            return;
        }
        if (entity instanceof Enemy && !((Enemy) entity).stuck()) {
            ((Enemy) entity).setStuckTicks(movementFactor);
        }
    }
}
