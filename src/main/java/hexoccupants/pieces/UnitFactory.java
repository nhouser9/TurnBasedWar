package hexoccupants.pieces;

import hex.Hex;
import hex.HexGrid;
import java.util.AbstractMap;
import java.util.HashMap;
import players.Player;
import turnbasedwar.Settings;

/**
 * Factory for Units.
 *
 * @author Nick Houser
 */
public class UnitFactory {

    //list of the leaders, which should be initialized before regular Unit creation
    private final AbstractMap<Player, Leader> leaders;

    /**
     * Constructor which initializes internal data structures.
     */
    public UnitFactory() {
        leaders = new HashMap<>();
    }

    /**
     * Factory creation method for Units.
     *
     * @param <T> the type of Unit to create
     * @param type the class of the Unit to create
     * @param creator the Player who should own the new Unit
     * @param grid the board on which the Unit will be created
     * @param target the Hex to add the new Unit to
     * @return a new Unit of the requested type
     * @throws InsufficentPointsException if the Player cannot affor the Unit
     * @throws TooFarFromLeaderError if the Hex is out of range of the creator's
     * Leader
     * @throws HexOccupiedError if the passed Hex is already occupied
     * @throws BadUnitTypeError if the passed type is not supported
     * @throws UnitCreatedBeforeLeaderError if asked to create a non-Leader Unit
     * for a Player before a Leader has been created for that Player
     */
    public <T extends Unit> Unit create(Class<T> type, Player creator, HexGrid<Hex> grid, Hex target) throws InsufficentPointsException {
        if (target.occupant() != null) {
            throw new HexOccupiedError();
        }

        Leader leader = null;
        if (type != Leader.class) {
            if (!leaders.containsKey(creator)) {
                throw new UnitCreatedBeforeLeaderError();
            } else {
                leader = leaders.get(creator);
                Hex location = leader.location();
                if (grid.distance(target.x(), target.y(), location.x(), location.y()) > Settings.RANGE_CREATION) {
                    throw new TooFarFromLeaderError();
                }
            }
        }

        Unit created = null;
        if (type.equals(Melee.class)) {
            created = new Melee(creator, leader);
        } else if (type.equals(Ranged.class)) {
            created = new Ranged(creator, leader);
        } else if (type.equals(Builder.class)) {
            created = new Builder(creator, leader);
        } else if (type.equals(Healer.class)) {
            created = new Healer(creator, leader);
        } else if (type.equals(Leader.class)) {
            created = new Leader(creator);
            leaders.put(creator, (Leader) created);
        }

        if (created == null) {
            throw new BadUnitTypeError();
        }

        if (creator.purchasePoints() < created.cost()) {
            throw new InsufficentPointsException();
        }

        creator.purchase(created);
        target.setOccupant(created);
        created.relocate(target);
        return created;
    }

    /**
     * Exception trown when the Player creating the Unit cannot afford the
     * requested Unit.
     */
    public class InsufficentPointsException extends Exception {
    }

    /**
     * Exception thrown when a Unit would be created too far from its Leader.
     */
    public class TooFarFromLeaderError extends Error {
    }

    /**
     * Error thrown when the factory is asked to fill a Hex that is already
     * filled.
     */
    public class HexOccupiedError extends Error {
    }

    /**
     * Error thrown when the factory is asked to create a bad Unit type.
     */
    public class BadUnitTypeError extends Error {
    }

    /**
     * Error thrown when the factory is asked to create a Unit for a Player
     * before that Player has a Leader.
     */
    public class UnitCreatedBeforeLeaderError extends Error {
    }
}
