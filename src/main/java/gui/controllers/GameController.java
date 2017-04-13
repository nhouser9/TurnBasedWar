package gui.controllers;

import hex.Hex;
import hexoccupants.actions.Action;
import hexoccupants.pieces.Unit;
import hexoccupants.pieces.UnitFactory;
import java.util.Collection;
import java.util.List;
import players.Player;

/**
 * Interface describing a controller which acts as an intermediary between the
 * application data and the UI.
 *
 * @author Nick Houser
 */
public interface GameController {

    public int size();

    public Collection<Hex> hexes();

    public boolean purchasable(Hex target);

    public boolean connected();

    public Player active();

    public void pass();

    public void add(Class<? extends Unit> unitType) throws UnitFactory.InsufficentPointsException;

    public List<Action> actions();

    public void act(Action action);

    public SelectionManager selections();

    public UiManager ui();

    public String[] score();
}
