/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hexoccupants;

/**
 *
 * @author Nick Houser
 */
public enum Constructs {
    NONE,
    OBJECTIVE_EDGE,
    OBJECTIVE_CENTER,
    FORT,
    ROAD;

    private String id;

    static {
        NONE.id = "damage";
        FORT.id = "defense";
        ROAD.id = "movement";
    }

    public String id() {
        return id;
    }
}
