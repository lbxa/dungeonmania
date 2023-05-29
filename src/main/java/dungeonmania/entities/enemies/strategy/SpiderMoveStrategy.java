package dungeonmania.entities.enemies.strategy;

import java.util.List;

import dungeonmania.Game;
import dungeonmania.entities.Boulder;
import dungeonmania.entities.Entity;
import dungeonmania.entities.enemies.Enemy;
import dungeonmania.util.Position;

public class SpiderMoveStrategy implements MoveStrategy {
    private Enemy enemy;
    private int nextPositionElement;
    private boolean forward;
    private List<Position> movementTrajectory;

    public SpiderMoveStrategy(Enemy enemy, Position position) {
        this.enemy = enemy;
        this.nextPositionElement = 1;
        this.forward = true;
        this.movementTrajectory = position.getAdjacentPositions();
    }

    private void updateNextPosition() {
        if (forward) {
            nextPositionElement++;
            if (nextPositionElement == 8) {
                nextPositionElement = 0;
            }
        } else {
            nextPositionElement--;
            if (nextPositionElement == -1) {
                nextPositionElement = 7;
            }
        }
    }

    @Override
    public void move(Game game) {
        if (!enemy.stuck()) {
            Position nextPos = movementTrajectory.get(nextPositionElement);
            List<Entity> entities = game.getMap().getEntities(nextPos);
            if (entities != null && entities.size() > 0 && entities.stream().anyMatch(e -> e instanceof Boulder)) {
                forward = !forward;
                updateNextPosition();
                updateNextPosition();
            }
            nextPos = movementTrajectory.get(nextPositionElement);
            entities = game.getMap().getEntities(nextPos);
            if (entities == null
                    || entities.size() == 0
                    || entities.stream().allMatch(e -> e.canMoveOnto(game.getMap(), enemy))) {
                game.getMap().moveTo(enemy, nextPos);
                updateNextPosition();
            }
        }
    }
}
