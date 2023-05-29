package dungeonmania.entities.collectables.potions;

import dungeonmania.battles.BattleStatistics;
import dungeonmania.entities.collectables.Collectables;
import dungeonmania.util.Position;

public abstract class Potion extends Collectables {
    private int duration;

    public Potion(Position position, int duration) {
        super(position);
        this.duration = duration;
    }

    public int getDuration() {
        return duration;
    }

    public BattleStatistics applyBuff(BattleStatistics origin) {
        return origin;
    }
}
