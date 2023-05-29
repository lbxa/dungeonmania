package dungeonmania.entities;

import java.util.ArrayList;
import java.util.List;

import dungeonmania.entities.collectables.Bomb;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public class Switch extends Entity {
    private boolean activated;
    private int activatedTick;
    private List<Bomb> bombs = new ArrayList<>();

    public Switch(Position position) {
        super(position.asLayer(Entity.ITEM_LAYER));
    }

    public void subscribe(Bomb b) {
        bombs.add(b);
    }

    public void subscribe(Bomb bomb, GameMap map) {
        bombs.add(bomb);
        if (activated) {
            bombs.stream().forEach(b -> b.notify(map));
        }
    }

    public void unsubscribe(Bomb b) {
        bombs.remove(b);
    }

    @Override
    public boolean canMoveOnto(GameMap map, Entity entity) {
        return true;
    }

    @Override
    public void onOverlap(GameMap map, Entity entity) {
        if (entity instanceof Boulder) {
            activated = true;
            activatedTick = map.getGame().getTick();
            bombs.stream().forEach(b -> b.notify(map));
        }
    }

    @Override
    public void onMovedAway(GameMap map, Entity entity) {
        if (entity instanceof Boulder) {
            activated = false;
            activatedTick = -1;
        }
    }

    public boolean isActivated() {
        return activated;
    }

    public int getActivatedTick() {
        return activatedTick;
    }

    public void activateWires(GameMap gameMap) {
        List<Position> adjacentPositions = getPosition().getCardinallyAdjacentPositions();

        // for each cardinally adjacent position
        for (Position position : adjacentPositions) {
             List<Entity> entities = gameMap.getEntities(position);

            // activate any wires found
            entities.stream()
                    .filter(entity -> entity instanceof Wire)
                    .forEach(wire -> ((Wire) wire).activateSurroundingWires(gameMap, activatedTick));
        }
    }
}
