/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hexoccupants;

/**
 * Interface for Objects with a cost. This includes purchasable Units, which
 * have a cost to buy, and Actions, which have a cost in action points to
 * execute.
 *
 * @author Nick Houser
 */
public interface HasCost {

    /**
     * Method which forces implementing classes to define their cost.
     *
     * @return the cost of the Object
     */
    public int cost();
}
