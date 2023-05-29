package dungeonmania.entities;

import dungeonmania.entities.enemies.Spider;
import dungeonmania.entities.logicalrules.LogicalRuleStrategy;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public class SwitchDoor extends Entity {
    private LogicalRuleStrategy logicalRuleStrategy;
    private boolean activated;

    public SwitchDoor(Position position, LogicalRuleStrategy logicalRuleStrategy) {
        super(position);
        this.logicalRuleStrategy = logicalRuleStrategy;
        activated = false;
    }

    public LogicalRuleStrategy getLogicalRule() {
        return logicalRuleStrategy;
    }

    @Override
    public boolean canMoveOnto(GameMap map, Entity entity) {
        return (activated || entity instanceof Spider);
    }

    public void updateActivation(GameMap map) {
        activated = logicalRuleStrategy.isLogicallyActive(map, getPosition());
    }

    public boolean isActivated() {
        return activated;
    }
}
