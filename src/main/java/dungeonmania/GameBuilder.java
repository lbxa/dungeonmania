package dungeonmania;

import java.io.IOException;

import org.json.JSONObject;

import dungeonmania.entities.Entity;
import dungeonmania.entities.EntityFactory;
import dungeonmania.entities.Player;
import dungeonmania.goals.Goal;
import dungeonmania.goals.GoalFactory;
import dungeonmania.map.GameMap;
import dungeonmania.map.GraphNode;
import dungeonmania.map.GraphNodeFactory;
import dungeonmania.util.FileLoader;

/**
 * GameBuilder -- A builder to build up the whole game
 * @author      Webster Zhang
 * @author      Tina Ji
 */
public class GameBuilder {
    private String configName;
    private String dungeonName;

    private JSONObject config;
    private JSONObject dungeon;

    public GameBuilder setConfigName(String configName) {
        this.configName = configName;
        return this;
    }

    public GameBuilder setDungeonName(String dungeonName) {
        this.dungeonName = dungeonName;
        return this;
    }

    public Game buildGame() {
        loadConfig();
        loadDungeon();
        if (dungeon == null && config == null) {
            return null; // something went wrong
        }

        Game game = new Game(dungeonName);
        EntityFactory factory = new EntityFactory(config);
        game.setEntityFactory(factory);
        buildMap(game);
        buildGoals(game);
        game.init();

        return game;
    }

    private JSONObject createJSONEntity(String type, int x, int y) {
        JSONObject o = new JSONObject();
        o.put("x", x);
        o.put("y", y);
        o.put("type", type);
        return o;
    }

    public Game genRandomGame(int xStart, int yStart, int xEnd, int yEnd) {
        loadConfig();
        if (config == null) {
            return null; // something went wrong
        }

        Game game = new Game(dungeonName);
        EntityFactory factory = new EntityFactory(config);
        game.setEntityFactory(factory);

        GameMap map = new GameMap();
        map.setGame(game);

        addPlayer(game, map, xStart, yStart);
        addWalls(game, map, xStart, yStart, xEnd, yEnd);
        addExit(game, map, xEnd, yEnd);

        game.setMap(map);

        addExitGoal(game, map);
        game.init();

        return game;
    }

    private void loadConfig() {
        String configFile = String.format("/configs/%s.json", configName);
        try {
            config = new JSONObject(FileLoader.loadResourceFile(configFile));
        } catch (IOException e) {
            e.printStackTrace();
            config = null;
        }
    }

    private void loadDungeon() {
        String dungeonFile = String.format("/dungeons/%s.json", dungeonName);
        try {
            dungeon = new JSONObject(FileLoader.loadResourceFile(dungeonFile));
        } catch (IOException e) {
            dungeon = null;
        }
    }

    private void buildMap(Game game) {
        GameMap map = new GameMap();
        map.setGame(game);

        dungeon.getJSONArray("entities").forEach(e -> {
            JSONObject jsonEntity = (JSONObject) e;
            GraphNode newNode = GraphNodeFactory.createEntity(jsonEntity, game.getEntityFactory());
            Entity entity = newNode.getEntities().get(0);

            if (newNode != null)
                map.addNode(newNode);

            if (entity instanceof Player)
                map.setPlayer((Player) entity);
        });
        game.setMap(map);
    }

    private void addWalls(Game game, GameMap map, int xStart, int yStart, int xEnd, int yEnd) {
        DungeonGenerator dg = new DungeonGenerator(xStart, yStart, xEnd, yEnd);
        boolean[][] walls = dg.randomizedPrims();

        for (int col = 0; col < walls.length; col++) {
            for (int row = 0; row < walls[0].length; row++) {
                if (!walls[col][row]) {
                    JSONObject wall = createJSONEntity("wall", row + xStart - 1, col + yStart - 1);
                    GraphNode newNode = GraphNodeFactory.createEntity(wall, game.getEntityFactory());
                    if (newNode != null) map.addNode(newNode);
                }
            }
        }
    }

    private void addPlayer(Game game, GameMap map, int x, int y) {
        JSONObject playerJSON = createJSONEntity("player", x, y);
        GraphNode newPlayerNode = GraphNodeFactory.createEntity(playerJSON, game.getEntityFactory());
        Entity player = newPlayerNode.getEntities().get(0);
        map.addNode(newPlayerNode);
        map.setPlayer((Player) player);
    }

    private void addExit(Game game, GameMap map, int x, int y) {
        JSONObject exit = createJSONEntity("exit", x, y);
        GraphNode newNode = GraphNodeFactory.createEntity(exit, game.getEntityFactory());
        map.addNode(newNode);
    }

    public void buildGoals(Game game) {
        if (!dungeon.isNull("goal-condition")) {
            Goal goal = GoalFactory.createGoal(dungeon.getJSONObject("goal-condition"), config);
            game.setGoals(goal);
        }
    }

    private void addExitGoal(Game game, GameMap map) {
        JSONObject exitGoal = new JSONObject();
        exitGoal.put("goal", "exit");
        Goal goal = GoalFactory.createGoal(exitGoal, config);
        game.setGoals(goal);
    }
}
