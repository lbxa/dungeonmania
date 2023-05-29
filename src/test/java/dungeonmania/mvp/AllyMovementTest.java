package dungeonmania.mvp;

import dungeonmania.DungeonManiaController;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AllyMovementTest {
    @Test
    @Tag("12-1")
    @DisplayName("Test ally movement after they reached the cardinally adjacent tiles")
    public void allyMovementAfterCardinallyAdj() {
        //                                  Wall    Wall   Wall    Wall    Wall    Wall
        // P1       P2      P3M5    P4M6    M4      M3      M2      M1      .      Wall
        //                          P6M7    Wall    Wall   Wall    Wall    Wall    Wall
        //                          P79M8   P8M9

        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_allyMovementTest_allyMovementAfterCardinallyAdj",
                                           "c_mercenaryTest_simpleMovement");

        assertEquals(new Position(8, 1), getMercPos(res));
        res = dmc.tick(Direction.RIGHT);
        assertEquals(new Position(7, 1), getMercPos(res));
        res = dmc.tick(Direction.RIGHT);
        assertEquals(new Position(6, 1), getMercPos(res));
        res = dmc.tick(Direction.RIGHT);
        assertEquals(new Position(5, 1), getMercPos(res));

        String mercId = TestUtils.getEntitiesStream(res, "mercenary").findFirst().get().getId();
        res = assertDoesNotThrow(() -> dmc.interact(mercId));
        assertEquals(new Position(3, 1), getMercPos(res));
        res = dmc.tick(Direction.DOWN);
        assertEquals(new Position(4, 1), getMercPos(res));
        res = dmc.tick(Direction.DOWN);
        assertEquals(new Position(4, 2), getMercPos(res));
        res = dmc.tick(Direction.RIGHT);
        assertEquals(new Position(4, 3), getMercPos(res));
        res = dmc.tick(Direction.LEFT);
        assertEquals(new Position(5, 3), getMercPos(res));
    }

    @Test
    @Tag("12-2")
    @DisplayName("Test ally dijkstra movement after being successfully bribed")
    public void allyMovementAfterBribe() {
        //                                  Wall    Wall   Wall    Wall    Wall    Wall
        // P1       P2      P3      P4      M4      M3      M2      M1      .      Wall
        //                                  Wall    Wall   Wall    Wall    Wall    Wall
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_allyMovementTest_allyMovementAfterBribe",
                                          "c_allyMovementTest_allyMovementAfterBribe");

        assertEquals(new Position(8, 1), getMercPos(res));
        res = dmc.tick(Direction.RIGHT);
        assertEquals(new Position(7, 1), getMercPos(res));
        String mercId = TestUtils.getEntitiesStream(res, "mercenary").findFirst().get().getId();
        res = assertDoesNotThrow(() -> dmc.interact(mercId));
        assertEquals(new Position(6, 1), getMercPos(res));
        res = dmc.tick(Direction.UP);
        assertEquals(new Position(5, 1), getMercPos(res));
        res = dmc.tick(Direction.UP);
        assertEquals(new Position(4, 1), getMercPos(res));
        res = dmc.tick(Direction.UP);
        assertEquals(new Position(4, 0), getMercPos(res));
        res = dmc.tick(Direction.DOWN);
        assertEquals(new Position(3, 0), getMercPos(res));
        res = dmc.tick(Direction.LEFT);
        assertEquals(new Position(3, -1), getMercPos(res));
        res = dmc.tick(Direction.DOWN);
        assertEquals(new Position(2, -1), getMercPos(res));
        res = dmc.tick(Direction.UP);
        assertEquals(new Position(1, 0), getMercPos(res));
    }

    private Position getMercPos(DungeonResponse res) {
        return TestUtils.getEntities(res, "mercenary").get(0).getPosition();
    }
}
