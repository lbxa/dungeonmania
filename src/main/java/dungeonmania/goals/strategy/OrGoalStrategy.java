package dungeonmania.goals.strategy;

import dungeonmania.Game;
import dungeonmania.goals.Goal;

public class OrGoalStrategy implements GoalStrategy {
    private Goal goal1;
    private Goal goal2;

    public OrGoalStrategy(Goal goal1, Goal goal2) {
        this.goal1 = goal1;
        this.goal2 = goal2;
    }

    @Override
    public boolean achieved(Game game) {
        return goal1.achieved(game) || goal2.achieved(game);
    }

    @Override
    public String toString(Game game) {
        if (achieved(game)) return "";
        else return "(" + goal1.toString(game) + " OR " + goal2.toString(game) + ")";
    }
}
