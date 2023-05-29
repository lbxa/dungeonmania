package dungeonmania.goals.strategy;

import dungeonmania.Game;
import dungeonmania.entities.Switch;

public class BouldersGoalStrategy implements GoalStrategy {

    @Override
    public boolean achieved(Game game) {
        return game.getMap().getEntities(Switch.class).stream().allMatch(s -> s.isActivated());
    }

    @Override
    public String toString(Game game) {
        return ":boulders";
    }

}
