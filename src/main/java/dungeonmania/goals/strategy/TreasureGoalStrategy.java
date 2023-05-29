package dungeonmania.goals.strategy;

import dungeonmania.Game;
import dungeonmania.entities.collectables.Treasure;

public class TreasureGoalStrategy implements GoalStrategy {
    private int target;

    public TreasureGoalStrategy(int target) {
        this.target = target;
    }

    @Override
    public boolean achieved(Game game) {
        return game.getInitialTreasureCount() - game.getMap().getEntities(Treasure.class).size() >= target;
    }

    @Override
    public String toString(Game game) {
        return ":treasure";
    }

}
