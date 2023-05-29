package dungeonmania.entities;

import java.util.List;

import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public class Wire extends Entity {
    private boolean activated;
    private int activatedTick = -1;

    public Wire(Position position) {
        super(position.asLayer(Entity.ITEM_LAYER));
    }

    @Override
    public boolean canMoveOnto(GameMap map, Entity entity) {
        return true;
    }

    public boolean isActivated() {
        return activated;
    }

    public int getActivatedTick() {
        return activatedTick;
    }

    public void deactivate() {
        activated = false;
        activatedTick = -1;
    }

    public void activate(int activationTick) {
        activated = true;
        activatedTick = activationTick;
    }

    public void activateSurroundingWires(GameMap gameMap, int activationTick) {
        activate(activationTick);
        List<Position> adjacentPositions = getPosition().getCardinallyAdjacentPositions();

        for (Position position : adjacentPositions) {
            List<Entity> entities = gameMap.getEntities(position);

            // activate any wires found that weren't yet activated this tick
            entities.stream()
                    .filter(entity -> entity instanceof Wire).map(Wire.class::cast)
                    .filter(w -> w.getActivatedTick() != activationTick)
                    .forEach(w -> w.activateSurroundingWires(gameMap, activationTick));
        }
    }
}
