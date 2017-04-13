package hexoccupants.pieces;

import hex.Hex;
import hex.HexGrid;
import hexoccupants.actions.Upgrade;
import players.Player;

/**
 * Unit which moves and provides buffs to nearby Units. Initially provides no
 * buffs but levels up over time to provide more. The user can choose which
 * buffs.
 *
 * @author Nick Houser
 */
public class Leader extends Unit {

    /**
     * The number of turns that must pass before a Leader can level up.
     */
    protected static final int TURNS_UNITL_LEVEL = 10;

    /**
     * The range of a Leader's buffs.
     */
    public static final int RANGE = 3;

    /**
     * The amount of damage and healing applied to nearby units if the upgrade
     * is enabled.
     */
    protected static final int BUFF = 2;

    //the upgrades this Leader has achieved
    private final Upgrades upgrades;

    //the number of turns this Leader has survived
    private int age;

    /**
     * Constructor which simply calls the inherited constructor.
     *
     * @param creator the Player who created the Piece
     */
    protected Leader(Player creator) {
        super(creator, null);
        upgrades = new Upgrades();
        age = 0;
        addAction(new Upgrade(this, 0, UpgradeTypes.OFFENSE));
        addAction(new Upgrade(this, 0, UpgradeTypes.DEFENSE));
        addAction(new Upgrade(this, 1, UpgradeTypes.OFFENSE));
        addAction(new Upgrade(this, 1, UpgradeTypes.DEFENSE));
        addAction(new Upgrade(this, 2, UpgradeTypes.HEALING));
        addAction(new Upgrade(this, 2, UpgradeTypes.MOVEMENT));
        addAction(new Upgrade(this, 2, UpgradeTypes.DAMAGE));
    }

    /**
     * Gets the Upgrades object which tracks this Leader's upgrades.
     *
     * @return the Upgrades object which tracks this Leader's upgrades
     */
    public Upgrades upgrades() {
        return upgrades;
    }

    /**
     * Returns the level of the Leader in terms of its age. This controls how
     * often the Leader can upgrade.
     *
     * @return the level of the Leader
     */
    public int level() {
        return Math.floorDiv(age, TURNS_UNITL_LEVEL);
    }

    /**
     * Defines the cost of a Leader Unit.
     *
     * @return the cost of a Leader Unit
     */
    @Override
    public int cost() {
        return 0;
    }

    /**
     * Gets the path of the image used to represent this Piece on the board.
     *
     * @return the path of the image used to represent this Piece on the board
     */
    @Override
    public String imagePath() {
        return "leader";
    }

    /**
     * The initial action points for a Leader Unit.
     *
     * @return the initial number of action points for this Unit
     */
    @Override
    protected int initialActionPoints() {
        return 3;
    }

    /**
     * The initial health for a Leader Unit.
     *
     * @return the initial amount of health for this Unit
     */
    @Override
    protected int initialHealth() {
        return 6;
    }

    /**
     * Method which handles end of turn actions. Calls super() to reset the
     * Unit's action points before handling updating the Leader's age and
     * handling area of effect actions triggered by upgrades.
     *
     * @param hexes the list of Hexes on the board
     */
    @Override
    public void passed(HexGrid<Hex> hexes) {
        super.passed(hexes);

        age = age + 1;

        for (Hex hex : hexes.all()) {
            Unit occupant = hex.occupant();
            if (occupant == null) {
                continue;
            }

            int distance = hexes.distance(hex.x(), hex.y(), location().x(), location().y());
            if (distance == 0 || distance > RANGE) {
                continue;
            }

            if (occupant.creator().equals(creator())) {
                if (upgrades().count(UpgradeTypes.HEALING) > 0) {
                    occupant.heal(BUFF);
                }
            } else if (upgrades().count(UpgradeTypes.DAMAGE) > 0) {
                occupant.damage(BUFF);
            }
        }
    }
}
