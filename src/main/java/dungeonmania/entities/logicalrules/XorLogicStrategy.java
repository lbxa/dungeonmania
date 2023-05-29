package dungeonmania.entities.logicalrules;

import java.util.List;

import dungeonmania.entities.Entity;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public class XorLogicStrategy implements LogicalRuleStrategy {
    @Override
    public boolean isLogicallyActive(GameMap gameMap, Position currentPosition) {
        int cardinallyAdjActivated = 0;
        List<Position> cardinallyAdjPos = currentPosition.getCardinallyAdjacentPositions();

        for (Position position : cardinallyAdjPos) {
            List<Entity> entities = gameMap.getEntities(position);

            cardinallyAdjActivated += entities.stream()
                .filter(entity -> LogicalRuleStrategy.isActivated(entity)).count();
        }

        return cardinallyAdjActivated == 1;
    }

}

