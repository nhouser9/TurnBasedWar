/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.controllers;

import hex.Hex;
import hex.HexGrid;
import hexoccupants.Constructs;
import hexoccupants.actions.Action;
import hexoccupants.pieces.Leader;
import hexoccupants.pieces.Unit;
import hexoccupants.pieces.UnitFactory;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.util.Pair;
import players.Player;
import players.PlayerFactory;
import turnbasedwar.Settings;

/**
 *
 * @author Nick Houser
 */
public class LocalController implements GameController {

    private HexGrid<Hex> grid;

    private Player[] players;

    private Leader[] leaders;

    private int player;

    private UnitFactory units;

    private SelectionManager selections;

    private UiManager ui;

    public LocalController() {
        createPlayers();
        createGrid();
        setupObjectives();
        setupPieces();
        createSubcontrollers();
    }

    private void createPlayers() {
        PlayerFactory playerFactory = new PlayerFactory();
        players = new Player[Settings.NUM_PLAYERS];
        for (int i = 0; i < players.length; i++) {
            players[i] = playerFactory.create();
        }
        player = 0;
    }

    private void createGrid() {
        grid = new HexGrid<>(Settings.BOARD_SIZE);
        for (Pair<Integer, Integer> coordinate : grid.coordinates()) {
            new Hex(grid, coordinate.getKey(), coordinate.getValue());
        }
    }

    private void setupObjectives() {
        units = new UnitFactory();

        int index = 2 * (Settings.BOARD_SIZE - Settings.OBJECTIVE_OFFSET);
        grid.get(0, index).setConstruct(Constructs.OBJECTIVE_EDGE);
        grid.get(0, -index).setConstruct(Constructs.OBJECTIVE_EDGE);

        index = Settings.BOARD_SIZE - Settings.OBJECTIVE_OFFSET;
        grid.get(index, index).setConstruct(Constructs.OBJECTIVE_EDGE);
        grid.get(-index, index).setConstruct(Constructs.OBJECTIVE_EDGE);
        grid.get(index, -index).setConstruct(Constructs.OBJECTIVE_EDGE);
        grid.get(-index, -index).setConstruct(Constructs.OBJECTIVE_EDGE);

        grid.get(0, 0).setConstruct(Constructs.OBJECTIVE_CENTER);
    }

    private void setupPieces() {
        leaders = new Leader[Settings.NUM_PLAYERS];

        switch (Settings.NUM_PLAYERS) {
            case 2:
                addLeader(1, 0, Settings.BOARD_SIZE * 2);
            case 1:
                addLeader(0, 0, -Settings.BOARD_SIZE * 2);
                break;
            case 3:
                addLeader(0, 0, -Settings.BOARD_SIZE * 2);
                addLeader(1, Settings.BOARD_SIZE, Settings.BOARD_SIZE);
                addLeader(2, -Settings.BOARD_SIZE, Settings.BOARD_SIZE);
                break;
            case 6:
                addLeader(5, 0, Settings.BOARD_SIZE * 2);
            case 5:
                addLeader(4, 0, -Settings.BOARD_SIZE * 2);
            case 4:
                addLeader(0, Settings.BOARD_SIZE, Settings.BOARD_SIZE);
                addLeader(1, -Settings.BOARD_SIZE, Settings.BOARD_SIZE);
                addLeader(2, Settings.BOARD_SIZE, -Settings.BOARD_SIZE);
                addLeader(3, -Settings.BOARD_SIZE, -Settings.BOARD_SIZE);
                break;
        }
    }

    private void addLeader(int playernum, int x, int y) {
        try {
            leaders[playernum] = (Leader) units.create(Leader.class, players[playernum], grid, grid.get(x, y));
        } catch (UnitFactory.InsufficentPointsException | UnitFactory.TooFarFromLeaderError ex) {
            Logger.getLogger(LocalController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void createSubcontrollers() {
        selections = new SelectionManager();
        ui = new UiManager();
    }

    @Override
    public int size() {
        return grid.size();
    }

    @Override
    public Collection<Hex> hexes() {
        return grid.all();
    }

    @Override
    public boolean purchasable(Hex target) {
        Hex location = leaders[player].location();
        int distance = grid.distance(location.x(), location.y(), target.x(), target.y());
        return (distance <= Settings.RANGE_CREATION);
    }

    @Override
    public void pass() {
        players[player].finishPurchasing();
        player = player + 1;

        if (player >= players.length) {
            player = 0;
            for (Hex hex : grid.all()) {
                if (hex.occupant() != null) {
                    hex.occupant().passed(grid);

                    if (hex.construct() == Constructs.OBJECTIVE_CENTER) {
                        hex.occupant().creator().incrementScore(1);
                    }

                    if (hex.construct() == Constructs.OBJECTIVE_EDGE) {
                        hex.occupant().creator().incrementScore(2);
                    }
                }
            }
        }
    }

    @Override
    public Player active() {
        return players[player];
    }

    @Override
    public boolean connected() {
        return true;
    }

    @Override
    public void add(Class<? extends Unit> unitType) throws UnitFactory.InsufficentPointsException {
        units.create(unitType, active(), grid, selections.getSelection());
        ui.refresh();
    }

    @Override
    public List<Action> actions() {
        List<Action> allowed = new LinkedList<>();

        Hex selection = selections().getSelection();
        Hex target = selections().getTarget();

        if (selection == null || target == null) {
            return allowed;
        }

        Unit actor = selection.occupant();

        if (actor == null) {
            return allowed;
        }

        for (Action action : actor.actions()) {
            if (action.allowed(grid, target)) {
                allowed.add(action);
            }
        }

        return allowed;
    }

    @Override
    public void act(Action action) {
        action.act(grid, selections().getTarget());
        selections.setSelection(null);
        selections.setTarget(null);
    }

    @Override
    public SelectionManager selections() {
        return selections;
    }

    @Override
    public UiManager ui() {
        return ui;
    }

    @Override
    public String[] score() {
        String[] toReturn = new String[players.length];
        for (int i = 0; i < players.length; i++) {
            toReturn[i] = players[i].toString() + ": " + players[i].score();
        }
        return toReturn;
    }
}
