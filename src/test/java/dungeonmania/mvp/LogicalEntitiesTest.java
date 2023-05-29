package dungeonmania.mvp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import dungeonmania.DungeonManiaController;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class LogicalEntitiesTest {

    @Test
    @DisplayName("Test that the doors open and close properly")
    public void switchDoor() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_logicalEntitiesTest_switchDoorTest",
                                           "simple");

        // walk right
        res = dmc.tick(Direction.RIGHT);
        // assert position is that of the xor door (4,3) when walking down
        EntityResponse initPlayer = TestUtils.getPlayer(res).get();
        // create the expected result
        EntityResponse expectedPlayer = new EntityResponse(initPlayer.getId(),
        initPlayer.getType(), new Position(4, 3), false);
        // walk down
        DungeonResponse actualDungonRes = dmc.tick(Direction.DOWN);
        EntityResponse actualPlayer = TestUtils.getPlayer(actualDungonRes).get();
        // assert after movement
        assertTrue(TestUtils.entityResponsesEqual(expectedPlayer, actualPlayer));

        // walk down
        res = dmc.tick(Direction.DOWN);
        // walk down
        res = dmc.tick(Direction.DOWN);
        // walk right
        res = dmc.tick(Direction.RIGHT);
        // assert player position is below the and door (5,5) when walking up
        initPlayer = TestUtils.getPlayer(res).get();
        expectedPlayer = new EntityResponse(initPlayer.getId(),
        initPlayer.getType(), new Position(5, 5), false);
        actualDungonRes = dmc.tick(Direction.UP);
        actualPlayer = TestUtils.getPlayer(actualDungonRes).get();
        // assert after movement
        assertTrue(TestUtils.entityResponsesEqual(expectedPlayer, actualPlayer));
        // walk right
        res = dmc.tick(Direction.RIGHT);
        // walk up
        res = dmc.tick(Direction.UP);
        // assert position is on the or door (6,3) when walking up
        initPlayer = TestUtils.getPlayer(res).get();
        expectedPlayer = new EntityResponse(initPlayer.getId(),
        initPlayer.getType(), new Position(6, 3), false);
        actualDungonRes = dmc.tick(Direction.UP);
        actualPlayer = TestUtils.getPlayer(actualDungonRes).get();
    }

    @Test
    @DisplayName("Test CoAnd condition works correctly with switch and wire in tandem")
    public void coAnd() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_logicalEntitiesTest_coAndTest",
                                           "simple");
        // assert both lightbulbs are off
        assertEquals(2, TestUtils.countType(res, "light_bulb_off"));
        // move right
        res = dmc.tick(Direction.RIGHT);
        // assert that one lightbulb is on
        assertEquals(1, TestUtils.countType(res, "light_bulb_on"));
        // move left
        res = dmc.tick(Direction.LEFT);
        // move up
        res = dmc.tick(Direction.UP);
        // move up
        res = dmc.tick(Direction.UP);
        // move right
        res = dmc.tick(Direction.RIGHT);
        // assert that both are on
        assertEquals(2, TestUtils.countType(res, "light_bulb_on"));
        // move right
        res = dmc.tick(Direction.RIGHT);
        // assert that one is on
        assertEquals(1, TestUtils.countType(res, "light_bulb_on"));
        // move left
        res = dmc.tick(Direction.LEFT);
        // move down
        res = dmc.tick(Direction.DOWN);
        // move down
        res = dmc.tick(Direction.DOWN);
        // move right
        res = dmc.tick(Direction.RIGHT);
        // assert both are off
        assertEquals(2, TestUtils.countType(res, "light_bulb_off"));
    }

    @Test
    @DisplayName("Test logical rules implemented in logical entities")
    public void logicRules() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_logicalEntitiesTest_logicRulesTest",
                                           "simple");
        // assert 8 lightbulbs are off
        assertEquals(0, TestUtils.countType(res, "light_bulb_on"));
        assertEquals(8, TestUtils.countType(res, "light_bulb_off"));
        // walk up
        res = dmc.tick(Direction.UP);
        // walk right
        res = dmc.tick(Direction.RIGHT);
        // assert 7,1 lighbulb is on
        // assert 6,2 lightbulb is on
        // assert 8,2 lightbulb is off
        // assert 9,3 lightbulb is off
        // assert 5,4 ligthbbulb is on
        // assert 6,4 lightbulb is on
        // assert 8,4 ligthbulb is on
        // assert 7,5 lightbulb is off
        assertEquals(5, TestUtils.countType(res, "light_bulb_on"));
        assertEquals(3, TestUtils.countType(res, "light_bulb_off"));
        // walk down
        res = dmc.tick(Direction.DOWN);
        // walk left
        res = dmc.tick(Direction.LEFT);
        // walk down
        res = dmc.tick(Direction.DOWN);
        // walk right
        res = dmc.tick(Direction.RIGHT);
        // assert 9,3 lightbulb is off
        // assert 7,5 lightbulb is on
        assertEquals(6, TestUtils.countType(res, "light_bulb_on"));
        assertEquals(2, TestUtils.countType(res, "light_bulb_off"));
        //optional
        // walk up
        res = dmc.tick(Direction.UP);
        // walk right
        res = dmc.tick(Direction.RIGHT);
        // walk up
        res = dmc.tick(Direction.UP);
        // check all the lightbulbs are off
        assertEquals(0, TestUtils.countType(res, "light_bulb_on"));
        assertEquals(8, TestUtils.countType(res, "light_bulb_off"));
    }
}
