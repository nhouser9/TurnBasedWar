package gui.controllers;

import hex.Hex;

/**
 * Data class for tracking the user's selections. Tracks a selected Hex and a
 * targeted Hex.
 *
 * @author Nick Houser
 */
public class SelectionManager {

    //the user's selected Hex
    private Hex selection;
    private Hex target;

    /**
     * Sets the user's selected Hex.
     *
     * @param selection the new selected Hex
     */
    public void setSelection(Hex selection) {
        this.selection = selection;
    }

    /**
     * Gets the user's selected Hex.
     *
     * @return the user's selected Hex
     */
    public Hex getSelection() {
        return selection;
    }

    /**
     * Sets the user's targeted Hex.
     *
     * @param target the user's targeted Hex
     */
    public void setTarget(Hex target) {
        this.target = target;
    }

    /**
     * Gets the user's targeted Hex.
     *
     * @return the user's targeted Hex
     */
    public Hex getTarget() {
        return target;
    }
}
