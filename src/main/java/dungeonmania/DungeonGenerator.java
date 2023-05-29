package dungeonmania;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class DungeonGenerator {
    private boolean[][] grid;
    private int width;
    private int height;

    private static final boolean WALL = false;
    private static final boolean EMPTY = true;
    private static final int CARDINALLY_ADJACENT = 1;
    private static final int WALL_BOUNDARY = 1;

    private static class Pair {
        private int x;
        private int y;

        public Pair(int x, int y) {
            this.x = x; this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }
    }

    public DungeonGenerator(int xStart, int yStart, int xEnd, int yEnd) {
        this.width = Math.abs(xEnd - xStart) + (3 * WALL_BOUNDARY);
        this.height = Math.abs(yEnd - yStart) + (3 * WALL_BOUNDARY);
        this.grid = new boolean[height][width]; // all false by default
    }

    private boolean at(Pair loc) {
        return grid[loc.getY()][loc.getX()];
    };

    private void set(Pair loc, boolean val) {
         grid[loc.getY()][loc.getX()] = val;
    }

    private boolean checkInBounds(Pair loc) {
        return loc.getX() > 0 && loc.getX() < width - 1 && loc.getY() > 0 && loc.getY() < height - 1;
    }

    private boolean checkNotWall(Pair loc) {
        return at(loc) != WALL;
    }

    private Pair midpoint(Pair loc1, Pair loc2) {
        return new Pair((loc1.getX() + loc2.getX()) / 2, (loc1.getY() + loc2.getY()) / 2);
    }

    private List<Pair> getNeighbours(Pair loc, int cardinalDistance) {
        return Arrays.asList(
            new Pair(loc.getX() + cardinalDistance, loc.getY()),
            new Pair(loc.getX() - cardinalDistance, loc.getY()),
            new Pair(loc.getX(), loc.getY() + cardinalDistance),
            new Pair(loc.getX(), loc.getY() - cardinalDistance)
        );
    }

    private List<Pair> getNeighbours(Pair loc) {
        return Arrays.asList(
            new Pair(loc.getX() + 2, loc.getY()),
            new Pair(loc.getX() - 2, loc.getY()),
            new Pair(loc.getX(), loc.getY() + 2),
            new Pair(loc.getX(), loc.getY() - 2)
        );
    }

    private Pair popRandomElement(List<Pair> list) {
        Random rand = new Random();
        Pair x = list.get(rand.nextInt(list.size()));
        list.remove(x);
        return x;
    }

    private Pair getRandomElement(List<Pair> list) {
        Random rand = new Random();
        return list.get(rand.nextInt(list.size()));
    }

    public boolean[][] randomizedPrims() {
        Pair start = new Pair(1, 1);
        Pair end = new Pair(width - WALL_BOUNDARY - 1, height - WALL_BOUNDARY - 1);
        set(start, EMPTY);

        List<Pair> options = new ArrayList<>();

        getNeighbours(start).stream()
            .filter(n -> checkInBounds(n))
            .filter(n -> !checkNotWall(n))
            .forEach(n -> options.add(n));

        while (options.size() != 0) {
            Pair next = popRandomElement(options);

            List<Pair> neighbours = getNeighbours(next).stream()
                .filter(n -> checkInBounds(n))
                .filter(n -> checkNotWall(n))
                .collect(Collectors.toList());

            if (neighbours.size() != 0) {
                Pair neighbour = getRandomElement(neighbours);
                Pair mid = midpoint(next, neighbour);

                set(next, EMPTY);
                set(mid, EMPTY);
                set(neighbour, EMPTY);
            }

            getNeighbours(next).stream()
                .filter(n -> checkInBounds(n))
                .filter(n -> !checkNotWall(n))
                .forEach(n -> options.add(n));
        }

        if (at(end) == WALL) {
            set(end, EMPTY);
            List<Pair> neighbours = getNeighbours(end, CARDINALLY_ADJACENT);
            boolean someEmptyNeighbours = neighbours.stream().anyMatch(n -> checkNotWall(n));
            if (!someEmptyNeighbours) {
                Pair leftNeighbour = neighbours.stream()
                    .filter(n -> n.getX() <= end.getX())
                    .filter(n -> n.getY() <= end.getY())
                    .findFirst().get();
                set(leftNeighbour, EMPTY);
            }
        }
        return grid;
    }
}
