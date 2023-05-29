package dungeonmania.goals.strategy;

import dungeonmania.Game;
import dungeonmania.entities.enemies.ZombieToastSpawner;

public class EnemyGoalStrategy implements GoalStrategy {
    private int target;

    public EnemyGoalStrategy(int target) {
        this.target = target;
    }

    @Override
    public boolean achieved(Game game) {
        return game.getMap().getEntities(ZombieToastSpawner.class).size() == 0
            && game.getDestroyedEnemies() >= target;
    }

    @Override
    public String toString(Game game) {
        return ":enemies";
    }
}

