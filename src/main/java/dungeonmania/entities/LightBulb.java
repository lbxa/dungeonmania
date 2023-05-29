package dungeonmania.entities;

import dungeonmania.entities.logicalrules.LogicalRuleStrategy;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public class LightBulb extends Entity {
    private LogicalRuleStrategy logicalRuleStrategy;
    private boolean activated;

    public LightBulb(Position position, LogicalRuleStrategy logicalRuleStrategy) {
        super(position);
        this.logicalRuleStrategy = logicalRuleStrategy;
        activated = false;
    }

    public LogicalRuleStrategy getLogicalRule() {
        return logicalRuleStrategy;
    }

    @Override
    public boolean canMoveOnto(GameMap map, Entity entity) {
        return true;
    }

    public boolean isActivated() {
        return activated;
    }

    public void updateActivation(GameMap map) {
        activated = logicalRuleStrategy.isLogicallyActive(map, getPosition());
    }
}
