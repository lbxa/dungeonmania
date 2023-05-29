package dungeonmania.entities.enemies.strategy;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import dungeonmania.Game;
import dungeonmania.entities.enemies.Enemy;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public class RandomMoveStrategy implements MoveStrategy {
    private Enemy enemy;
    private Random randGen = new Random();

    public RandomMoveStrategy(Enemy enemy) {
        this.enemy = enemy;
    }

    @Override
    public void move(Game game) {
        if (!enemy.stuck()) {
            Position nextPos;
            GameMap map = game.getMap();
            List<Position> pos = enemy.getPosition().getCardinallyAdjacentPositions();
            pos = pos.stream()
                .filter(p -> map.canMoveTo(enemy, p)).collect(Collectors.toList());
            if (pos.size() == 0) {
                nextPos = enemy.getPosition();
                map.moveTo(enemy, nextPos);
            } else {
                nextPos = pos.get(randGen.nextInt(pos.size()));
                map.moveTo(enemy, nextPos);
            }
        }
    }
}
