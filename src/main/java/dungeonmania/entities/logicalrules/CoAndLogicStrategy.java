package dungeonmania.entities.logicalrules;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import dungeonmania.entities.Entity;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public class CoAndLogicStrategy implements LogicalRuleStrategy {
    @Override
    public boolean isLogicallyActive(GameMap gameMap, Position currentPosition) {
        int cardinallyAdjActivated = 0;
        boolean activatedSimultaneously = false;
        List<Integer> activationTicksOfAdjacent = new ArrayList<Integer>();
        List<Position> cardinallyAdjPos = currentPosition.getCardinallyAdjacentPositions();

        for (Position position : cardinallyAdjPos) {
            List<Entity> entities = gameMap.getEntities(position);

            cardinallyAdjActivated += entities.stream()
                .filter(entity -> LogicalRuleStrategy.isActivated(entity)).count();

            // Check when the activations of any adjacent wires/switches happened
            entities.stream()
                    .map(entity -> LogicalRuleStrategy.tickActivated(entity))
                    .filter(when -> when != -1)
                    .forEach(when -> activationTicksOfAdjacent.add(when));
        }

        // if | Set{activationTicksOfAdjacent} | >= | activationTicksOfAdjacent | it means
        // there were identical ticks in the Set.
        if ((new HashSet<Integer>(activationTicksOfAdjacent)).size() < activationTicksOfAdjacent.size()) {
            activatedSimultaneously = true;
        }

        return cardinallyAdjActivated >= 2 && activatedSimultaneously;
    }
}
