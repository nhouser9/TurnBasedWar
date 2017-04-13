/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hexoccupants.pieces;

/**
 *
 * @author Nick Houser
 */
public enum UpgradeTypes {
    DEFENSE,
    OFFENSE,
    HEALING,
    MOVEMENT,
    DAMAGE;

    private String id;

    static {
        DEFENSE.id = "defense";
        OFFENSE.id = "offense";
        HEALING.id = "healing";
        MOVEMENT.id = "movement";
        DAMAGE.id = "damage;";
    }

    public String id() {
        return id;
    }
}
