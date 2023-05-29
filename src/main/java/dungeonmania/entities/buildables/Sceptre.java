package dungeonmania.entities.buildables;

import dungeonmania.Game;
import dungeonmania.battles.BattleStatistics;

public class Sceptre extends Buildable {
    public static final int DEFAULT_MIND_DURATION = 8;
    private int duration = DEFAULT_MIND_DURATION;
    private int durability = 10;

    public Sceptre(int duration) {
        super(null);
        this.duration = duration;

    }

    @Override
    public BattleStatistics applyBuff(BattleStatistics origin) {
        return origin;
    }

    @Override
    public void use(Game game) {
    }

    @Override
    public int getDurability() {
        return durability;
    }
    public int getDuration() {
        return duration;
    }

}
