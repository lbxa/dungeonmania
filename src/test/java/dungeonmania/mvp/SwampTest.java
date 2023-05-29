package dungeonmania.mvp;

import dungeonmania.DungeonManiaController;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SwampTest {
    @Test
    @DisplayName("Testing an enemy mercenary gets stuck")
    public void enemySwampMove() {
        //                                  Wall    Wall    Wall
        // P1       P2/Treasure      .      M2      M1      Wall
        //                                  Wall    Wall    Wall
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_swamptileTest", "c_mercenaryTest_allyBattle");


        assertEquals(new Position(8, 1), getMercPos(res));
        res = dmc.tick(Direction.LEFT);
        assertEquals(new Position(7, 1), getMercPos(res));
        res = dmc.tick(Direction.LEFT);
        assertEquals(new Position(6, 1), getMercPos(res));
        res = dmc.tick(Direction.LEFT);
        assertEquals(new Position(5, 1), getMercPos(res));
        res = dmc.tick(Direction.LEFT);
        assertEquals(new Position(4, 1), getMercPos(res));
        res = dmc.tick(Direction.LEFT);
        assertEquals(new Position(4, 1), getMercPos(res));
        res = dmc.tick(Direction.LEFT);
        assertEquals(new Position(4, 1), getMercPos(res));
        res = dmc.tick(Direction.LEFT);
        assertEquals(new Position(3, 1), getMercPos(res));
        res = dmc.tick(Direction.LEFT);
        assertEquals(new Position(2, 1), getMercPos(res));
        res = dmc.tick(Direction.LEFT);
        assertEquals(new Position(1, 1), getMercPos(res));
    }
    @Test
    @DisplayName("Test ally dijkstra movement through swamp after being successfully bribed")
    public void swampAfterBribe() {
        //                                  Wall    Wall   Wall    Wall    Wall    Wall
        // P1       P2      P3      P4      M4      M3      M2      M1      .      Wall
        //                                  Wall    Wall   Wall    Wall    Wall    Wall
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_swamptileTest",
                                          "c_allyMovementTest_allyMovementAfterBribe");

        assertEquals(new Position(8, 1), getMercPos(res));
        res = dmc.tick(Direction.RIGHT);
        assertEquals(new Position(7, 1), getMercPos(res));
        String mercId = TestUtils.getEntitiesStream(res, "mercenary").findFirst().get().getId();
        res = dmc.tick(Direction.RIGHT);
        assertEquals(new Position(6, 1), getMercPos(res));
        res = dmc.tick(Direction.LEFT);

        assertEquals(new Position(5, 1), getMercPos(res));
        res = assertDoesNotThrow(() -> dmc.interact(mercId));
        assertEquals(new Position(4, 1), getMercPos(res));
        res = dmc.tick(Direction.RIGHT);
        assertEquals(new Position(2, 1), getMercPos(res));
    }
    @Test
    @DisplayName("Test player movement through swamp")
    public void playerSwamp() {
        //                                  Wall    Wall   Wall    Wall    Wall    Wall
        // P1       P2      P3      P4      M4      M3      M2      M1      .      Wall
        //                                  Wall    Wall   Wall    Wall    Wall    Wall
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_swamptileTest",
                                          "c_allyMovementTest_allyMovementAfterBribe");

        assertEquals(new Position(1, 1), getPlayerPos(res));
        res = dmc.tick(Direction.RIGHT);
        assertEquals(new Position(2, 1), getPlayerPos(res));
        res = dmc.tick(Direction.RIGHT);
        assertEquals(new Position(3, 1), getPlayerPos(res));
        res = dmc.tick(Direction.RIGHT);
        assertEquals(new Position(4, 1), getPlayerPos(res));
        res = dmc.tick(Direction.RIGHT);
        assertEquals(new Position(5, 1), getPlayerPos(res));
    }
    private Position getPlayerPos(DungeonResponse res) {
        return TestUtils.getEntities(res, "player").get(0).getPosition();
    }
    private Position getMercPos(DungeonResponse res) {
        return TestUtils.getEntities(res, "mercenary").get(0).getPosition();
    }
}
