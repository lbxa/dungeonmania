package dungeonmania.entities.enemies.strategy;

import java.util.List;

import dungeonmania.Game;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;
import dungeonmania.entities.collectables.potions.InvisibilityPotion;
import dungeonmania.entities.enemies.Enemy;
import dungeonmania.entities.enemies.Mercenary;

public class AllyMoveStrategy implements MoveStrategy {
    private Enemy enemy;
    private boolean allied;

    public AllyMoveStrategy(Enemy enemy, boolean allied) {
        this.enemy = enemy;
        this.allied = allied;
    }

    @Override
    public void move(Game game) {
        Position nextPos;
        GameMap map = game.getMap();
        List<Position> pos = enemy.getPosition().getCardinallyAdjacentPositions();
        if (allied) {
            if (pos.contains(map.getPlayer().getPosition()) || ((Mercenary) enemy).isCardinallyAdj()) {
                ((Mercenary) enemy).setCardinallyAdj(true);
                nextPos = map.getPlayer().getPreviousDistinctPosition();
                map.moveTo(enemy, nextPos);
            } else {
                if (!enemy.stuck()) {
                nextPos = map.dijkstraPathFind(enemy.getPosition(), map.getPlayer().getPosition(), enemy);
                map.moveTo(enemy, nextPos);
                }
            }
        } else {
            if (!enemy.stuck()) {
                if (map.getPlayer().getEffectivePotion() instanceof InvisibilityPotion) return;
                nextPos = map.dijkstraPathFind(enemy.getPosition(), map.getPlayer().getPosition(), enemy);
                map.moveTo(enemy, nextPos);
            }
        }
    }
}
