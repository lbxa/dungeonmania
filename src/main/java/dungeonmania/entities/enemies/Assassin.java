package dungeonmania.entities.enemies;

import java.util.Random;

import dungeonmania.Game;
import dungeonmania.entities.Player;
import dungeonmania.util.Position;

public class Assassin extends Mercenary {
    public static final int DEFAULT_BRIBE_AMOUNT = 1;
    public static final int DEFAULT_BRIBE_RADIUS = 1;
    public static final double DEFAULT_ATTACK = 12.5;
    public static final double DEFAULT_HEALTH = 10.0;
    public static final double DEFAULT_BRIBE_FAIL_RATE = 0.5;

    private double bribeFailRate = Assassin.DEFAULT_BRIBE_FAIL_RATE;
    private Random rand = new Random();

    public Assassin(Position position, double health, double attack, int bribeAmount,
                    int bribeRadius, double bribeFailRate) {
        super(position, health, attack, bribeAmount, bribeRadius);
        this.bribeFailRate = bribeFailRate;
    }

    @Override
    public void interact(Player player, Game game) {
        if (rand.nextInt(100) >= 100 * bribeFailRate) {
            super.interact(player, game);
        } else {
            super.bribe(player);
        }
    }
}
