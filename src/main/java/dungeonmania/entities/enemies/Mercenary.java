package dungeonmania.entities.enemies;

import dungeonmania.Game;
import dungeonmania.entities.Entity;
import dungeonmania.entities.Interactable;
import dungeonmania.entities.Player;
import dungeonmania.entities.buildables.Sceptre;
import dungeonmania.entities.collectables.Treasure;
import dungeonmania.entities.enemies.strategy.AllyMoveStrategy;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public class Mercenary extends Enemy implements Interactable {
    public static final int DEFAULT_BRIBE_AMOUNT = 1;
    public static final int DEFAULT_BRIBE_RADIUS = 1;
    public static final double DEFAULT_ATTACK = 5.0;
    public static final double DEFAULT_HEALTH = 10.0;

    private int bribeAmount = Mercenary.DEFAULT_BRIBE_AMOUNT;
    private int bribeRadius = Mercenary.DEFAULT_BRIBE_RADIUS;
    private boolean allied = false;
    private boolean cardinallyAdj = false;
    private int mindControlTicks = 0;

    public Mercenary(Position position, double health, double attack, int bribeAmount, int bribeRadius) {
        super(position, health, attack);
        this.bribeAmount = bribeAmount;
        this.bribeRadius = bribeRadius;
        setMoveStrategy(new AllyMoveStrategy(null, false));
    }

    public boolean isAllied() {
        return allied;
    }

    public boolean isCardinallyAdj() {
        return cardinallyAdj;
    }

    public void setCardinallyAdj(boolean cardinallyAdj) {
        this.cardinallyAdj = cardinallyAdj;
    }

    @Override
    public void onOverlap(GameMap map, Entity entity) {
        if (allied || mindControl()) return;
        super.onOverlap(map, entity);
    }

    /**
     * check whether the current merc can be bribed
     * @param player
     * @return
     */
    private boolean canBeBribed(Player player) {
        return bribeRadius >= Position.calculatePositionBetween(player.getPosition(), getPosition()).magnitude()
         && player.countEntityOfType(Treasure.class) >= bribeAmount;
    }

    /**
     * bribe the merc
     */
    protected void bribe(Player player) {
        for (int i = 0; i < bribeAmount; i++) {
            player.use(Treasure.class);
        }
    }

    private boolean mindControl() {
        if (mindControlTicks == 0) {
            return false;
        }
        return true;
    }


    @Override
    public void interact(Player player, Game game) {
        if (canBeBribed(player)) {
            allied = true;
            bribe(player);
        } else {
            mindControlTicks = player.getInventory().getFirst(Sceptre.class).getDuration();
        }

    }

    @Override
    public void move(Game game) {
        setMoveStrategy(new AllyMoveStrategy(this, allied || mindControl()));
        getMoveStrategy().move(game);
        if (mindControlTicks > 0) mindControlTicks--;
    }

    @Override
    public boolean isInteractable(Player player) {
        return (!allied && canBeBribed(player)) || (!mindControl() && player.countEntityOfType(Sceptre.class) == 1);
    }
}
