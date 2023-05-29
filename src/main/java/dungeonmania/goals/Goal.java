package dungeonmania.goals;

import dungeonmania.Game;
import dungeonmania.goals.strategy.AndGoalStrategy;
import dungeonmania.goals.strategy.BouldersGoalStrategy;
import dungeonmania.goals.strategy.EnemyGoalStrategy;
import dungeonmania.goals.strategy.ExitGoalStrategy;
import dungeonmania.goals.strategy.GoalStrategy;
import dungeonmania.goals.strategy.OrGoalStrategy;
import dungeonmania.goals.strategy.TreasureGoalStrategy;


public class Goal {
    private String type;
    private int target;
    private Goal goal1;
    private Goal goal2;

    private GoalStrategy goalStrategy;

    public Goal(String type) {
        this.type = type;
    }

    public Goal(String type, int target) {
        this.type = type;
        this.target = target;
    }

    public Goal(String type, Goal goal1, Goal goal2) {
        this.type = type;
        this.goal1 = goal1;
        this.goal2 = goal2;
    }

    public GoalStrategy getGoalStrategy() {
        return goalStrategy;
    }

    public void setGoalStrategy(GoalStrategy goalStrategy) {
        this.goalStrategy = goalStrategy;
    }

    /**
     * @return true if the goal has been achieved, false otherwise
     */
    public boolean achieved(Game game) {
        if (game.getPlayer() == null) return false;
        switch (type) {
            case "exit":
                setGoalStrategy(new ExitGoalStrategy());
                return getGoalStrategy().achieved(game);
            case "boulders":
                setGoalStrategy(new BouldersGoalStrategy());
                return getGoalStrategy().achieved(game);
            case "treasure":
                setGoalStrategy(new TreasureGoalStrategy(target));
                return getGoalStrategy().achieved(game);
            case "enemies":
                setGoalStrategy(new EnemyGoalStrategy(target));
                return getGoalStrategy().achieved(game);
            case "AND":
                setGoalStrategy(new AndGoalStrategy(goal1, goal2));
                return getGoalStrategy().achieved(game);
            case "OR":
                setGoalStrategy(new OrGoalStrategy(goal1, goal2));
                return getGoalStrategy().achieved(game);
            default:
                break;
        }
        return false;
    }

    public String toString(Game game) {
        if (this.achieved(game)) return "";
        switch (type) {
            case "exit":
                setGoalStrategy(new ExitGoalStrategy());
                return getGoalStrategy().toString(game);
            case "boulders":
                setGoalStrategy(new BouldersGoalStrategy());
                return getGoalStrategy().toString(game);
            case "treasure":
                setGoalStrategy(new TreasureGoalStrategy(target));
                return getGoalStrategy().toString(game);
            case "enemies":
                setGoalStrategy(new EnemyGoalStrategy(target));
                return getGoalStrategy().toString(game);
            case "AND":
                setGoalStrategy(new AndGoalStrategy(goal1, goal2));
                return getGoalStrategy().toString(game);
            case "OR":
                setGoalStrategy(new OrGoalStrategy(goal1, goal2));
                return getGoalStrategy().toString(game);
            default:
                break;
        }
        return "";
    }

}
