/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hexoccupants.pieces;

import java.util.AbstractMap;
import java.util.EnumMap;

/**
 *
 * @author Nick Houser
 */
public class Upgrades {

    private int level;
    private int defense;
    private int offense;
    private boolean healing;
    private boolean movement;
    private boolean damage;

    private final AbstractMap<UpgradeTypes, Integer> unlocked;

    public Upgrades() {
        unlocked = new EnumMap<>(UpgradeTypes.class);
    }

    public void upgrade(UpgradeTypes type) {
        if (!unlocked.containsKey(type)) {
            unlocked.put(type, 1);
        } else {
            unlocked.put(type, unlocked.get(type) + 1);
        }
        level = level + 1;
    }

    public int level() {
        return level;
    }

    public int count(UpgradeTypes type) {
        if (unlocked.containsKey(type)) {
            return unlocked.get(type);
        } else {
            return 0;
        }
    }
}
