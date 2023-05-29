package dungeonmania.mvp;

import dungeonmania.DungeonManiaController;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AssassinTest {
    @Test
    @Tag("12-1")
    @DisplayName("Tests the assassin being bribed successfully")
    public void passBribe() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_assassinTest_passBribe", "c_assassinTest_passBribe");

        assertEquals(new Position(4, 1), getAssPos(res));
        res = dmc.tick(Direction.RIGHT);
        assertEquals(new Position(3, 1), getAssPos(res));
        String assId = TestUtils.getEntitiesStream(res, "assassin").findFirst().get().getId();
        res = assertDoesNotThrow(() -> dmc.interact(assId));
        assertEquals(new Position(1, 1), getAssPos(res));

    }

    @Test
    @Tag("12-2")
    @DisplayName("Tests the assassin being bribed unsuccessfully")
    public void failBribe() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_assassinTest_failBribe", "c_assassinTest_failBribe");

        assertEquals(new Position(4, 1), getAssPos(res));
        res = dmc.tick(Direction.RIGHT);
        assertEquals(new Position(3, 1), getAssPos(res));
        String assId = TestUtils.getEntitiesStream(res, "assassin").findFirst().get().getId();
        res = assertDoesNotThrow(() -> dmc.interact(assId));
        assertEquals(new Position(2, 1), getAssPos(res));
    }
    // However if the assassin's attack is set to 4 (1 less than the Player's),
    // after interacting they move to (1,1) instead of (2,1)

    private Position getAssPos(DungeonResponse res) {
        return TestUtils.getEntities(res, "assassin").get(0).getPosition();
    }

}
