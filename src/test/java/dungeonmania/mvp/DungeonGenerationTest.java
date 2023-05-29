package dungeonmania.mvp;

import dungeonmania.DungeonManiaController;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.util.Position;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class DungeonGenerationTest {
    @Test
    @DisplayName("Generate rectangular dungeon with correct start and end")
    public void simpleGeneration() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse game = dmc.generateDungeon(1, 1, 18, 18, "simple");

        assertEquals(new Position(1, 1), TestUtils.getPlayerPos(game));
        assertEquals(new Position(18, 18), TestUtils.getEntityPos(game, "exit"));
    }

    @Test
    @DisplayName("Test for sorrounding walls around the map")
    public void sorroundingWalls() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse game = dmc.generateDungeon(10, 10, 20, 20, "simple");

        List<Position> walls = TestUtils.getEntityPositions(game, "wall");
        assertEquals(48, walls.stream()
            .filter(w -> w.getX() == 9 || w.getX() == 21 || w.getY() == 9 || w.getY() == 21)
            .count());
    }
}
